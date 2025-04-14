package com.hgo_soft.device_for_all.mapper;

import com.hgo_soft.device_for_all.dto.DeviceStatusDto;
import com.hgo_soft.device_for_all.entity.DeviceStatus;

import java.util.List;

public class DeviceStatusMapper {
    public static DeviceStatusDto toDto(DeviceStatus deviceStatus) {
        if (deviceStatus == null) return null;
        DeviceStatusDto dto = new DeviceStatusDto();
        dto.setId(deviceStatus.getId());
        return dto;
    }

    public static List<DeviceStatusDto> toDtoList(List<DeviceStatus> deviceStatuses){
        if(deviceStatuses == null) return null;
        return deviceStatuses.stream().map(DeviceStatusMapper::toDto).toList();
    }

    public static DeviceStatus toEntity(DeviceStatusDto deviceStatusDto) {
        if (deviceStatusDto == null) return null;
        DeviceStatus deviceStatus = new DeviceStatus();
        deviceStatus.setId(deviceStatusDto.getId());
        return deviceStatus;
    }

    public static List<DeviceStatus> toEntityList(List<DeviceStatusDto> deviceStatusDtos){
        if(deviceStatusDtos == null) return null;
        return deviceStatusDtos.stream().map(DeviceStatusMapper::toEntity).toList();
    }
}
