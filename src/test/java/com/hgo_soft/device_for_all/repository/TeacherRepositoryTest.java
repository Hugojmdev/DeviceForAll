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
public class TeacherRepositoryTest extends RepositoryTestSetup {
    
    @Autowired
    private TeacherRepository repository;

    @Test
    void testSave() {
        Teacher teacher = Teacher.builder().id(1L).build();
        Teacher saved = repository.save(teacher);
        assertNotNull(saved.getId());
    }

    @Test
    void testFindById() {
        Optional<Teacher> result = repository.findById(1L);
        assertTrue(result.isPresent());
    }

    @Test
    void testFindAll() {
        List<Teacher> list = repository.findAll();
        assertTrue(list.size() >= 1);
    }

    @Test
    void testDeleteById() {
        repository.deleteById(1L);
        assertFalse(repository.findById(1L).isPresent());
    }
}
