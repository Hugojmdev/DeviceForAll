package com.hgo_soft.device_for_all.devices.entities;

import com.hgo_soft.device_for_all.devices.enums.DeviceStatus;
import com.hgo_soft.device_for_all.loans.entities.Loan;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "devices")
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String serialNumber;

    private String model;

    private String description;

    /*@Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private DeviceType type;*/

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private DeviceStatus status;

    @ManyToOne
    @JoinColumn(name = "device_type_id")
    private DeviceType deviceType;

    @ManyToMany(mappedBy = "devices")
    private Set<Loan> loans = new HashSet<>();
}
