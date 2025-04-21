package com.hgo_soft.device_for_all.controller;

import com.hgo_soft.device_for_all.dto.EmployeeDto;
import com.hgo_soft.device_for_all.entity.Employee;
import com.hgo_soft.device_for_all.mapper.EmployeeMapper;
import com.hgo_soft.device_for_all.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController extends AbstractRestController{

    private final EmployeeService service;

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDto>> getAll() {
        return okOrEmpty(EmployeeMapper.toDtoList(service.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getById(@PathVariable Long id) {
        return okOrNotFound(
                service.findById(id).map(EmployeeMapper::toDto)
        );
    }

    @PostMapping
    public ResponseEntity<EmployeeDto> create(@RequestBody EmployeeDto employeeDto) {
        EmployeeDto saved = EmployeeMapper.toDto(service.save(EmployeeMapper.toEntity(employeeDto)));
        return created("/api/employees/" + saved.getId(), saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDto> update(@PathVariable Long id, @RequestBody EmployeeDto employeeDto) {
        employeeDto.setId(id);
        EmployeeDto updated = EmployeeMapper.toDto(service.save(EmployeeMapper.toEntity(employeeDto)));
        return okOrNotFound(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteById(id);
        return deletedSuccessfully();
    }
}
