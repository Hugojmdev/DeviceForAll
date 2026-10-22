package com.hgo_soft.device_for_all.users.repositories;

import com.hgo_soft.device_for_all.auth.entities.Permission;
import com.hgo_soft.device_for_all.auth.entities.Role;
import com.hgo_soft.device_for_all.auth.repositories.PermissionRepository;
import com.hgo_soft.device_for_all.auth.repositories.RoleRepository;
import com.hgo_soft.device_for_all.users.entities.User;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
public class UserRepositoryTest {

    @Autowired
    private UserRepository repository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PermissionRepository permissionRepository;
    @Autowired
    private EntityManager entityManager;

    @Test
    void testSave() {
        User saved = repository.saveAndFlush(createUser("save"));

        assertNotNull(saved.getId());
    }

    @Test
    void testSave_ShouldSetCreatedAtBeforeInsert() {
        User user = createUser("created-at");

        assertNull(user.getCreatedAt());

        User saved = repository.saveAndFlush(user);

        assertNotNull(saved.getCreatedAt());
    }

    @Test
    void testFindById() {
        User saved = repository.saveAndFlush(createUser("find"));

        Optional<User> result = repository.findById(saved.getId());

        assertTrue(result.isPresent());
    }

    @Test
    void testFindAll() {
        repository.save(createUser("first"));
        repository.save(createUser("second"));
        repository.flush();

        List<User> list = repository.findAll();

        assertEquals(2, list.size());
    }

    @Test
    void testDeleteById() {
        User saved = repository.saveAndFlush(createUser("delete"));

        repository.deleteById(saved.getId());
        repository.flush();

        assertFalse(repository.findById(saved.getId()).isPresent());
    }

    @Test
    void findUserWithRolesAndPermission_shouldReturnUserWithRolesAndPermissions() {
        Permission permission = permissionRepository.save(Permission.builder().name("DEVICE_READ").build());
        Role role = roleRepository.save(Role.builder().name("SUPERVISOR")
                .permissions(new HashSet<>(Set.of(permission))).build());
        User newUser = createUser("super");
        newUser.getRoles().add(role);
        repository.saveAndFlush(newUser);
        entityManager.clear();

        User savedUser = repository.findRolesAndPermissionsByUsername(newUser.getUsername()).orElseThrow();

        assertFalse(savedUser.getRoles().isEmpty());
        assertTrue(savedUser.getRoles().stream().anyMatch(r -> !r.getPermissions().isEmpty()));
    }

    private User createUser(String suffix) {
        return User.builder()
                .username("user-" + suffix)
                .email(suffix + "@example.com")
                .passwordHash("hashed-password")
                .enabled(true)
                .build();
    }
}
