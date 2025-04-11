package com.hgo_soft.device_for_all.controller;

import com.hgo_soft.device_for_all.entity.DeviceStatus;
import com.hgo_soft.device_for_all.service.DeviceStatusService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/deviceStatuss")
public class DeviceStatusController {

    private final DeviceStatusService service;

    public DeviceStatusController(DeviceStatusService service) {
        this.service = service;
    }

    @GetMapping
    public List<DeviceStatus> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public DeviceStatus getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public DeviceStatus create(@RequestBody DeviceStatus entity) {
        return service.save(entity);
    }

    @PutMapping("/{id}")
    public DeviceStatus update(@PathVariable Long id, @RequestBody DeviceStatus entity) {
        entity.setId(id);
        return service.save(entity);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteById(id);
    }
}
