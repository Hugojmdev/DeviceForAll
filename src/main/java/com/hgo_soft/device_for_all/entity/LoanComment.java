package com.hgo_soft.device_for_all.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "loan_comments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoanComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "loan_id", nullable = false)
    private Loan loan;

    @Column(length = 250)
    private String comment;

    @Column(name = "creation_date", nullable = false)
    private LocalDate creationDate;
}
