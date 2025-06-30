package com.hgo_soft.device_for_all.service.impl;

import com.hgo_soft.device_for_all.entity.Loan;
import com.hgo_soft.device_for_all.repository.LoanRepository;
import com.hgo_soft.device_for_all.service.LoanService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LoanServiceImpl implements LoanService {
    private final LoanRepository repository;

    public LoanServiceImpl(LoanRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Loan> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Loan> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Loan save(Loan entity) {
        return repository.save(entity);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
