package com.hgo_soft.device_for_all.controller;

import com.hgo_soft.device_for_all.dto.TeacherDto;
import com.hgo_soft.device_for_all.entity.Teacher;
import com.hgo_soft.device_for_all.mapper.TeacherMapper;
import com.hgo_soft.device_for_all.service.TeacherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teachers")
public class TeacherController extends AbstractRestController{

    private final TeacherService service;

    public TeacherController(TeacherService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<TeacherDto>> getAll() {
        return okOrEmpty(TeacherMapper.toDtoList(service.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeacherDto> getById(@PathVariable Long id) {
        /*Teacher teacher = service.findById(id);
        return okOrNotFound(TeacherMapper.toDto(teacher));*/
        return okOrNotFound(
                service.findById(id).map(TeacherMapper::toDto)
        );
    }

    @PostMapping
    public ResponseEntity<TeacherDto> create(@RequestBody TeacherDto teacherDto) {
        TeacherDto saved = TeacherMapper.toDto(service.save(TeacherMapper.toEntity(teacherDto)));
        return created("/api/teachers/" + saved.getId(), saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TeacherDto> update(@PathVariable Long id, @RequestBody TeacherDto teacherDto) {
        teacherDto.setId(id);
        TeacherDto updated = TeacherMapper.toDto(service.save(TeacherMapper.toEntity(teacherDto)));
        return okOrNotFound(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteById(id);
        return deletedSuccessfully();
    }
}
