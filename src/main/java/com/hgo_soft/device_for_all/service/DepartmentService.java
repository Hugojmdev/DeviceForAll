package com.hgo_soft.device_for_all.service;

import com.hgo_soft.device_for_all.entity.Department;

import java.util.List;
import java.util.Optional;

public interface DepartmentService {
    List<Department> findAll();
    Optional<Department> findById(Long id);
    Department save(Department entity);
    void deleteById(Long id);
}
