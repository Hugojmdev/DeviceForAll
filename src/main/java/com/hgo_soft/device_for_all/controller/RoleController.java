package com.hgo_soft.device_for_all.controller;

import com.hgo_soft.device_for_all.dto.RoleDto;
import com.hgo_soft.device_for_all.entity.Role;
import com.hgo_soft.device_for_all.mapper.RoleMapper;
import com.hgo_soft.device_for_all.service.RoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RoleController extends AbstractRestController{

    private final RoleService service;

    public RoleController(RoleService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<RoleDto>> getAll() {
        return okOrEmpty(RoleMapper.toDtoList(service.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleDto> getById(@PathVariable Long id) {
        return okOrNotFound(
                service.findById(id).map(RoleMapper::toDto)
        );
    }

    @PostMapping
    public ResponseEntity<RoleDto> create(@RequestBody RoleDto roleDto) {
        RoleDto saved = RoleMapper.toDto(service.save(RoleMapper.toEntity(roleDto)));
        return created("/api/roles/" + saved.getId(), saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoleDto> update(@PathVariable Long id, @RequestBody RoleDto roleDto) {
        roleDto.setId(id);
        RoleDto updated = RoleMapper.toDto(service.save(RoleMapper.toEntity(roleDto)));
        return okOrNotFound(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteById(id);
        return deletedSuccessfully();
    }
}
