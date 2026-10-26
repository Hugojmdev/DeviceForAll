package com.hgo_soft.device_for_all.auth.repositories;

import com.hgo_soft.device_for_all.auth.entities.Permission;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
public class PermissionRepositoryTest  {

    @Autowired
    private PermissionRepository repository;

    @Test
    void testFindById() {
        Permission permission = Permission.builder()
                .name("TEST_PERMISSION")
                .build();
        repository.saveAndFlush(permission);
        Long permissionId = permission.getId();
        Optional<Permission> result = repository.findById(permissionId);
        assertTrue(result.isPresent());
    }

    @Test
    void testFindAll() {
        Permission permission = Permission.builder()
                .name("TEST_PERMISSION")
                .build();
        repository.saveAndFlush(permission);
        Permission permission2 = Permission.builder()
                .name("TEST_PERMISSION2")
                .build();

        repository.saveAndFlush(permission2);
        List<Permission> list = repository.findAll();
        assertFalse(list.isEmpty());
    }

    @Test
    void testFindByName() {
        Permission permission = Permission.builder()
                .name("DEVICE_READ")
                .build();
        repository.saveAndFlush(permission);

        Optional<Permission> result = repository.findByName("DEVICE_READ");

        assertTrue(result.isPresent());
        assertEquals("DEVICE_READ", result.get().getName());
    }

    @Test
    void testFindByNameNotFound() {
        Optional<Permission> result = repository.findByName("UNKNOWN_PERMISSION");

        assertTrue(result.isEmpty());
    }

    @Test
    void testFindAllByNameIn() {
        Permission permission = Permission.builder()
                .name("DEVICE_READ")
                .build();
        Permission permission2 = Permission.builder()
                .name("DEVICE_CREATE")
                .build();
        Permission permission3 = Permission.builder()
                .name("DEVICE_UPDATE")
                .build();
        repository.saveAllAndFlush(List.of(permission, permission2, permission3));

        List<Permission> result = repository.findAllByNameIn(
                Set.of("DEVICE_READ", "DEVICE_CREATE")
        );

        assertEquals(2, result.size());
        assertTrue(result.stream()
                .map(Permission::getName)
                .allMatch(Set.of("DEVICE_READ", "DEVICE_CREATE")::contains));
    }
}
