package com.hgo_soft.device_for_all.users.repositories;

import com.hgo_soft.device_for_all.departments.entities.Department;
import com.hgo_soft.device_for_all.users.entities.Student;
import com.hgo_soft.device_for_all.users.entities.UserDetail;
import com.hgo_soft.device_for_all.common.repositories.RepositoryTestSetup;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
public class StudentRepositoryTest extends RepositoryTestSetup {

    @Autowired
    private StudentRepository repository;

    @Test
    void testSave() {
        Student student = Student.builder()
                .id(3L)
                .userDetail(UserDetail.builder().id(3L).build())
                .department(Department.builder().id(1L).build())
                .build();
        Student saved = repository.save(student);
        assertNotNull(saved.getId());
    }

    @Test
    void testFindById() {
        Optional<Student> result = repository.findById(1L);
        assertTrue(result.isPresent());
    }

    @Test
    void testFindAll() {
        List<Student> list = repository.findAll();
        assertTrue(list.size() >= 2);
    }

    @Test
    void testDeleteById() {
        repository.deleteById(1L);
        assertFalse(repository.findById(1L).isPresent());
    }
}
