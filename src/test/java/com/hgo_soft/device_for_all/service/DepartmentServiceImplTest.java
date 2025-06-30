package com.hgo_soft.device_for_all.service;

import com.hgo_soft.device_for_all.entity.Department;
import com.hgo_soft.device_for_all.repository.DepartmentRepository;
import com.hgo_soft.device_for_all.service.impl.DepartmentServiceImpl;
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

public class DepartmentServiceImplTest {
    private DepartmentRepository repository;
    private DepartmentServiceImpl service;

    @BeforeEach
    void setUp() {
        repository = mock(DepartmentRepository.class);
        service = new DepartmentServiceImpl(repository);
    }

    @Test
    void testFindAll_ShouldReturnDepartments() {
        List<Department> departments = Arrays.asList(Department.builder().id(1L).build(), Department.builder().id(2L).build());
        when(repository.findAll()).thenReturn(departments);

        List<Department> result = service.findAll();

        assertEquals(2, result.size());
        verify(repository).findAll();
    }

    @Test
    void testFindById_WhenFound() {
        Department department = Department.builder().id(1L).build();
        when(repository.findById(1L)).thenReturn(Optional.of(department));

        Optional<Department> result = service.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
    }

    @Test
    void testFindById_WhenNotFound() {
        when(repository.findById(999L)).thenReturn(Optional.empty());

        Optional<Department> result = service.findById(999L);

        assertFalse(result.isPresent());
    }

    @Test
    void testSave_ShouldReturnSavedDepartment() {
        Department toSave = new Department();
        Department saved = Department.builder().id(10L).build();

        when(repository.save(ArgumentMatchers.any())).thenReturn(saved);

        Department result = service.save(toSave);

        assertNotNull(result);
        assertEquals(10L, result.getId());
    }

    @Test
    void testUpdate_ShouldReturnUpdatedDepartment() {
        Department department = Department.builder().id(3L).build();
        when(repository.save(department)).thenReturn(department);

        Department result = service.save(department);

        assertEquals(3L, result.getId());
    }

    @Test
    void testDeleteById() {
        doNothing().when(repository).deleteById(5L);

        service.deleteById(5L);

        verify(repository, times(1)).deleteById(5L);
    }
}
