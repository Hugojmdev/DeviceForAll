package com.hgo_soft.device_for_all.mapper;

import com.hgo_soft.device_for_all.dto.DeviceDto;
import com.hgo_soft.device_for_all.entity.Device;

import java.util.List;

public class DeviceMapper {
    public static DeviceDto toDto(Device device) {
        if (device == null) return null;
        DeviceDto dto = new DeviceDto();
        dto.setId(device.getId());
        return dto;
    }

    public static List<DeviceDto> toDtoList(List<Device> devices){
        if(devices == null) return null;
        return devices.stream().map(DeviceMapper::toDto).toList();
    }

    public static Device toEntity(DeviceDto deviceDto) {
        if (deviceDto == null) return null;
        Device device = new Device();
        device.setId(deviceDto.getId());
        return device;
    }

    public static List<Device> toEntityList(List<DeviceDto> deviceDtos){
        if(deviceDtos == null) return null;
        return deviceDtos.stream().map(DeviceMapper::toEntity).toList();
    }
}