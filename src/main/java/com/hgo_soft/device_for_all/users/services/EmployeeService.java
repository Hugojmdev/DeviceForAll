package com.hgo_soft.device_for_all.users.services;

import com.hgo_soft.device_for_all.users.entities.Employee;
import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    List<Employee> findAll();
    Optional<Employee> findById(Long id);
    Employee save(Employee entity);
    void deleteById(Long id);
}
