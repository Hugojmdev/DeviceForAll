package com.hgo_soft.device_for_all.service.impl;

import com.hgo_soft.device_for_all.entity.Department;
import com.hgo_soft.device_for_all.repository.DepartmentRepository;
import com.hgo_soft.device_for_all.service.DepartmentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository repository;

    public DepartmentServiceImpl(DepartmentRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Department> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Department> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Department save(Department entity) {
        return repository.save(entity);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
