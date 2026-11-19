package com.hgo_soft.device_for_all.devices.dtos;

import lombok.Builder;

@Builder
public record DeviceTypeRequest(
        Long id,
        String name,
        String description
) {
}
