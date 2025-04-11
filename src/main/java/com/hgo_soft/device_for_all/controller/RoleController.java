package com.hgo_soft.device_for_all.controller;

import com.hgo_soft.device_for_all.entity.Role;
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
    public List<Role> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Role getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public Role create(@RequestBody Role entity) {
        return service.save(entity);
    }

    @PutMapping("/{id}")
    public Role update(@PathVariable Long id, @RequestBody Role entity) {
        entity.setId(id);
        return service.save(entity);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteById(id);
    }
}
