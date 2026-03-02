package com.hgo_soft.device_for_all.controller;

import com.hgo_soft.device_for_all.dto.DepartmentDto;
import com.hgo_soft.device_for_all.entity.Department;
import com.hgo_soft.device_for_all.mapper.DepartmentMapper;
import com.hgo_soft.device_for_all.mapper.DepartmentMapperImpl;
import com.hgo_soft.device_for_all.service.DepartmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

public class DepartmentControllerTest {

    private DepartmentService service;
    private DepartmentController controller;

    @BeforeEach
    void setUp() {
        service = mock(DepartmentService.class);
        DepartmentMapper mapper = new DepartmentMapperImpl();
        controller = new DepartmentController(service, mapper);
    }

    @Test
    void testGetAll_ShouldReturnListOfDepartments() {
        List<Department> departments = Arrays.asList(
                Department.builder().id(1L).build(),
                Department.builder().id(2L).build()
        );

        when(service.findAll()).thenReturn(departments);

        ResponseEntity<List<DepartmentDto>> response = controller.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        verify(service).findAll();
    }

    @Test
    void testGetAll_ShouldReturnNoContent() {
        when(service.findAll()).thenReturn(Collections.emptyList());

        ResponseEntity<List<DepartmentDto>> response = controller.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isEmpty());
    }

    @Test
    void testGetById_Found() {
        long id = 1L;
        Department entityToFound = Department.builder().id(id).build();
        when(service.findById(any())).thenReturn(Optional.of(entityToFound));

        ResponseEntity<DepartmentDto> response = controller.getById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(id, response.getBody().getId());
    }

    @Test
    void testGetById_NotFound() {
        when(service.findById(999L)).thenReturn(Optional.empty());

        ResponseEntity<DepartmentDto> response = controller.getById(999L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testCreate_ShouldReturnCreatedDepartment() {
        long id = 10L;
        DepartmentDto inputDto = new DepartmentDto();
        Department savedEntity = Department.builder().id(id).build();
        when(service.save(any())).thenReturn(savedEntity);
        ResponseEntity<DepartmentDto> response = controller.create(inputDto);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("/api/departments/10", Objects.requireNonNull(response.getHeaders().getLocation()).toString());
    }

    @Test
    void testUpdate_ShouldReturnUpdatedDepartment() {
        Long id = 5L;
        DepartmentDto inputDto = DepartmentDto.builder().id(id).build();
        Department savedEntity = Department.builder().id(id).build();
        when(service.save(any())).thenReturn(savedEntity);
        ResponseEntity<DepartmentDto> response = controller.update(id, inputDto);
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
