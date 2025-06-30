package com.hgo_soft.device_for_all.repository;

import com.hgo_soft.device_for_all.entity.Department;
import com.hgo_soft.device_for_all.entity.Employee;
import com.hgo_soft.device_for_all.entity.UserDetail;
import com.hgo_soft.device_for_all.enums.EmployeeType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
public class EmployeeRepositoryTest extends RepositoryTestSetup {

    @Autowired
    private EmployeeRepository repository;

    @Test
    void testSave() {
        Employee employee = Employee.builder()
                .id(1L)
                .user(UserDetail.builder().id(1L).build())
                .department(Department.builder().id(1L).build())
                .type(EmployeeType.ADMIN)
                .build();
        Employee saved = repository.save(employee);
        assertNotNull(saved.getId());
    }

    @Test
    void testFindById() {
        Optional<Employee> result = repository.findById(1L);
        assertTrue(result.isPresent());
    }

    @Test
    void testFindAll() {
        List<Employee> list = repository.findAll();
        assertTrue(list.size() >= 2);
    }

    @Test
    void testDeleteById() {
        repository.deleteById(1L);
        assertFalse(repository.findById(1L).isPresent());
    }
}
