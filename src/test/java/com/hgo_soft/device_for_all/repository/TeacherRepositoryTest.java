package com.hgo_soft.device_for_all.repository;

import com.hgo_soft.device_for_all.entity.Teacher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
public class TeacherRepositoryTest {
    
    @Autowired
    private TeacherRepository repository;

    @Test
    void testSave() {
        Teacher teacher = Teacher.builder().email("test@example.com").build();
        Teacher saved = repository.save(teacher);
        assertNotNull(saved.getId());
    }

    @Test
    void testFindById() {
        Teacher teacher = Teacher.builder().email("test@example.com").build();
        Teacher saved = repository.save(teacher);
        Optional<Teacher> result = repository.findById(saved.getId());
        assertTrue(result.isPresent());
    }

    @Test
    void testFindAll() {
        repository.save(Teacher.builder().email("one@example.com").build());
        repository.save(Teacher.builder().email("two@example.com").build());
        List<Teacher> list = repository.findAll();
        assertTrue(list.size() >= 2);
    }

    @Test
    void testDeleteById() {
        Teacher teacher = Teacher.builder().email("delete@example.com").build();
        Teacher saved = repository.save(teacher);
        repository.deleteById(saved.getId());
        assertFalse(repository.findById(saved.getId()).isPresent());
    }
}
