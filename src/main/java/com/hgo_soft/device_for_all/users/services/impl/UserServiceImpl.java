package com.hgo_soft.device_for_all.users.services.impl;

import com.hgo_soft.device_for_all.users.entities.User;
import com.hgo_soft.device_for_all.users.entities.UserProfile;
import com.hgo_soft.device_for_all.users.repositories.UserProfileRepository;
import com.hgo_soft.device_for_all.users.repositories.UserRepository;
import com.hgo_soft.device_for_all.users.services.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final UserProfileRepository userProfileRepository;
    private final PasswordEncoder encoder;

    public UserServiceImpl(UserRepository repository,
                           UserProfileRepository userProfileRepository,
                           PasswordEncoder encoder) {
        this.repository = repository;
        this.userProfileRepository = userProfileRepository;
        this.encoder = encoder;
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

    @Override
    public User save(User user) {
        //TODO add email verification
        user.setPasswordHash(encoder.encode(user.getPasswordHash()));
        //user.setPasswordHash(user.getPasswordHash());
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
}
