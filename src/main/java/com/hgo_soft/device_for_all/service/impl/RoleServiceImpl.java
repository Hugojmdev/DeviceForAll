package com.hgo_soft.device_for_all.service.impl;

import com.hgo_soft.device_for_all.entity.Role;
import com.hgo_soft.device_for_all.exception.ResourceNotFoundException;
import com.hgo_soft.device_for_all.repository.RoleRepository;
import com.hgo_soft.device_for_all.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository repository;

    public RoleServiceImpl(RoleRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Role> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Role> findById(Long id) {
        return repository.findById(id)/*.orElse(new Role());*//*.orElseThrow(() -> new ResourceNotFoundException("Role not found with id: " + id))*/;
    }

    @Override
    public Role save(Role entity) {
        return repository.save(entity);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
