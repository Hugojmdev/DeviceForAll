package com.hgo_soft.device_for_all.service;

import com.hgo_soft.device_for_all.entity.Loan;

import java.util.List;
import java.util.Optional;

public interface LoanService {
    List<Loan> findAll();
    Optional<Loan> findById(Long id);
    Loan save(Loan entity);
    void deleteById(Long id);
}
