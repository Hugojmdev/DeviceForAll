package com.hgo_soft.device_for_all.controller;

import com.hgo_soft.device_for_all.dto.PermissionDto;
import com.hgo_soft.device_for_all.entity.Permission;
import com.hgo_soft.device_for_all.mapper.PermissionMapper;
import com.hgo_soft.device_for_all.mapper.PermissionMapperImpl;
import com.hgo_soft.device_for_all.service.PermissionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

public class PermissionControllerTest {

    private PermissionService service;
    private PermissionController controller;

    @BeforeEach
    void setUp() {
        service = mock(PermissionService.class);
        PermissionMapper mapper = new PermissionMapperImpl();
        controller = new PermissionController(service, mapper);
    }

    @Test
    void testGetAll_ShouldReturnListOfPermissions() {
        List<Permission> permissions = Arrays.asList(
                Permission.builder().id(1L).build(),
                Permission.builder().id(2L).build()
        );
        when(service.findAll()).thenReturn(permissions);

        ResponseEntity<List<PermissionDto>> response = controller.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        verify(service).findAll();
    }

    @Test
    void testGetAll_ShouldReturnNoContent() {
        ResponseEntity<List<PermissionDto>> response = controller.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isEmpty());;
    }

    @Test
    void testGetById_Found() {
        long id = 1L;
        PermissionDto inputDto = PermissionDto.builder().id(id).build();
        Permission entityToFound = Permission.builder().id(id).build();
        when(service.findById(any())).thenReturn(Optional.of(entityToFound));

        ResponseEntity<PermissionDto> response = controller.getById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(id, response.getBody().getId());
    }

    @Test
    void testGetById_NotFound() {
        when(service.findById(999L)).thenReturn(Optional.empty());

        ResponseEntity<PermissionDto> response = controller.getById(999L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testCreate_ShouldReturnCreatedPermission() {
        long id = 10L;
        PermissionDto inputDto = new PermissionDto();
        Permission savedEntity = Permission.builder().id(id).build();
        when(service.save(any())).thenReturn(savedEntity);
        ResponseEntity<PermissionDto> response = controller.create(inputDto);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("/api/permissions/10", Objects.requireNonNull(response.getHeaders().getLocation()).toString());
    }

    @Test
    void testUpdate_ShouldReturnUpdatedPermission() {
        Long id = 5L;
        PermissionDto inputDto = PermissionDto.builder().id(id).build();
        Permission savedEntity = Permission.builder().id(id).build();
        when(service.save(any())).thenReturn(savedEntity);
        ResponseEntity<PermissionDto> response = controller.update(id, inputDto);
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