package com.hgo_soft.device_for_all.devices.entities;

import com.hgo_soft.device_for_all.common.audit.AuditableEntity;
import com.hgo_soft.device_for_all.devices.enums.DeviceCondition;
import com.hgo_soft.device_for_all.devices.enums.DeviceStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "devices")
public class Device extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "device_type_id", nullable = false)
    private DeviceType type;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String serialNumber;

    private String model;

    private String make;

    private String description;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(name = "status", nullable = false, length = 50)
    private DeviceStatus status;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(name = "physical_condition", nullable = false, length = 50)
    private DeviceCondition physicalCondition;

    private LocalDate acquiredOn;

    private LocalDate warrantyExpiresOn;

    private Instant retiredAt;

    private String retirementReason;

}
