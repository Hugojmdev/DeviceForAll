package com.hgo_soft.device_for_all.auth.services;

import com.hgo_soft.device_for_all.auth.entities.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    List<Role> findAll();
    Optional<Role> findById(Long id);
    Role save(Role entity);
    void deleteById(Long id);
}
