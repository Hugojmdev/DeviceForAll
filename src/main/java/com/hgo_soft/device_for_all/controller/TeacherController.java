package com.hgo_soft.device_for_all.controller;

import com.hgo_soft.device_for_all.dto.TeacherDto;
import com.hgo_soft.device_for_all.entity.Teacher;
import com.hgo_soft.device_for_all.mapper.TeacherMapper;
import com.hgo_soft.device_for_all.service.TeacherService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teachers")
public class TeacherController {

    private final TeacherService service;

    public TeacherController(TeacherService service) {
        this.service = service;
    }

    @GetMapping
    public List<TeacherDto> getAll() {
        return TeacherMapper.toDtoList(service.findAll());
    }

    @GetMapping("/{id}")
    public TeacherDto getById(@PathVariable Long id) {
        return TeacherMapper.toDto(service.findById(id));
    }

    @PostMapping
    public TeacherDto create(@RequestBody Teacher entity) {
        return TeacherMapper.toDto(service.save(entity));
    }

    @PutMapping("/{id}")
    public TeacherDto update(@PathVariable Long id, @RequestBody Teacher entity) {
        entity.setId(id);
        return TeacherMapper.toDto(service.save(entity));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteById(id);
    }
}
