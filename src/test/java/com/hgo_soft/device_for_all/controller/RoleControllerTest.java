package com.hgo_soft.device_for_all.controller;

import com.hgo_soft.device_for_all.dto.RoleDto;
import com.hgo_soft.device_for_all.entity.Role;
import com.hgo_soft.device_for_all.mapper.RoleMapper;
import com.hgo_soft.device_for_all.service.RoleService;
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

public class RoleControllerTest {
    
    private RoleService service;
    private RoleMapper mapper;
    private RoleController controller;

    @BeforeEach
    void setUp() {
        service = mock(RoleService.class);
        mapper = mock(RoleMapper.class);
        controller = new RoleController(service, mapper);
    }

    @Test
    void testGetAll_ShouldReturnListOfRoles() {
        List<Role> roles = Arrays.asList(
                Role.builder().id(1L).build(),
                Role.builder().id(2L).build()
        );
        List<RoleDto> roleDtos = Arrays.asList(
                RoleDto.builder().id(1L).build(),
                RoleDto.builder().id(2L).build()
        );
        when(mapper.toEntityList(anyList())).thenReturn(roles);
        when(mapper.toDtoList(anyList())).thenReturn(roleDtos);

        when(service.findAll()).thenReturn(roles);

        ResponseEntity<List<RoleDto>> response = controller.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        verify(service).findAll();
    }

    @Test
    void testGetAll_ShouldReturnNoContent() {
        List<RoleDto> roleDtos = new ArrayList<>();
        List<Role> roles = new ArrayList<>();
        when(mapper.toEntityList(anyList())).thenReturn(roles);
        when(mapper.toDtoList(anyList())).thenReturn(roleDtos);
        when(service.findAll()).thenReturn(Collections.emptyList());

        ResponseEntity<List<RoleDto>> response = controller.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testGetById_Found() {
        long id = 1L;
        RoleDto inputDto = RoleDto.builder().id(id).build();
        Role entityToFound = Role.builder().id(id).build();
        RoleDto dtoToFound = RoleDto.builder().id(id).build();
        when(mapper.toEntity(inputDto)).thenReturn(entityToFound);
        when(mapper.toDto(entityToFound)).thenReturn(dtoToFound);
        when(service.findById(any())).thenReturn(Optional.of(entityToFound));

        ResponseEntity<RoleDto> response = controller.getById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(id, response.getBody().getId());
    }

    @Test
    void testGetById_NotFound() {
        when(service.findById(999L)).thenReturn(Optional.empty());

        ResponseEntity<RoleDto> response = controller.getById(999L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testCreate_ShouldReturnCreatedRole() {
        long id = 10L;
        RoleDto inputDto = new RoleDto();
        Role savedEntity = Role.builder().id(id).build();
        RoleDto savedDto = RoleDto.builder().id(id).build();
        // simulate mapper and persistence
        when(mapper.toEntity(inputDto)).thenReturn(savedEntity);
        when(mapper.toDto(savedEntity)).thenReturn(savedDto);
        when(service.save(any())).thenReturn(savedEntity);
        ResponseEntity<RoleDto> response = controller.create(inputDto);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("/api/roles/10", Objects.requireNonNull(response.getHeaders().getLocation()).toString());
    }

    @Test
    void testUpdate_ShouldReturnUpdatedRole() {
        Long id = 5L;
        RoleDto inputDto = RoleDto.builder().id(id).build();
        Role savedEntity = Role.builder().id(id).build();
        RoleDto savedDto = RoleDto.builder().id(id).build();
        when(mapper.toEntity(inputDto)).thenReturn(savedEntity);
        when(mapper.toDto(savedEntity)).thenReturn(savedDto);
        when(service.save(any())).thenReturn(savedEntity);
        ResponseEntity<RoleDto> response = controller.update(id, inputDto);
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
