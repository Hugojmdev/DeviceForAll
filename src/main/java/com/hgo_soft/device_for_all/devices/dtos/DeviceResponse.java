package com.hgo_soft.device_for_all.devices.dtos;


import com.hgo_soft.device_for_all.devices.enums.DeviceCondition;
import com.hgo_soft.device_for_all.devices.enums.DeviceStatus;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record DeviceResponse(
        Long id,
        DeviceTypeResponse type,
        String name,
        String serialNumber,
        String model,
        String make,
        String description,
        DeviceStatus status,
        DeviceCondition physicalCondition,
        LocalDate acquiredOn,
        LocalDate warrantyExpiresOn
) {
}
