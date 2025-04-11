package com.hgo_soft.device_for_all.service;

import com.hgo_soft.device_for_all.entity.User;
import java.util.List;

public interface UserService {
    List<User> findAll();
    User findById(Long id);
    User save(User entity);
    void deleteById(Long id);
}
