package com.hgo_soft.device_for_all.loans.repositories;

import com.hgo_soft.device_for_all.loans.entities.LoanComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanCommentRepository extends JpaRepository<LoanComment, Long> {
}
