package com.hgo_soft.device_for_all.auth.controllers;

import com.hgo_soft.device_for_all.auth.dtos.RoleResponse;
import com.hgo_soft.device_for_all.auth.mappers.RoleMapper;
import com.hgo_soft.device_for_all.auth.services.RoleService;
import com.hgo_soft.device_for_all.common.api.AbstractRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RoleController extends AbstractRestController {
    private final RoleService service;
    private final RoleMapper mapper;

    public RoleController(RoleService service, RoleMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<RoleResponse>> getAll() {
        return okOrEmpty(mapper.toResponseList(service.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleResponse> getById(@PathVariable Long id) {
        return okOrNotFound(
                service.findById(id).map(mapper::toResponse)
        );
    }
}
