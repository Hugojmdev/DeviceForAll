package com.hgo_soft.device_for_all.devices.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record RetireDeviceRequest(
        @NotBlank
        @Size(max = 255)
        String retirementReason
) {
}
