package com.hgo_soft.device_for_all.devices.dtos;

import com.hgo_soft.device_for_all.devices.enums.DeviceStatus;
import com.hgo_soft.device_for_all.devices.enums.DeviceType;
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
