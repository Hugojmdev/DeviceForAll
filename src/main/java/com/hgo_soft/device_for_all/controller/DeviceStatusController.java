package com.hgo_soft.device_for_all.controller;

import com.hgo_soft.device_for_all.dto.DeviceStatusDto;
import com.hgo_soft.device_for_all.mapper.DeviceStatusMapper;
import com.hgo_soft.device_for_all.service.DeviceStatusService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/deviceStatus")
public class DeviceStatusController extends AbstractRestController{

    private final DeviceStatusService service;

    public DeviceStatusController(DeviceStatusService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<DeviceStatusDto>> getAll() {
        return okOrEmpty(DeviceStatusMapper.toDtoList(service.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeviceStatusDto> getById(@PathVariable Long id) {
        return okOrNotFound(DeviceStatusMapper.toDto(service.findById(id)));
    }

    @PostMapping
    public ResponseEntity<DeviceStatusDto> create(@RequestBody DeviceStatusDto deviceStatusDto) {
        DeviceStatusDto saved = DeviceStatusMapper.toDto(service.save(DeviceStatusMapper.toEntity(deviceStatusDto)));
        return created("/api/deviceStatus/" + saved.getId(), saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DeviceStatusDto> update(@PathVariable Long id, @RequestBody DeviceStatusDto deviceStatusDto) {
        deviceStatusDto.setId(id);
        DeviceStatusDto updated = DeviceStatusMapper.toDto(service.save(DeviceStatusMapper.toEntity(deviceStatusDto)));
        return okOrNotFound(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteById(id);
        return deletedSuccessfully();
    }
}
