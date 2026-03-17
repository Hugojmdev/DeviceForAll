package com.hgo_soft.device_for_all.users.repositories;

import com.hgo_soft.device_for_all.users.entities.UserProfile;
import com.hgo_soft.device_for_all.common.repositories.RepositoryTestSetup;
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
public class UserProfileRepositoryTest extends RepositoryTestSetup {

    @Autowired
    private UserProfileRepository repository;

    @Test
    void testSave() {
        UserProfile userProfile = UserProfile.builder()
                .firstName("Fred")
                .lastName("Sanfield")
                .build();
        UserProfile saved = repository.save(userProfile);
        assertNotNull(saved.getUserId());
    }

    @Test
    void testFindById() {
        Optional<UserProfile> result = repository.findById(3L);
        assertTrue(result.isPresent());
    }

    @Test
    void testFindAll() {
        List<UserProfile> list = repository.findAll();
        assertFalse(list.isEmpty());
    }

    @Test
    void testDeleteById() {
        repository.deleteById(2L);
        assertFalse(repository.findById(2L).isPresent());
    }
}
