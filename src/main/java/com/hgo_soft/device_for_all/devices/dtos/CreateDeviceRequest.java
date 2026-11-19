package com.hgo_soft.device_for_all.devices.dtos;

import com.hgo_soft.device_for_all.devices.enums.DeviceCondition;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record CreateDeviceRequest(
        @NotNull
        @Valid
        DeviceTypeRequest type,
        @NotBlank
        @Size(max = 100)
        String name,
        @NotBlank
        @Size(max = 100)
        String serialNumber,
        @NotBlank
        @Size(max = 100)
        String model,
        @NotBlank
        @Size(max = 100)
        String make,
        @Size(max = 250)
        String description,
        @NotNull
        DeviceCondition physicalCondition,
        LocalDate acquiredOn,
        LocalDate warrantyExpiresOn
) {
}
