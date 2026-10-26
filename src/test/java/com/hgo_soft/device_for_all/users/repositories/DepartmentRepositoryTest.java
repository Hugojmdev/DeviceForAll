package com.hgo_soft.device_for_all.users.repositories;

import com.hgo_soft.device_for_all.users.entities.Department;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
public class DepartmentRepositoryTest {
    @Autowired
    private DepartmentRepository repository;

    @Test
    void testSave() {
        Department department = buildDepartment();
        Department saved = repository.save(department);
        assertNotNull(saved.getId());
    }

    @Test
    void testFindById() {
        Department department = buildDepartment();
        repository.saveAndFlush(department);
        Long departmentId = department.getId();
        Optional<Department> savedDepartment = repository.findById(departmentId);
        assertTrue(savedDepartment.isPresent());
    }

    @Test
    void testFindAll() {
        Department department = buildDepartment();
        repository.saveAndFlush(department);
        Department department2 = buildDepartment();
        repository.saveAndFlush(department2);
        List<Department> departments = repository.findAll();
        assertFalse(departments.isEmpty());
        assertEquals(2, departments.size());
    }

    @Test
    void testDeleteById() {
        repository.deleteById(1L);
        assertFalse(repository.findById(1L).isPresent());
    }

    private Department buildDepartment() {
        return Department.builder()
                .name("tes-department")
                .build();
    }
}
