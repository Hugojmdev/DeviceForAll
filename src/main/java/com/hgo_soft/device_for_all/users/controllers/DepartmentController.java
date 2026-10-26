package com.hgo_soft.device_for_all.users.controllers;

import com.hgo_soft.device_for_all.common.api.AbstractRestController;
import com.hgo_soft.device_for_all.common.security.SecuredRestController;
import com.hgo_soft.device_for_all.users.dtos.DepartmentDto;
import com.hgo_soft.device_for_all.users.mappers.DepartmentMapper;
import com.hgo_soft.device_for_all.users.services.DepartmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SecuredRestController
@RequestMapping("/api/departments")
public class DepartmentController extends AbstractRestController {

    private final DepartmentService service;
    private final DepartmentMapper mapper;

    public DepartmentController(DepartmentService service, DepartmentMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }


    @PreAuthorize("hasAuthority('USER_READ')")
    @GetMapping
    public ResponseEntity<List<DepartmentDto>> getAll() {
        return okOrEmpty(mapper.toDtoList(service.findAll()));
    }

    @PreAuthorize("hasAuthority('USER_READ')")
    @GetMapping("/{id}")
    public ResponseEntity<DepartmentDto> getById(@PathVariable Long id) {
        return okOrNotFound(
                service.findById(id).map(mapper::toDto)
        );
    }

    @PreAuthorize("hasAuthority('USER_READ')")
    @PostMapping
    public ResponseEntity<DepartmentDto> create(@RequestBody DepartmentDto departmentDto) {
        DepartmentDto saved = mapper.toDto(service.save(mapper.toEntity(departmentDto)));
        return created("/api/departments/" + saved.getId(), saved);
    }

    @PreAuthorize("hasAuthority('USER_READ')")
    @PutMapping("/{id}")
    public ResponseEntity<DepartmentDto> update(@PathVariable Long id, @RequestBody DepartmentDto departmentDto) {
        departmentDto.setId(id);
        DepartmentDto updated = mapper.toDto(service.save(mapper.toEntity(departmentDto)));
        return okOrNotFound(updated);
    }

    @PreAuthorize("hasAuthority('USER_READ')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteById(id);
        return deletedSuccessfully();
    }
}
