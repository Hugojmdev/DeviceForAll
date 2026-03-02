package com.hgo_soft.device_for_all.mapper;

import com.hgo_soft.device_for_all.dto.DeviceDto;
import com.hgo_soft.device_for_all.entity.Device;
import com.hgo_soft.device_for_all.mapper.config.MapperConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = MapperConfiguration.class)
public interface DeviceMapper {
    DeviceDto toDto(Device device);
    Device toEntity(DeviceDto deviceDto);
    List<DeviceDto> toDtoList(List<Device> devices);
    List<Device> toEntityList(List<DeviceDto> deviceDtos);
}