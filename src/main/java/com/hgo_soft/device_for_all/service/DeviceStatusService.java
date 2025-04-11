package com.hgo_soft.device_for_all.service;

import com.hgo_soft.device_for_all.entity.DeviceStatus;
import java.util.List;

public interface DeviceStatusService {
    List<DeviceStatus> findAll();
    DeviceStatus findById(Long id);
    DeviceStatus save(DeviceStatus entity);
    void deleteById(Long id);
}
