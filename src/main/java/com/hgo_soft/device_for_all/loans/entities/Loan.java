package com.hgo_soft.device_for_all.loans.entities;

import com.hgo_soft.device_for_all.devices.entities.Device;
import com.hgo_soft.device_for_all.loans.enums.LoanStatus;
import com.hgo_soft.device_for_all.users.entities.UserDetail;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "loans")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_detail_id", nullable = false)
    private UserDetail userDetail; // can be student or teacher

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "due_date", nullable = false)
    private LocalDate dueDate;

    private LocalDate requestDate;

    private LocalDate extendedDueDate;

    private LocalDate returnDate;

    @Enumerated(EnumType.STRING)
    private LoanStatus status;

    @ManyToMany
    @JoinTable(
            name = "loan_devices",
            joinColumns = @JoinColumn(name = "loan_id"),
            inverseJoinColumns = @JoinColumn(name = "device_id")
    )
    private Set<Device> devices;

    @OneToMany(mappedBy = "loan", cascade = CascadeType.ALL)
    private Set<LoanComment> comments;
}
