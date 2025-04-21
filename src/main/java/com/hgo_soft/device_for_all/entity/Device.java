package com.hgo_soft.device_for_all.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "devices")
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String serialNumber;

    private String type;

    private String model;

    private String libraryDeviceUniqueId;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "status_id")
    private DeviceStatus status;

}
