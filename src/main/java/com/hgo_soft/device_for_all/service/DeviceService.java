package com.hgo_soft.device_for_all.service;

import com.hgo_soft.device_for_all.entity.Device;
import java.util.List;
import java.util.Optional;

public interface DeviceService {
    List<Device> findAll();
    Optional<Device> findById(Long id);
    Device save(Device entity);
    void deleteById(Long id);
}
