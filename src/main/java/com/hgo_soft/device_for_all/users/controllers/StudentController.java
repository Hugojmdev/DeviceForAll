package com.hgo_soft.device_for_all.users.controllers;

import com.hgo_soft.device_for_all.common.api.AbstractRestController;
import com.hgo_soft.device_for_all.users.dtos.StudentDto;
import com.hgo_soft.device_for_all.users.mappers.StudentMapper;
import com.hgo_soft.device_for_all.users.services.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController extends AbstractRestController {

    private final StudentService service;
    private final StudentMapper mapper;

    public StudentController(StudentService service, StudentMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<StudentDto>> getAll() {
        return okOrEmpty(mapper.toDtoList(service.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDto> getById(@PathVariable Long id) {
        return okOrNotFound(
                service.findById(id).map(mapper::toDto)
        );
    }

    @PostMapping
    public ResponseEntity<StudentDto> create(@RequestBody StudentDto studentDto) {
        StudentDto saved = mapper.toDto(service.save(mapper.toEntity(studentDto)));
        return created("/api/students/" + saved.getId(), saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentDto> update(@PathVariable Long id, @RequestBody StudentDto studentDto) {
        studentDto.setId(id);
        StudentDto updated = mapper.toDto(service.save(mapper.toEntity(studentDto)));
        return okOrNotFound(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteById(id);
        return deletedSuccessfully();
    }
}
