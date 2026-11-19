package com.hgo_soft.device_for_all.devices.dtos;

import com.hgo_soft.device_for_all.common.annotations.NotBlankIfPresent;
import com.hgo_soft.device_for_all.devices.enums.DeviceCondition;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record UpdateDeviceRequest(
        DeviceTypeRequest type,
        @NotBlankIfPresent
        @Size(max = 100)
        String name,
        @NotBlankIfPresent
        @Size(max = 100)
        String serialNumber,
        @NotBlankIfPresent
        @Size(max = 100)
        String model,
        @NotBlankIfPresent
        @Size(max = 100)
        String make,
        @Size(max = 250)
        String description,
        DeviceCondition physicalCondition,
        LocalDate acquiredOn,
        LocalDate warrantyExpiresOn
) {
}
