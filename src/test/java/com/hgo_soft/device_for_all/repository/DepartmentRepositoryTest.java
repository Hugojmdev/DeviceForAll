package com.hgo_soft.device_for_all.repository;

import com.hgo_soft.device_for_all.entity.Department;
import com.hgo_soft.device_for_all.entity.UserDetail;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
public class DepartmentRepositoryTest extends RepositoryTestSetup{
    @Autowired
    private DepartmentRepository repository;

    @Test
    void testSave() {
        Department department = Department.builder()
                .id(1L)
                .name("Philosophy")
                .build();
        Department saved = repository.save(department);
        assertNotNull(saved.getId());
    }

    @Test
    void testFindById() {
        Optional<Department> result = repository.findById(1L);
        assertTrue(result.isPresent());
    }

    @Test
    void testFindAll() {
        List<Department> list = repository.findAll();
        assertTrue(list.size() >= 1);
    }

    @Test
    void testDeleteById() {
        repository.deleteById(1L);
        assertFalse(repository.findById(1L).isPresent());
    }
}
