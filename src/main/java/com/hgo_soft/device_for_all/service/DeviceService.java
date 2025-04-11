package com.hgo_soft.device_for_all.service;

import com.hgo_soft.device_for_all.entity.Device;
import java.util.List;

public interface DeviceService {
    List<Device> findAll();
    Device findById(Long id);
    Device save(Device entity);
    void deleteById(Long id);
}
