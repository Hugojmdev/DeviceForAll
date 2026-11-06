package com.hgo_soft.device_for_all.devices.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record RetireDeviceRequest(
        @NotBlank
        String retirementReason
) {
}
