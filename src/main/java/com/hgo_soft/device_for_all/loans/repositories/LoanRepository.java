package com.hgo_soft.device_for_all.loans.repositories;

import com.hgo_soft.device_for_all.loans.entities.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
}
