package com.hgo_soft.device_for_all.devices.controllers;

import com.hgo_soft.device_for_all.common.api.AbstractRestController;
import com.hgo_soft.device_for_all.common.security.SecuredRestController;
import com.hgo_soft.device_for_all.devices.dtos.*;
import com.hgo_soft.device_for_all.devices.mappers.DeviceMapper;
import com.hgo_soft.device_for_all.devices.services.DeviceService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SecuredRestController
@RequestMapping("/api/devices")
public class DeviceController extends AbstractRestController {

    private final DeviceService service;
    private final DeviceMapper mapper;

    public DeviceController(DeviceService service, DeviceMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PreAuthorize("hasAuthority('DEVICE_READ')")
    @GetMapping
    public ResponseEntity<List<DeviceResponse>> getAll() {
        return okOrEmpty(mapper.toResponseList(service.findAll()));
    }

    @PreAuthorize("hasAuthority('DEVICE_READ')")
    @GetMapping("/{id}")
    public ResponseEntity<DeviceResponse> getById(@PathVariable Long id) {
        DeviceResponse response = mapper.toResponse(service.findById(id));
        return okOrNotFound(response);
    }

    @PreAuthorize("hasAuthority('DEVICE_READ')")
    @GetMapping("/device-types")
    public ResponseEntity<List<DeviceTypeResponse>> getActiveDeviceTypes() {
        List<DeviceTypeResponse> response = mapper.toDeviceTypeResponseList(service.findAllActiveDeviceTypes());
        return okOrNotFound(response);
    }

    @PreAuthorize("hasAuthority('DEVICE_CREATE')")
    @PostMapping
    public ResponseEntity<DeviceResponse> create(@Valid @RequestBody CreateDeviceRequest request) {
        DeviceResponse response = mapper.toResponse(service.create(request));
        return created("/api/devices/" + response.id(), response);
    }

    @PreAuthorize("hasAuthority('DEVICE_UPDATE')")
    @PatchMapping("/{id}")
    public ResponseEntity<DeviceResponse> update(@PathVariable Long id, @Valid @RequestBody UpdateDeviceRequest request) {
        DeviceResponse updated = mapper.toResponse(service.update(id, request));
        return okOrNotFound(updated);
    }

    @PreAuthorize("hasAuthority('DEVICE_RETIRE')")
    @PatchMapping("/retire/{id}")
    public ResponseEntity<RetireDeviceResponse> retire(@PathVariable Long id, @Valid @RequestBody RetireDeviceRequest request) {
        RetireDeviceResponse response = mapper.toRetireResponse(service.retire(id, request));
        return okOrNotFound(response);
    }

}
