package com.hgo_soft.device_for_all.devices.controllers;

import com.hgo_soft.device_for_all.common.api.AbstractRestController;
import com.hgo_soft.device_for_all.common.security.SecuredRestController;
import com.hgo_soft.device_for_all.devices.dtos.DeviceDto;
import com.hgo_soft.device_for_all.devices.mappers.DeviceMapper;
import com.hgo_soft.device_for_all.devices.services.DeviceService;
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
    public ResponseEntity<List<DeviceDto>> getAll() {
        return okOrEmpty(mapper.toDtoList(service.findAll()));
    }

    @PreAuthorize("hasAuthority('DEVICE_READ')")
    @GetMapping("/{id}")
    public ResponseEntity<DeviceDto> getById(@PathVariable Long id) {
        return okOrNotFound(
                service.findById(id).map(mapper::toDto)
        );
    }

    @PreAuthorize("hasAuthority('DEVICE_CREATE')")
    @PostMapping
    public ResponseEntity<DeviceDto> create(@RequestBody DeviceDto deviceDto) {
        DeviceDto saved = mapper.toDto(mapper.toEntity(deviceDto));
        return created("/api/devices/" + saved.getId(), saved);
    }

    @PreAuthorize("hasAuthority('DEVICE_UPDATE')")
    @PutMapping("/{id}")
    public ResponseEntity<DeviceDto> update(@PathVariable Long id, @RequestBody DeviceDto deviceDto) {
        deviceDto.setId(id);
        DeviceDto updated = mapper.toDto(mapper.toEntity(deviceDto));
        return okOrNotFound(updated);
    }

    @PreAuthorize("hasAuthority('DEVICE_RETIRE')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteById(id);
        return deletedSuccessfully();
    }
}
