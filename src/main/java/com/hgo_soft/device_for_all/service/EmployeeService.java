package com.hgo_soft.device_for_all.service;

import com.hgo_soft.device_for_all.entity.Employee;
import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    List<Employee> findAll();
    Optional<Employee> findById(Long id);
    Employee save(Employee entity);
    void deleteById(Long id);
}
