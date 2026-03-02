package com.hgo_soft.device_for_all.dto;

import com.hgo_soft.device_for_all.enums.DeviceStatus;
import com.hgo_soft.device_for_all.enums.DeviceType;
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
