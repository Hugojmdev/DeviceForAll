package com.hgo_soft.device_for_all.controller;

import com.hgo_soft.device_for_all.dto.StudentDto;
import com.hgo_soft.device_for_all.entity.Student;
import com.hgo_soft.device_for_all.mapper.StudentMapper;
import com.hgo_soft.device_for_all.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController extends AbstractRestController{

    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<StudentDto>> getAll() {
        return okOrEmpty(StudentMapper.toDtoList(service.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDto> getById(@PathVariable Long id) {
        /*Student student = service.findById(id);
        return okOrNotFound(StudentMapper.toDto(student));*/
        return okOrNotFound(
                service.findById(id).map(StudentMapper::toDto)
        );
    }

    @PostMapping
    public ResponseEntity<StudentDto> create(@RequestBody StudentDto studentDto) {
        StudentDto saved = StudentMapper.toDto(service.save(StudentMapper.toEntity(studentDto)));
        return created("/api/students/" + saved.getId(), saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentDto> update(@PathVariable Long id, @RequestBody StudentDto studentDto) {
        studentDto.setId(id);
        StudentDto updated = StudentMapper.toDto(service.save(StudentMapper.toEntity(studentDto)));
        return okOrNotFound(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteById(id);
        return deletedSuccessfully();
    }
}
