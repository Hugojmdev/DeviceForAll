package com.hgo_soft.device_for_all.service;

import com.hgo_soft.device_for_all.entity.Employee;
import com.hgo_soft.device_for_all.repository.EmployeeRepository;
import com.hgo_soft.device_for_all.service.impl.EmployeeServiceImpl;
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

public class EmployeeServiceImplTest {
    private EmployeeRepository repository;
    private EmployeeServiceImpl service;

    @BeforeEach
    void setUp() {
        repository = mock(EmployeeRepository.class);
        service = new EmployeeServiceImpl(repository);
    }

    @Test
    void testFindAll_ShouldReturnEmployees() {
        List<Employee> employees = Arrays.asList(Employee.builder().id(1L).build(), Employee.builder().id(2L).build());
        when(repository.findAll()).thenReturn(employees);

        List<Employee> result = service.findAll();

        assertEquals(2, result.size());
        verify(repository).findAll();
    }

    @Test
    void testFindById_WhenFound() {
        Employee employee = Employee.builder().id(1L).build();
        when(repository.findById(1L)).thenReturn(Optional.of(employee));

        Optional<Employee> result = service.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
    }

    @Test
    void testFindById_WhenNotFound() {
        when(repository.findById(999L)).thenReturn(Optional.empty());

        Optional<Employee> result = service.findById(999L);

        assertFalse(result.isPresent());
    }

    @Test
    void testSave_ShouldReturnSavedEmployee() {
        Employee toSave = new Employee();
        Employee saved = Employee.builder().id(10L).build();

        when(repository.save(ArgumentMatchers.any())).thenReturn(saved);

        Employee result = service.save(toSave);

        assertNotNull(result);
        assertEquals(10L, result.getId());
    }

    @Test
    void testUpdate_ShouldReturnUpdatedEmployee() {
        Employee employee = Employee.builder().id(3L).build();
        when(repository.save(employee)).thenReturn(employee);

        Employee result = service.save(employee);

        assertEquals(3L, result.getId());
    }

    @Test
    void testDeleteById() {
        doNothing().when(repository).deleteById(5L);

        service.deleteById(5L);

        verify(repository, times(1)).deleteById(5L);
    }
}
