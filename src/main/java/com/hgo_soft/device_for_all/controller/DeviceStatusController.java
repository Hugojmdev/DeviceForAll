package com.hgo_soft.device_for_all.controller;

import com.hgo_soft.device_for_all.dto.DeviceStatusDto;
import com.hgo_soft.device_for_all.entity.DeviceStatus;
import com.hgo_soft.device_for_all.mapper.DeviceStatusMapper;
import com.hgo_soft.device_for_all.service.DeviceStatusService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/deviceStatus")
public class DeviceStatusController {

    private final DeviceStatusService service;

    public DeviceStatusController(DeviceStatusService service) {
        this.service = service;
    }

    @GetMapping
    public List<DeviceStatusDto> getAll() {
        return DeviceStatusMapper.toDtoList(service.findAll());
    }

    @GetMapping("/{id}")
    public DeviceStatusDto getById(@PathVariable Long id) {
        return DeviceStatusMapper.toDto(service.findById(id));
    }

    @PostMapping
    public DeviceStatusDto create(@RequestBody DeviceStatus entity) {
        return DeviceStatusMapper.toDto(service.save(entity));
    }

    @PutMapping("/{id}")
    public DeviceStatusDto update(@PathVariable Long id, @RequestBody DeviceStatus entity) {
        entity.setId(id);
        return DeviceStatusMapper.toDto(service.save(entity));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteById(id);
    }
}
