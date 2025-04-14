package com.hgo_soft.device_for_all.controller;

import com.hgo_soft.device_for_all.dto.DeviceDto;
import com.hgo_soft.device_for_all.mapper.DeviceMapper;
import com.hgo_soft.device_for_all.service.DeviceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/devices")
public class DeviceController extends AbstractRestController {

    private final DeviceService service;

    public DeviceController(DeviceService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<DeviceDto>> getAll() {
        return okOrEmpty(DeviceMapper.toDtoList(service.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeviceDto> getById(@PathVariable Long id) {
        return okOrNotFound(DeviceMapper.toDto(service.findById(id)));
    }

    @PostMapping
    public ResponseEntity<DeviceDto> create(@RequestBody DeviceDto deviceDto) {
        DeviceDto saved = DeviceMapper.toDto(DeviceMapper.toEntity(deviceDto));
        return created("/api/devices/" + saved.getId(), saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DeviceDto> update(@PathVariable Long id, @RequestBody DeviceDto deviceDto) {
        deviceDto.setId(id);
        DeviceDto updated = DeviceMapper.toDto(DeviceMapper.toEntity(deviceDto));
        return okOrNotFound(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteById(id);
        return deletedSuccessfully();
    }
}
