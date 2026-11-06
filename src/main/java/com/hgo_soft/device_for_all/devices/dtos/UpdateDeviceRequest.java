package com.hgo_soft.device_for_all.devices.dtos;

import com.hgo_soft.device_for_all.devices.enums.DeviceCondition;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record UpdateDeviceRequest(
        DeviceTypeRequest type,
        String name,
        String serialNumber,
        String model,
        String make,
        String description,
        DeviceCondition physicalCondition,
        LocalDate acquiredOn,
        LocalDate warrantyExpiresOn
) {
}
