package com.hgo_soft.device_for_all.controller;
import com.hgo_soft.device_for_all.dto.EmployeeDto;
import com.hgo_soft.device_for_all.entity.Employee;
import com.hgo_soft.device_for_all.mapper.EmployeeMapper;
import com.hgo_soft.device_for_all.mapper.EmployeeMapperImpl;
import com.hgo_soft.device_for_all.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmployeeControllerTest {

    private EmployeeService service;
    private EmployeeController controller;

    @BeforeEach
    void setUp() {
        service = mock(EmployeeService.class);
        EmployeeMapper mapper = new EmployeeMapperImpl();
        controller = new EmployeeController(service, mapper);
    }

    @Test
    void testGetAll_ShouldReturnListOfEmployees() {
        List<Employee> employees = Arrays.asList(
                Employee.builder().id(1L).build(),
                Employee.builder().id(2L).build()
        );
        when(service.findAll()).thenReturn(employees);

        ResponseEntity<List<EmployeeDto>> response = controller.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        verify(service).findAll();
    }

    @Test
    void testGetAll_ShouldReturnNoContent() {
        ResponseEntity<List<EmployeeDto>> response = controller.getAll();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isEmpty());
    }

    @Test
    void testGetById_Found() {
        long id = 1L;
        Employee entityToFound = Employee.builder().id(id).build();
        when(service.findById(any())).thenReturn(Optional.of(entityToFound));

        ResponseEntity<EmployeeDto> response = controller.getById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(id, response.getBody().getId());
    }

    @Test
    void testGetById_NotFound() {
        when(service.findById(999L)).thenReturn(Optional.empty());

        ResponseEntity<EmployeeDto> response = controller.getById(999L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testCreate_ShouldReturnCreatedEmployee() {
        long id = 10L;
        EmployeeDto inputDto = new EmployeeDto();
        Employee savedEntity = Employee.builder().id(id).build();
        when(service.save(any())).thenReturn(savedEntity);
        ResponseEntity<EmployeeDto> response = controller.create(inputDto);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("/api/employees/10", Objects.requireNonNull(response.getHeaders().getLocation()).toString());
    }

    @Test
    void testUpdate_ShouldReturnUpdatedEmployee() {
        Long id = 5L;
        EmployeeDto inputDto = EmployeeDto.builder().id(id).build();
        Employee savedEntity = Employee.builder().id(id).build();
        when(service.save(any())).thenReturn(savedEntity);
        ResponseEntity<EmployeeDto> response = controller.update(id, inputDto);
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