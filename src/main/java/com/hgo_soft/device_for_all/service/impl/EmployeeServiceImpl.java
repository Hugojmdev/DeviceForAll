package com.hgo_soft.device_for_all.service.impl;

import com.hgo_soft.device_for_all.entity.Employee;
import com.hgo_soft.device_for_all.exception.ResourceNotFoundException;
import com.hgo_soft.device_for_all.repository.EmployeeRepository;
import com.hgo_soft.device_for_all.service.EmployeeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository repository;

    public EmployeeServiceImpl(EmployeeRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Employee> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Employee> findById(Long id) {
        return repository.findById(id)/*.orElse(new Employee());*//*.orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id))*/;
    }

    @Override
    public Employee save(Employee entity) {
        return repository.save(entity);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
