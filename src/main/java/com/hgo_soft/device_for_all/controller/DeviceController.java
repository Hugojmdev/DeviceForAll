package com.hgo_soft.device_for_all.controller;

import com.hgo_soft.device_for_all.entity.Device;
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
    public List<Device> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Device getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public Device create(@RequestBody Device entity) {
        return service.save(entity);
    }

    @PutMapping("/{id}")
    public Device update(@PathVariable Long id, @RequestBody Device entity) {
        entity.setId(id);
        return service.save(entity);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteById(id);
    }
}
