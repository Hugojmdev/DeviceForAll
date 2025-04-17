package com.hgo_soft.device_for_all.service;

import com.hgo_soft.device_for_all.entity.User;
import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findAll();
    Optional<User> findById(Long id);
    User save(User entity);
    void deleteById(Long id);
}
