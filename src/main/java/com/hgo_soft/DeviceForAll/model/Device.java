package com.hgo_soft.DeviceForAll.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String type;
    private boolean isAvailable;
}