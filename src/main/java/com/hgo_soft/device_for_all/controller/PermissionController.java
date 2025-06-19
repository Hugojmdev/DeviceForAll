package com.hgo_soft.device_for_all.controller;


import com.hgo_soft.device_for_all.dto.PermissionDto;
import com.hgo_soft.device_for_all.mapper.PermissionMapper;
import com.hgo_soft.device_for_all.service.PermissionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/permissions")
public class PermissionController extends AbstractRestController{
    private final PermissionService service;
    private final PermissionMapper mapper;

    public PermissionController(PermissionService service, PermissionMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<PermissionDto>> getAll() {
        return okOrEmpty(mapper.toDtoList(service.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PermissionDto> getById(@PathVariable Long id) {
        return okOrNotFound(
                service.findById(id).map(mapper::toDto)
        );
    }

    @PostMapping
    public ResponseEntity<PermissionDto> create(@RequestBody PermissionDto permissionDto) {
        PermissionDto saved = mapper.toDto(service.save(mapper.toEntity(permissionDto)));
        return created("/api/permissions/" + saved.getId(), saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PermissionDto> update(@PathVariable Long id, @RequestBody PermissionDto permissionDto) {
        permissionDto.setId(id);
        PermissionDto updated = mapper.toDto(service.save(mapper.toEntity(permissionDto)));
        return okOrNotFound(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteById(id);
        return deletedSuccessfully();
    }
}
