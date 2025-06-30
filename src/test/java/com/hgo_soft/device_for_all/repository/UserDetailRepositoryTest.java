package com.hgo_soft.device_for_all.repository;

import com.hgo_soft.device_for_all.entity.UserDetail;
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
public class UserDetailRepositoryTest extends RepositoryTestSetup{

    @Autowired
    private UserDetailRepository repository;

    @Test
    void testSave() {
        UserDetail userDetail = UserDetail.builder()
                .email("test@email.com")
                .firstName("Fred")
                .lastName("Sanfield")
                .build();
        UserDetail saved = repository.save(userDetail);
        assertNotNull(saved.getId());
    }

    @Test
    void testFindById() {
        Optional<UserDetail> result = repository.findById(3L);
        assertTrue(result.isPresent());
    }

    @Test
    void testFindAll() {
        List<UserDetail> list = repository.findAll();
        assertFalse(list.isEmpty());
    }

    @Test
    void testDeleteById() {
        repository.deleteById(2L);
        assertFalse(repository.findById(2L).isPresent());
    }
}
