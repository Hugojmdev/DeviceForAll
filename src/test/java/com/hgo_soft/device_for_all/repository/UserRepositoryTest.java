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
public class UserRepositoryTest extends RepositoryTestSetup {

    @Autowired
    private UserRepository repository;

    @Test
    void testSave() {
        User user = User.builder().id(1L).build();
        User saved = repository.save(user);
        assertNotNull(saved.getId());
    }

    @Test
    void testFindById() {
        Optional<User> result = repository.findById(1L);
        assertTrue(result.isPresent());
    }

    @Test
    void testFindAll() {
        List<User> list = repository.findAll();
        assertTrue(list.size() >= 2);
    }

    @Test
    void testDeleteById() {
        repository.deleteById(1L);
        assertFalse(repository.findById(1L).isPresent());
    }
}