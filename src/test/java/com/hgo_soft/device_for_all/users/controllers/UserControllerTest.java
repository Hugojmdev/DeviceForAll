package com.hgo_soft.device_for_all.users.controllers;

import com.hgo_soft.device_for_all.common.exceptions.handlers.GlobalExceptionHandler;
import com.hgo_soft.device_for_all.common.exceptions.ResourceNotFoundException;
import com.hgo_soft.device_for_all.users.dtos.CreateUserRequest;
import com.hgo_soft.device_for_all.users.dtos.UpdateUserRequest;
import com.hgo_soft.device_for_all.users.dtos.UserResponse;
import com.hgo_soft.device_for_all.users.entities.User;
import com.hgo_soft.device_for_all.users.mappers.UserMapper;
import com.hgo_soft.device_for_all.users.mappers.UserMapperImpl;
import com.hgo_soft.device_for_all.users.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest {
    
    private UserService service;
    private UserController controller;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        service = mock(UserService.class);
        UserMapper mapper = new UserMapperImpl();
        controller = new UserController(service, mapper);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void testGetAll_ShouldReturnListOfUsers() {
        List<User> users = Arrays.asList(
                User.builder().id(1L).build(),
                User.builder().id(2L).build()
        );
        when(service.findAll()).thenReturn(users);

        ResponseEntity<List<UserResponse>> response = controller.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        verify(service).findAll();
    }

    @Test
    void testGetAll_ShouldReturnNoContent() {
        when(service.findAll()).thenReturn(Collections.emptyList());

        ResponseEntity<List<UserResponse>> response = controller.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isEmpty());
    }

    @Test
    void testGetById_Found() {
        long id = 1L;
        User entityToFound = User.builder().id(id).build();
        when(service.findById(any())).thenReturn(Optional.of(entityToFound));

        ResponseEntity<UserResponse> response = controller.getById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(id, response.getBody().id());
    }

    @Test
    void testGetById_NotFound() {
        when(service.findById(999L)).thenReturn(Optional.empty());

        ResponseEntity<UserResponse> response = controller.getById(999L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testCreate_ShouldReturnCreatedUser() {
        long id = 10L;
        CreateUserRequest createUserRequest = new CreateUserRequest(
                "test",
                "test@example.com",
                "validPassword123"
        );
        User savedEntity = User.builder().id(id).build();
        when(service.create(any())).thenReturn(savedEntity);

        ResponseEntity<UserResponse> response = controller.create(createUserRequest);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(id, response.getBody().id());
        assertEquals("/api/users/10", Objects.requireNonNull(response.getHeaders().getLocation()).toString());
        verify(service).create(createUserRequest);
    }

    @Test
    void testUpdate_ShouldReturnUpdatedUser() {
        Long id = 5L;
        UpdateUserRequest updateUserRequest = new UpdateUserRequest("testUser");
        User savedEntity = User.builder().id(id).build();
        when(service.update(id, updateUserRequest))
                .thenReturn(savedEntity);
        ResponseEntity<UserResponse> response = controller.update(id, updateUserRequest);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(id, response.getBody().id());
        verify(service).update(id, updateUserRequest);
    }

    @Test
    void testUpdate_WhenUserDoesNotExist_ShouldReturnNotFound() throws Exception {
        Long id = 999L;
        UpdateUserRequest request = new UpdateUserRequest("updated-user");

        when(service.update(id, request))
                .thenThrow(new ResourceNotFoundException("User not found"));

        mockMvc.perform(put("/api/users/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "username": "updated-user"
                                }
                                """))
                .andExpect(status().isNotFound())
                .andExpect(content().string("User not found"));

        verify(service).update(id, request);
    }

    @Test
    void testDelete_ShouldReturnOk() {
        doNothing().when(service).deleteById(1L);

        ResponseEntity<Void> response = controller.delete(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(service).deleteById(1L);
    }
}
