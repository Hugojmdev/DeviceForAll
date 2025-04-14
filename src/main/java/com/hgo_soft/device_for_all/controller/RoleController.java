package com.hgo_soft.device_for_all.controller;

import com.hgo_soft.device_for_all.dto.RoleDto;
import com.hgo_soft.device_for_all.entity.Role;
import com.hgo_soft.device_for_all.mapper.RoleMapper;
import com.hgo_soft.device_for_all.service.RoleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    private final RoleService service;

    public RoleController(RoleService service) {
        this.service = service;
    }

    @GetMapping
    public List<RoleDto> getAll() {
        return RoleMapper.toDtoList(service.findAll());
    }

    @GetMapping("/{id}")
    public RoleDto getById(@PathVariable Long id) {
        return RoleMapper.toDto(service.findById(id));
    }

    @PostMapping
    public RoleDto create(@RequestBody Role entity) {
        return RoleMapper.toDto(service.save(entity));
    }

    @PutMapping("/{id}")
    public RoleDto update(@PathVariable Long id, @RequestBody Role entity) {
        entity.setId(id);
        return RoleMapper.toDto(service.save(entity));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteById(id);
    }
}
