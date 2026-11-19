package com.hgo_soft.device_for_all.devices.mappers;

import com.hgo_soft.device_for_all.devices.dtos.*;
import com.hgo_soft.device_for_all.devices.entities.Device;
import com.hgo_soft.device_for_all.common.mapping.MapperConfiguration;
import com.hgo_soft.device_for_all.devices.entities.DeviceType;
import org.mapstruct.*;

import java.util.List;

@Mapper(config = MapperConfiguration.class)
public interface DeviceMapper {
    //Device mappers
    DeviceResponse toResponse(Device device);
    RetireDeviceResponse toRetireResponse(Device device);
    List<DeviceResponse> toResponseList(List<Device> devices);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "type", ignore = true)
    @Mapping(target = "retiredAt", ignore = true)
    @Mapping(target = "retirementReason", ignore = true)
    Device toEntity(CreateDeviceRequest createDeviceRequest);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "type", ignore = true)
    void updateEntity(UpdateDeviceRequest updateDeviceRequest, @MappingTarget Device device);
    void retireDevice(RetireDeviceRequest retireDeviceRequest, @MappingTarget Device device);

    //Device types mappers
    DeviceTypeResponse toDeviceTypeResponse(DeviceType deviceType);
    List<DeviceTypeResponse> toDeviceTypeResponseList(List<DeviceType> deviceTypes);
    @Mapping(target = "id", ignore = true)
    DeviceType toDeviceTypeEntity(DeviceTypeRequest deviceTypeRequest);


}