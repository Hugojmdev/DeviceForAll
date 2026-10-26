package com.hgo_soft.device_for_all.users.services;

import com.hgo_soft.device_for_all.users.dtos.CreateUserRequest;
import com.hgo_soft.device_for_all.users.dtos.UpdateUserRequest;
import com.hgo_soft.device_for_all.users.entities.User;
import com.hgo_soft.device_for_all.users.entities.UserProfile;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findAll();
    Optional<User> findById(Long id);
    Optional<User> findByUsername(String username);
    void deleteById(Long id);

    List<UserProfile>  findAllUserDetails();
    Optional<UserProfile> findUserDetailById(Long id);
    UserProfile saveUserDetail(UserProfile userProfile);
    void deleteUserDetail(UserProfile userProfile);

    User create(CreateUserRequest createUserRequest);
    User update(Long id, UpdateUserRequest updateUserRequest);
}
