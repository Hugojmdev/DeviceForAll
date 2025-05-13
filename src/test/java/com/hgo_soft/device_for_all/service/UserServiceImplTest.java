package com.hgo_soft.device_for_all.service;

import com.hgo_soft.device_for_all.entity.User;
import com.hgo_soft.device_for_all.repository.UserRepository;
import com.hgo_soft.device_for_all.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {
    private UserRepository repository;
    private UserServiceImpl service;

    @BeforeEach
    void setUp() {
        repository = mock(UserRepository.class);
        service = new UserServiceImpl(repository);
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
    void testSave_ShouldReturnSavedUser() {
        User toSave = new User();
        User saved = User.builder().id(10L).build();

        when(repository.save(ArgumentMatchers.any())).thenReturn(saved);

        User result = service.save(toSave);

        assertNotNull(result);
        assertEquals(10L, result.getId());
    }

    @Test
    void testUpdate_ShouldReturnUpdatedUser() {
        User user = User.builder().id(3L).build();
        when(repository.save(user)).thenReturn(user);

        User result = service.save(user);

        assertEquals(3L, result.getId());
    }

    @Test
    void testDeleteById() {
        doNothing().when(repository).deleteById(5L);

        service.deleteById(5L);

        verify(repository, times(1)).deleteById(5L);
    }
}
