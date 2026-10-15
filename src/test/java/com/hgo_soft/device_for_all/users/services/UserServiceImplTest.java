package com.hgo_soft.device_for_all.users.services;

import com.hgo_soft.device_for_all.common.exceptions.ResourceNotFoundException;
import com.hgo_soft.device_for_all.users.dtos.CreateUserRequest;
import com.hgo_soft.device_for_all.users.dtos.UpdateUserRequest;
import com.hgo_soft.device_for_all.users.entities.User;
import com.hgo_soft.device_for_all.users.mappers.UserMapper;
import com.hgo_soft.device_for_all.users.repositories.UserProfileRepository;
import com.hgo_soft.device_for_all.users.repositories.UserRepository;
import com.hgo_soft.device_for_all.users.services.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {
    private UserRepository repository;
    private UserProfileRepository userProfileRepository;
    private UserServiceImpl service;
    private PasswordEncoder encoder;
    private UserMapper mapper;

    @BeforeEach
    void setUp() {
        repository = mock(UserRepository.class);
        userProfileRepository = mock(UserProfileRepository.class);
        mapper = mock(UserMapper.class);
        encoder = mock(PasswordEncoder.class);
        service = new UserServiceImpl(repository, userProfileRepository, encoder, mapper);
    }

    @Test
    void testFindAll_ShouldReturnUsers() {
        List<User> users = Arrays.asList(User.builder().id(1L).build(), User.builder().id(2L).build());
        when(repository.findAll()).thenReturn(users);

        List<User> result = service.findAll();

        assertEquals(2, result.size());
        verify(repository).findAll();
    }

    @Test
    void testFindById_WhenFound() {
        User user = User.builder().id(1L).build();
        when(repository.findById(1L)).thenReturn(Optional.of(user));

        Optional<User> result = service.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
    }

    @Test
    void testFindById_WhenNotFound() {
        when(repository.findById(999L)).thenReturn(Optional.empty());

        Optional<User> result = service.findById(999L);

        assertFalse(result.isPresent());
    }

    @Test
    void testCreate_ShouldReturnSavedUser() {
        CreateUserRequest createUserRequest = new CreateUserRequest(
                "test",
                "test@example.com",
                "validPassword123"
        );
        User mappedUser = new User();
        User savedUser = User.builder().id(10L).build();

        when(mapper.toEntity(createUserRequest)).thenReturn(mappedUser);
        when(encoder.encode(createUserRequest.password())).thenReturn("encoded-password");
        when(repository.save(mappedUser)).thenReturn(savedUser);

        User result = service.create(createUserRequest);

        assertEquals(10L, result.getId());
        assertEquals("encoded-password", mappedUser.getPasswordHash());
        assertTrue(mappedUser.isEnabled());
        verify(mapper).toEntity(createUserRequest);
        verify(encoder).encode(createUserRequest.password());
        verify(repository).save(mappedUser);
    }

    @Test
    void testCreate_ShouldHashPasswordBeforeSaving() {
        PasswordEncoder realEncoder = new BCryptPasswordEncoder();
        service = new UserServiceImpl(
                repository,
                userProfileRepository,
                realEncoder,
                mapper
        );

        String rawPassword = "validPassword123";
        CreateUserRequest request = new CreateUserRequest(
                "test",
                "test@example.com",
                rawPassword
        );
        User mappedUser = new User();

        when(mapper.toEntity(request)).thenReturn(mappedUser);
        when(repository.save(mappedUser)).thenReturn(mappedUser);

        User result = service.create(request);

        assertNotEquals(rawPassword, result.getPasswordHash());
        assertTrue(realEncoder.matches(rawPassword, result.getPasswordHash()));
        verify(repository).save(mappedUser);
    }

    @Test
    void testUpdate_ShouldReturnUpdatedUser() {
        Long id = 3L;
        UpdateUserRequest request = new UpdateUserRequest("updated-user");

        User existingUser = User.builder()
                .id(id)
                .username("old-user")
                .build();

        when(repository.findById(id))
                .thenReturn(Optional.of(existingUser));
        when(repository.save(existingUser))
                .thenReturn(existingUser);

        User result = service.update(id, request);

        assertSame(existingUser, result);
        verify(repository).findById(id);
        verify(mapper).updateEntity(request, existingUser);
        verify(repository).save(existingUser);
    }

    @Test
    void testUpdate_WhenUserDoesNotExist_ShouldThrowResourceNotFoundException() {
        Long id = 999L;
        UpdateUserRequest request = new UpdateUserRequest("updated-user");

        when(repository.findById(id)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> service.update(id, request)
        );

        assertEquals("User not found", exception.getMessage());
        verify(repository).findById(id);
        verify(mapper, never()).updateEntity(any(), any());
        verify(repository, never()).save(any());
    }

    @Test
    void testDeleteById() {
        doNothing().when(repository).deleteById(5L);

        service.deleteById(5L);

        verify(repository, times(1)).deleteById(5L);
    }
}
