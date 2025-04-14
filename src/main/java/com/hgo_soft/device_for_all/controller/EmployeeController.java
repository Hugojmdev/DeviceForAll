package com.hgo_soft.device_for_all.controller;

import com.hgo_soft.device_for_all.dto.EmployeeDto;
import com.hgo_soft.device_for_all.entity.Employee;
import com.hgo_soft.device_for_all.mapper.EmployeeMapper;
import com.hgo_soft.device_for_all.service.EmployeeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService service;

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    @GetMapping
    public List<EmployeeDto> getAll() {
        return EmployeeMapper.toDtoList(service.findAll());
    }

    @GetMapping("/{id}")
    public EmployeeDto getById(@PathVariable Long id) {
        return EmployeeMapper.toDto(service.findById(id));
    }

    @PostMapping
    public EmployeeDto create(@RequestBody Employee entity) {
        return EmployeeMapper.toDto(service.save(entity));
    }

    @PutMapping("/{id}")
    public EmployeeDto update(@PathVariable Long id, @RequestBody Employee entity) {
        entity.setId(id);
        return EmployeeMapper.toDto(service.save(entity));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteById(id);
    }
}
