package com.hgo_soft.device_for_all.controller;

import com.hgo_soft.device_for_all.dto.UserDto;
import com.hgo_soft.device_for_all.entity.User;
import com.hgo_soft.device_for_all.mapper.UserMapper;
import com.hgo_soft.device_for_all.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

public class UserControllerTest {
    
    private UserService service;
    private UserMapper mapper;
    private UserController controller;

    @BeforeEach
    void setUp() {
        service = mock(UserService.class);
        mapper = mock(UserMapper.class);
        controller = new UserController(service, mapper);
    }

    @Test
    void testGetAll_ShouldReturnListOfUsers() {
        List<User> users = Arrays.asList(
                User.builder().id(1L).build(),
                User.builder().id(2L).build()
        );
        List<UserDto> userDtos = Arrays.asList(
                UserDto.builder().id(1L).build(),
                UserDto.builder().id(2L).build()
        );
        when(mapper.toEntityList(anyList())).thenReturn(users);
        when(mapper.toDtoList(anyList())).thenReturn(userDtos);

        when(service.findAll()).thenReturn(users);

        ResponseEntity<List<UserDto>> response = controller.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        verify(service).findAll();
    }

    @Test
    void testGetAll_ShouldReturnNoContent() {
        List<UserDto> userDtos = new ArrayList<>();
        List<User> users = new ArrayList<>();
        when(mapper.toEntityList(anyList())).thenReturn(users);
        when(mapper.toDtoList(anyList())).thenReturn(userDtos);
        when(service.findAll()).thenReturn(Collections.emptyList());

        ResponseEntity<List<UserDto>> response = controller.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testGetById_Found() {
        long id = 1L;
        UserDto inputDto = UserDto.builder().id(id).build();
        User entityToFound = User.builder().id(id).build();
        UserDto dtoToFound = UserDto.builder().id(id).build();
        when(mapper.toEntity(inputDto)).thenReturn(entityToFound);
        when(mapper.toDto(entityToFound)).thenReturn(dtoToFound);
        when(service.findById(any())).thenReturn(Optional.of(entityToFound));

        ResponseEntity<UserDto> response = controller.getById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(id, response.getBody().getId());
    }

    @Test
    void testGetById_NotFound() {
        when(service.findById(999L)).thenReturn(Optional.empty());

        ResponseEntity<UserDto> response = controller.getById(999L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testCreate_ShouldReturnCreatedUser() {
        long id = 10L;
        UserDto inputDto = new UserDto();
        User savedEntity = User.builder().id(id).build();
        UserDto savedDto = UserDto.builder().id(id).build();
        // simulate mapper and persistence
        when(mapper.toEntity(inputDto)).thenReturn(savedEntity);
        when(mapper.toDto(savedEntity)).thenReturn(savedDto);
        when(service.save(any())).thenReturn(savedEntity);
        ResponseEntity<UserDto> response = controller.create(inputDto);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("/api/users/10", Objects.requireNonNull(response.getHeaders().getLocation()).toString());
    }

    @Test
    void testUpdate_ShouldReturnUpdatedUser() {
        Long id = 5L;
        UserDto inputDto = UserDto.builder().id(id).build();
        User savedEntity = User.builder().id(id).build();
        UserDto savedDto = UserDto.builder().id(id).build();
        when(mapper.toEntity(inputDto)).thenReturn(savedEntity);
        when(mapper.toDto(savedEntity)).thenReturn(savedDto);
        when(service.save(any())).thenReturn(savedEntity);
        ResponseEntity<UserDto> response = controller.update(id, inputDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(id, response.getBody().getId());
    }

    @Test
    void testDelete_ShouldReturnOk() {
        doNothing().when(service).deleteById(1L);

        ResponseEntity<Void> response = controller.delete(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(service).deleteById(1L);
    }
}
