package com.hgo_soft.device_for_all.loans.entities;

import com.hgo_soft.device_for_all.common.audit.AuditableEntity;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "loan_comments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoanComment extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "loan_id", nullable = false)
    private Loan loan;

    @Column(length = 250)
    private String comment;
}
