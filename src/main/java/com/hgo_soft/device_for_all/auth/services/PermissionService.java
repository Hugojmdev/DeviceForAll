package com.hgo_soft.device_for_all.auth.services;

import com.hgo_soft.device_for_all.auth.entities.Permission;

import java.util.List;
import java.util.Optional;

public interface PermissionService {
    List<Permission> findAll();
    Optional<Permission> findById(Long id);
    Permission save(Permission entity);
    void deleteById(Long id);
}
