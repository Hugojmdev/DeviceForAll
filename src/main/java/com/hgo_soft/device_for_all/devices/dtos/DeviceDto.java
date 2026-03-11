package com.hgo_soft.device_for_all.devices.dtos;

import com.hgo_soft.device_for_all.devices.entities.DeviceType;
import com.hgo_soft.device_for_all.devices.enums.DeviceStatus;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeviceDto {
    private Long id;
    private DeviceType type;
    private DeviceStatus status;
}
