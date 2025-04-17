package com.hgo_soft.device_for_all.mapper;

import com.hgo_soft.device_for_all.dto.DeviceDto;
import com.hgo_soft.device_for_all.entity.Device;

import java.util.ArrayList;
import java.util.List;

public class DeviceMapper {
    public static DeviceDto toDto(Device device) {
        if (device == null) {
            throw new IllegalStateException("Null value cannot be mapped");
        }
        DeviceDto dto = new DeviceDto();
        dto.setId(device.getId());
        return dto;
    }

    public static List<DeviceDto> toDtoList(List<Device> devices){
        if(devices == null) {
            throw new IllegalStateException("Null value cannot be mapped");
        }
        return devices.stream().map(DeviceMapper::toDto).toList();
    }

    public static Device toEntity(DeviceDto deviceDto) {
        if (deviceDto == null) {
            throw new IllegalStateException("Null value cannot be mapped");
        }
        Device device = new Device();
        device.setId(deviceDto.getId());
        return device;
    }

    public static List<Device> toEntityList(List<DeviceDto> deviceDtos){
        if(deviceDtos == null) {
            throw new IllegalStateException("Null value cannot be mapped");
        }
        return deviceDtos.stream().map(DeviceMapper::toEntity).toList();
    }
}