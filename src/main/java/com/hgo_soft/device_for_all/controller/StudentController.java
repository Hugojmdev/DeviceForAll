package com.hgo_soft.device_for_all.controller;

import com.hgo_soft.device_for_all.dto.StudentDto;
import com.hgo_soft.device_for_all.entity.Student;
import com.hgo_soft.device_for_all.mapper.StudentMapper;
import com.hgo_soft.device_for_all.service.StudentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    @GetMapping
    public List<StudentDto> getAll() {
        return StudentMapper.toDtoList(service.findAll());
    }

    @GetMapping("/{id}")
    public StudentDto getById(@PathVariable Long id) {
        return StudentMapper.toDto(service.findById(id));
    }

    @PostMapping
    public StudentDto create(@RequestBody Student entity) {
        return StudentMapper.toDto(service.save(entity));
    }

    @PutMapping("/{id}")
    public StudentDto update(@PathVariable Long id, @RequestBody Student entity) {
        entity.setId(id);
        return StudentMapper.toDto(service.save(entity));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteById(id);
    }
}
