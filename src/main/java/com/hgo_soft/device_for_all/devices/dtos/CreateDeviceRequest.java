package com.hgo_soft.device_for_all.devices.dtos;

import com.hgo_soft.device_for_all.devices.enums.DeviceCondition;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record CreateDeviceRequest(
        @NotNull
        @Valid
        DeviceTypeRequest type,
        @NotBlank
        String name,
        @NotBlank
        String serialNumber,
        @NotBlank
        String model,
        @NotBlank
        String make,
        String description,
        @NotNull
        DeviceCondition physicalCondition,
        LocalDate acquiredOn,
        LocalDate warrantyExpiresOn
) {
}
