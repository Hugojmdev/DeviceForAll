package com.hgo_soft.device_for_all.devices.mappers;

import com.hgo_soft.device_for_all.devices.dtos.DeviceDto;
import com.hgo_soft.device_for_all.devices.entities.Device;
import com.hgo_soft.device_for_all.common.mapping.MapperConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = MapperConfiguration.class)
public interface DeviceMapper {
    DeviceDto toDto(Device device);
    Device toEntity(DeviceDto deviceDto);
    List<DeviceDto> toDtoList(List<Device> devices);
    List<Device> toEntityList(List<DeviceDto> deviceDtos);
}