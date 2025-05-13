package com.hgo_soft.device_for_all.repository;

import com.hgo_soft.device_for_all.entity.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
public class StudentRepositoryTest {

    @Autowired
    private StudentRepository repository;

    @Test
    void testSave() {
        Student student = Student.builder().email("test@example.com").build();
        Student saved = repository.save(student);
        assertNotNull(saved.getId());
    }

    @Test
    void testFindById() {
        Student student = Student.builder().email("test@example.com").build();
        Student saved = repository.save(student);
        Optional<Student> result = repository.findById(saved.getId());
        assertTrue(result.isPresent());
    }

    @Test
    void testFindAll() {
        repository.save(Student.builder().email("one@example.com").build());
        repository.save(Student.builder().email("two@example.com").build());
        List<Student> list = repository.findAll();
        assertTrue(list.size() >= 2);
    }

    @Test
    void testDeleteById() {
        Student student = Student.builder().email("delete@example.com").build();
        Student saved = repository.save(student);
        repository.deleteById(saved.getId());
        assertFalse(repository.findById(saved.getId()).isPresent());
    }
}
