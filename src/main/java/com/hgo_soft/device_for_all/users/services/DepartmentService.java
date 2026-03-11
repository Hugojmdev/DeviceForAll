package com.hgo_soft.device_for_all.users.services;

import com.hgo_soft.device_for_all.users.entities.Department;

import java.util.List;
import java.util.Optional;

public interface DepartmentService {
    List<Department> findAll();
    Optional<Department> findById(Long id);
    Department save(Department entity);
    void deleteById(Long id);
}
