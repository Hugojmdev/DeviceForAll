package com.hgo_soft.device_for_all.devices.services;

import com.hgo_soft.device_for_all.devices.entities.Device;
import java.util.List;
import java.util.Optional;

public interface DeviceService {
    List<Device> findAll();
    Optional<Device> findById(Long id);
    Device save(Device entity);
    void deleteById(Long id);
}
