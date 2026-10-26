package com.hgo_soft.device_for_all.users.services.impl;

import com.hgo_soft.device_for_all.common.exceptions.ResourceNotFoundException;
import com.hgo_soft.device_for_all.users.dtos.CreateUserRequest;
import com.hgo_soft.device_for_all.users.dtos.UpdateUserRequest;
import com.hgo_soft.device_for_all.users.entities.User;
import com.hgo_soft.device_for_all.users.entities.UserProfile;
import com.hgo_soft.device_for_all.users.mappers.UserMapper;
import com.hgo_soft.device_for_all.users.repositories.UserProfileRepository;
import com.hgo_soft.device_for_all.users.repositories.UserRepository;
import com.hgo_soft.device_for_all.users.services.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final UserProfileRepository userProfileRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository repository,
                           UserProfileRepository userProfileRepository,
                           PasswordEncoder passwordEncoder,
                           UserMapper userMapper) {
        this.repository = repository;
        this.userProfileRepository = userProfileRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<User> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return repository.findByUsername(username);
    }

    private User save(User user) {
        return repository.save(user);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<UserProfile> findAllUserDetails() {
        return List.of();
    }

    @Override
    public Optional<UserProfile> findUserDetailById(Long id) {
        return Optional.empty();
    }

    @Override
    public UserProfile saveUserDetail(UserProfile userProfile) {
        return null;
    }

    @Override
    public void deleteUserDetail(UserProfile userProfile) {
    }

    @Override
    @Transactional
    public User create(CreateUserRequest createUserRequest) {
        User user = userMapper.toEntity(createUserRequest);
        //TODO add email verification
        user.setPasswordHash(passwordEncoder.encode(createUserRequest.password()));
        user.setEnabled(true);
        return save(user);

    }

    @Override
    @Transactional
    public User update(Long id, UpdateUserRequest updateUserRequest) {
        User user = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        userMapper.updateEntity(updateUserRequest, user);
        return save(user);
    }
}
