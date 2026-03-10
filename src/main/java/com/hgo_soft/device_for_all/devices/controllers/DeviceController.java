package com.hgo_soft.device_for_all.devices.controllers;

import com.hgo_soft.device_for_all.common.api.AbstractRestController;
import com.hgo_soft.device_for_all.devices.dtos.DeviceDto;
import com.hgo_soft.device_for_all.devices.mappers.DeviceMapper;
import com.hgo_soft.device_for_all.devices.services.DeviceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/devices")
public class DeviceController extends AbstractRestController {

    private final DeviceService service;
    private final DeviceMapper mapper;

    public DeviceController(DeviceService service, DeviceMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<DeviceDto>> getAll() {
        return okOrEmpty(mapper.toDtoList(service.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeviceDto> getById(@PathVariable Long id) {
        return okOrNotFound(
                service.findById(id).map(mapper::toDto)
        );
    }

    @PostMapping
    public ResponseEntity<DeviceDto> create(@RequestBody DeviceDto deviceDto) {
        DeviceDto saved = mapper.toDto(mapper.toEntity(deviceDto));
        return created("/api/devices/" + saved.getId(), saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DeviceDto> update(@PathVariable Long id, @RequestBody DeviceDto deviceDto) {
        deviceDto.setId(id);
        DeviceDto updated = mapper.toDto(mapper.toEntity(deviceDto));
        return okOrNotFound(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteById(id);
        return deletedSuccessfully();
    }
}
