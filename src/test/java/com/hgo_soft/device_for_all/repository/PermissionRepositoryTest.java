package com.hgo_soft.device_for_all.repository;

import com.hgo_soft.device_for_all.entity.Permission;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

@DataJpaTest
@ActiveProfiles("test")
public class PermissionRepositoryTest extends RepositoryTestSetup {

    @Autowired
    private PermissionRepository repository;

    @Test
    void testSave() {
        Permission permission = Permission.builder()
                .name("EDIT_DEVICES")
                .description("Can edit Devices")
                .build();
        Permission saved = repository.save(permission);

        assertNotNull(saved.getId());
    }

    @Test
    void testFindById() {
        Optional<Permission> result = repository.findById(1L);
        assertTrue(result.isPresent());
    }

    @Test
    void testFindAll() {
        List<Permission> list = repository.findAll();
        assertFalse(list.isEmpty());
    }

    @Test
    void testDeleteById() {
        repository.deleteById(2L);
        assertFalse(repository.findById(2L).isPresent());
    }
}
