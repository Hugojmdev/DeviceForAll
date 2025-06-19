package com.hgo_soft.device_for_all.controller;

import com.hgo_soft.device_for_all.dto.DepartmentDto;
import com.hgo_soft.device_for_all.mapper.DepartmentMapper;
import com.hgo_soft.device_for_all.service.DepartmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController extends AbstractRestController{

    private final DepartmentService service;
    private final DepartmentMapper mapper;

    public DepartmentController(DepartmentService service, DepartmentMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<DepartmentDto>> getAll() {
        return okOrEmpty(mapper.toDtoList(service.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartmentDto> getById(@PathVariable Long id) {
        return okOrNotFound(
                service.findById(id).map(mapper::toDto)
        );
    }

    @PostMapping
    public ResponseEntity<DepartmentDto> create(@RequestBody DepartmentDto departmentDto) {
        DepartmentDto saved = mapper.toDto(service.save(mapper.toEntity(departmentDto)));
        return created("/api/departments/" + saved.getId(), saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DepartmentDto> update(@PathVariable Long id, @RequestBody DepartmentDto departmentDto) {
        departmentDto.setId(id);
        DepartmentDto updated = mapper.toDto(service.save(mapper.toEntity(departmentDto)));
        return okOrNotFound(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteById(id);
        return deletedSuccessfully();
    }
}
