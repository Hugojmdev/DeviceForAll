package com.hgo_soft.device_for_all.repository;

import com.hgo_soft.device_for_all.entity.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
public class RoleRepositoryTest {

    @Autowired
    private RoleRepository repository;

    @Test
    void testSave() {
        Role role = Role.builder().name("admin").build();
        Role saved = repository.save(role);
        assertNotNull(saved.getId());
    }

    @Test
    void testFindById() {
        Role role = Role.builder().name("admin").build();
        Role saved = repository.save(role);
        Optional<Role> result = repository.findById(saved.getId());
        assertTrue(result.isPresent());
    }

    @Test
    void testFindAll() {
        repository.save(Role.builder().name("admin").build());
        repository.save(Role.builder().name("employee").build());
        List<Role> list = repository.findAll();
        assertTrue(list.size() >= 2);
    }

    @Test
    void testDeleteById() {
        Role role = Role.builder().name("admin").build();
        Role saved = repository.save(role);
        repository.deleteById(saved.getId());
        assertFalse(repository.findById(saved.getId()).isPresent());
    }
    
}
