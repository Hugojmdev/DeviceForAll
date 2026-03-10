package com.hgo_soft.device_for_all.users.services.impl;

import com.hgo_soft.device_for_all.users.entities.UserDetail;
import com.hgo_soft.device_for_all.users.repositories.UserDetailRepository;
import com.hgo_soft.device_for_all.users.services.UserDetailService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserDetailServiceImpl implements UserDetailService {
    private final UserDetailRepository repository;

    public UserDetailServiceImpl(UserDetailRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<UserDetail> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<UserDetail> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public UserDetail save(UserDetail entity) {
        return repository.save(entity);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
