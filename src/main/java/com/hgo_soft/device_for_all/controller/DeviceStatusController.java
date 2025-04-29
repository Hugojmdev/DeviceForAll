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
    private final DeviceStatusMapper mapper;

    public DeviceStatusController(DeviceStatusService service, DeviceStatusMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<DeviceStatusDto>> getAll() {
        return okOrEmpty(mapper.toDtoList(service.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeviceStatusDto> getById(@PathVariable Long id) {
        return okOrNotFound(
                service.findById(id).map(mapper::toDto)
        );
    }

    @PostMapping
    public ResponseEntity<DeviceStatusDto> create(@RequestBody DeviceStatusDto deviceStatusDto) {
        DeviceStatusDto saved = mapper.toDto(service.save(mapper.toEntity(deviceStatusDto)));
        return created("/api/deviceStatus/" + saved.getId(), saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DeviceStatusDto> update(@PathVariable Long id, @RequestBody DeviceStatusDto deviceStatusDto) {
        deviceStatusDto.setId(id);
        DeviceStatusDto updated = mapper.toDto(service.save(mapper.toEntity(deviceStatusDto)));
        return okOrNotFound(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteById(id);
        return deletedSuccessfully();
    }
}
