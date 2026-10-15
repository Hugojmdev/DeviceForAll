package com.hgo_soft.device_for_all.users.repositories;

import com.hgo_soft.device_for_all.users.entities.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
public class UserRepositoryTest {

    @Autowired
    private UserRepository repository;

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

    private User createUser(String suffix) {
        return User.builder()
                .username("user-" + suffix)
                .email(suffix + "@example.com")
                .passwordHash("hashed-password")
                .enabled(true)
                .build();
    }
}
