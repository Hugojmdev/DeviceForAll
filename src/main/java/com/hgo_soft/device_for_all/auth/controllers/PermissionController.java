package com.hgo_soft.device_for_all.auth.controllers;


import com.hgo_soft.device_for_all.auth.dtos.PermissionResponse;
import com.hgo_soft.device_for_all.common.api.AbstractRestController;
import com.hgo_soft.device_for_all.auth.mappers.PermissionMapper;
import com.hgo_soft.device_for_all.auth.services.PermissionService;
import com.hgo_soft.device_for_all.common.security.SecuredRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SecuredRestController
@RequestMapping("/api/permissions")
public class PermissionController extends AbstractRestController {
    private final PermissionService service;
    private final PermissionMapper mapper;

    public PermissionController(PermissionService service, PermissionMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PreAuthorize("hasAuthority('ACCESS_MANAGE')")
    @GetMapping
    public ResponseEntity<List<PermissionResponse>> getAll() {
        return okOrEmpty(mapper.toResponseList(service.findAll()));
    }

    @PreAuthorize("hasAuthority('ACCESS_MANAGE')")
    @GetMapping("/{id}")
    public ResponseEntity<PermissionResponse> getById(@PathVariable Long id) {
        return okOrNotFound(
                service.findById(id).map(mapper::toResponse)
        );
    }
}
