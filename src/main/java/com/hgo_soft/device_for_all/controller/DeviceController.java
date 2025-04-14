package com.hgo_soft.device_for_all.controller;

import com.hgo_soft.device_for_all.dto.DeviceDto;
import com.hgo_soft.device_for_all.entity.Device;
import com.hgo_soft.device_for_all.mapper.DeviceMapper;
import com.hgo_soft.device_for_all.service.DeviceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/devices")
public class DeviceController {

    private final DeviceService service;

    public DeviceController(DeviceService service) {
        this.service = service;
    }

    @GetMapping
    public List<DeviceDto> getAll() {
        return DeviceMapper.toDtoList(service.findAll());
    }

    @GetMapping("/{id}")
    public DeviceDto getById(@PathVariable Long id) {
        return DeviceMapper.toDto(service.findById(id));
    }

    @PostMapping
    public DeviceDto create(@RequestBody Device device) {
        return DeviceMapper.toDto(service.save(device));
    }

    @PutMapping("/{id}")
    public DeviceDto update(@PathVariable Long id, @RequestBody Device device) {
        device.setId(id);
        return DeviceMapper.toDto(service.save(device));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteById(id);
    }
}
