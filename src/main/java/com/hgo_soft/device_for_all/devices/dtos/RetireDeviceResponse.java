package com.hgo_soft.device_for_all.devices.dtos;

import com.hgo_soft.device_for_all.devices.enums.DeviceStatus;
import lombok.Builder;

import java.time.Instant;

@Builder
public record RetireDeviceResponse (
        Long id,
        Instant retiredAt,
        String retirementReason,
        DeviceStatus status
) {
}
