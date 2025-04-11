package com.hgo_soft.device_for_all.service;

import com.hgo_soft.device_for_all.entity.Employee;
import java.util.List;

public interface EmployeeService {
    List<Employee> findAll();
    Employee findById(Long id);
    Employee save(Employee entity);
    void deleteById(Long id);
}
