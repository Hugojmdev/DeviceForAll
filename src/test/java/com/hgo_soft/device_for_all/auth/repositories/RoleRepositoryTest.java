package com.hgo_soft.device_for_all.auth.repositories;

import com.hgo_soft.device_for_all.auth.entities.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ActiveProfiles("test")
public class RoleRepositoryTest {

    @Autowired
    private RoleRepository repository;

    @Test
    void testFindByName() {
        Role role = Role.builder()
                .name("ADMIN")
                .build();
        repository.saveAndFlush(role);

        Optional<Role> result = repository.findByName("ADMIN");

        assertTrue(result.isPresent());
        assertEquals("ADMIN", result.get().getName());
    }

    @Test
    void testFindByNameNotFound() {
        Optional<Role> result = repository.findByName("UNKNOWN_ROLE");

        assertTrue(result.isEmpty());
    }

    @Test
    void testFindAllByNameIn() {
        Role role = Role.builder()
                .name("ADMIN")
                .build();
        Role role2 = Role.builder()
                .name("STAFF")
                .build();
        Role role3 = Role.builder()
                .name("BORROWER")
                .build();
        repository.saveAllAndFlush(List.of(role, role2, role3));

        List<Role> result = repository.findAllByNameIn(Set.of("ADMIN", "STAFF"));

        assertEquals(2, result.size());
        assertTrue(result.stream()
                .map(Role::getName)
                .allMatch(Set.of("ADMIN", "STAFF")::contains));
    }
}
