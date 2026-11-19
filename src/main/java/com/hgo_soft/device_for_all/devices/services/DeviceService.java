package com.hgo_soft.device_for_all.devices.services;

import com.hgo_soft.device_for_all.devices.dtos.CreateDeviceRequest;
import com.hgo_soft.device_for_all.devices.dtos.RetireDeviceRequest;
import com.hgo_soft.device_for_all.devices.dtos.UpdateDeviceRequest;
import com.hgo_soft.device_for_all.devices.entities.Device;
import com.hgo_soft.device_for_all.devices.entities.DeviceType;

import java.util.List;

public interface DeviceService {
    List<Device> findAll();
    Device findById(Long id);
    Device create(CreateDeviceRequest request);
    Device update(Long id, UpdateDeviceRequest request);
    Device retire(Long id, RetireDeviceRequest request);

    List<DeviceType> findAllActiveDeviceTypes();
}
