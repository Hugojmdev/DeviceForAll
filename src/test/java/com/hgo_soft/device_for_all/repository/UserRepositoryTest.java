package com.hgo_soft.device_for_all.repository;

import com.hgo_soft.device_for_all.entity.User;
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
        User user = User.builder().email("test@example.com").build();
        User saved = repository.save(user);
        assertNotNull(saved.getId());
    }

    @Test
    void testFindById() {
        User user = User.builder().email("test@example.com").build();
        User saved = repository.save(user);
        Optional<User> result = repository.findById(saved.getId());
        assertTrue(result.isPresent());
    }

    @Test
    void testFindAll() {
        repository.save(User.builder().email("one@example.com").build());
        repository.save(User.builder().email("two@example.com").build());
        List<User> list = repository.findAll();
        assertTrue(list.size() >= 2);
    }

    @Test
    void testDeleteById() {
        User user = User.builder().email("delete@example.com").build();
        User saved = repository.save(user);
        repository.deleteById(saved.getId());
        assertFalse(repository.findById(saved.getId()).isPresent());
    }
}