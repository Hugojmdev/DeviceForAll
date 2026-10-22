package com.hgo_soft.device_for_all.auth.controllers;

import com.hgo_soft.device_for_all.auth.dtos.RoleResponse;
import com.hgo_soft.device_for_all.auth.entities.Role;
import com.hgo_soft.device_for_all.auth.mappers.RoleMapper;
import com.hgo_soft.device_for_all.auth.mappers.RoleMapperImpl;
import com.hgo_soft.device_for_all.auth.services.RoleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

public class RoleControllerTest {


    private RoleService service;
    private RoleController controller;

    @BeforeEach
    void setUp() {
        service = mock(RoleService.class);
        RoleMapper mapper = new RoleMapperImpl();
        controller = new RoleController(service, mapper);
    }

    @Test
    void testGetAll_ShouldReturnListOfRoles() {
        List<Role> roles = Arrays.asList(
                Role.builder().id(1L).build(),
                Role.builder().id(2L).build()
        );
        when(service.findAll()).thenReturn(roles);

        ResponseEntity<List<RoleResponse>> response = controller.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        verify(service).findAll();
    }

    @Test
    void testGetAll_ShouldReturnNoContent() {
        ResponseEntity<List<RoleResponse>> response = controller.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isEmpty());
    }

    @Test
    void testGetById_Found() {
        long id = 1L;
        Role entityToFound = Role.builder().id(id).build();
        when(service.findById(any())).thenReturn(Optional.of(entityToFound));

        ResponseEntity<RoleResponse> response = controller.getById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(id, response.getBody().id());
    }

    @Test
    void testGetById_NotFound() {
        when(service.findById(999L)).thenReturn(Optional.empty());

        ResponseEntity<RoleResponse> response = controller.getById(999L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
