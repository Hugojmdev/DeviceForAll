package com.hgo_soft.device_for_all.repository;

import com.hgo_soft.device_for_all.entity.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
public class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository repository;

    @Test
    void testSave() {
        Employee employee = Employee.builder().email("test@example.com").build();
        Employee saved = repository.save(employee);
        assertNotNull(saved.getId());
    }

    @Test
    void testFindById() {
        Employee employee = Employee.builder().email("test@example.com").build();
        Employee saved = repository.save(employee);
        Optional<Employee> result = repository.findById(saved.getId());
        assertTrue(result.isPresent());
    }

    @Test
    void testFindAll() {
        repository.save(Employee.builder().email("one@example.com").build());
        repository.save(Employee.builder().email("two@example.com").build());
        List<Employee> list = repository.findAll();
        assertTrue(list.size() >= 2);
    }

    @Test
    void testDeleteById() {
        Employee employee = Employee.builder().email("delete@example.com").build();
        Employee saved = repository.save(employee);
        repository.deleteById(saved.getId());
        assertFalse(repository.findById(saved.getId()).isPresent());
    }
}
