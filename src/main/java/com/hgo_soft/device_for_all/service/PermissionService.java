package com.hgo_soft.device_for_all.service;

import com.hgo_soft.device_for_all.entity.Permission;

import java.util.List;
import java.util.Optional;

public interface PermissionService {
    List<Permission> findAll();
    Optional<Permission> findById(Long id);
    Permission save(Permission entity);
    void deleteById(Long id);
}
