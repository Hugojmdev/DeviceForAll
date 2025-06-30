package com.hgo_soft.device_for_all.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.hgo_soft.device_for_all.enums.EmployeeType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JsonBackReference
    @JoinColumn(name = "user_detail_id", nullable = false)
    private UserDetail user;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @Enumerated(EnumType.STRING)
    @Column(name = "employee_type", nullable = false)
    private EmployeeType type;

}
