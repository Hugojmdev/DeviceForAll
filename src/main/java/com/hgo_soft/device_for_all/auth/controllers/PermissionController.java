package com.hgo_soft.device_for_all.auth.controllers;


import com.hgo_soft.device_for_all.auth.dtos.PermissionResponse;
import com.hgo_soft.device_for_all.common.api.AbstractRestController;
import com.hgo_soft.device_for_all.auth.mappers.PermissionMapper;
import com.hgo_soft.device_for_all.auth.services.PermissionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/permissions")
public class PermissionController extends AbstractRestController {
    private final PermissionService service;
    private final PermissionMapper mapper;

    public PermissionController(PermissionService service, PermissionMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<PermissionResponse>> getAll() {
        return okOrEmpty(mapper.toResponseList(service.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PermissionResponse> getById(@PathVariable Long id) {
        return okOrNotFound(
                service.findById(id).map(mapper::toResponse)
        );
    }
}
