package com.hgo_soft.DeviceForAll.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Device device;

    @ManyToOne
    private Student student;

    @ManyToOne
    private Teacher teacher;

    private LocalDate loanDate;
    private LocalDate returnDate;
}