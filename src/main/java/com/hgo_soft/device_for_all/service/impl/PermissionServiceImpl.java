package com.hgo_soft.device_for_all.service.impl;

import com.hgo_soft.device_for_all.entity.Permission;
import com.hgo_soft.device_for_all.repository.PermissionRepository;
import com.hgo_soft.device_for_all.service.PermissionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PermissionServiceImpl implements PermissionService {
    private final PermissionRepository repository;

    public PermissionServiceImpl(PermissionRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Permission> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Permission> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Permission save(Permission entity) {
        return repository.save(entity);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
