package com.hgo_soft.device_for_all.auth.services.impl;

import com.hgo_soft.device_for_all.auth.entities.Role;
import com.hgo_soft.device_for_all.auth.repositories.RoleRepository;
import com.hgo_soft.device_for_all.auth.services.RoleService;
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
        return repository.findById(Math.toIntExact(id));
    }

    @Override
    public Role save(Role entity) {
        return repository.save(entity);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(Math.toIntExact(id));
    }
}
