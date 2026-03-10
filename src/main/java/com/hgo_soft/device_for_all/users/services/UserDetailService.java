package com.hgo_soft.device_for_all.users.services;

import com.hgo_soft.device_for_all.users.entities.UserDetail;

import java.util.List;
import java.util.Optional;

public interface UserDetailService {
    List<UserDetail> findAll();
    Optional<UserDetail> findById(Long id);
    UserDetail save(UserDetail entity);
    void deleteById(Long id);
}
