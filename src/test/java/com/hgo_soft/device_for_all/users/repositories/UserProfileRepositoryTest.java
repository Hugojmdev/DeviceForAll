package com.hgo_soft.device_for_all.users.repositories;

import com.hgo_soft.device_for_all.users.entities.Department;
import com.hgo_soft.device_for_all.users.entities.User;
import com.hgo_soft.device_for_all.users.entities.UserProfile;
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
public class UserProfileRepositoryTest {

    @Autowired
    private UserProfileRepository repository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DepartmentRepository departmentRepository;

    @Test
    void testSave() {
        UserProfile userProfile = buildUserProfile();
        repository.saveAndFlush(userProfile);
        assertNotNull(userProfile.getUserId());
    }

    @Test
    void testFindById() {
        UserProfile userProfile = buildUserProfile();
        repository.saveAndFlush(userProfile);
        Long userProfileId = userProfile.getUserId();
        Optional<UserProfile> result = repository.findById(userProfileId);
        assertTrue(result.isPresent());
    }

    @Test
    void testFindAll() {
        UserProfile userProfile = buildUserProfile();
        repository.saveAndFlush(userProfile);
        List<UserProfile> list = repository.findAll();
        assertFalse(list.isEmpty());
    }

    @Test
    void testDeleteById() {
        repository.deleteById(2L);
        assertFalse(repository.findById(2L).isPresent());
    }

    private UserProfile buildUserProfile() {
        User user = User.builder()
                .username("user-test")
                .email("test" + "@example.com")
                .passwordHash("hashed-password")
                .enabled(true)
                .build();
        userRepository.saveAndFlush(user);

        Department department = Department.builder()
                .name("test-department")
                .build();
        departmentRepository.saveAndFlush(department);

        return UserProfile.builder()
                .department(department)
                .user(user)
                .firstName("test-firstName")
                .lastName("test-lastName")
                .address("test-address")
                .phoneNumber("test-phoneNumber")
                .build();
    }
}
