package com.hgo_soft.device_for_all.service.impl;

import com.hgo_soft.device_for_all.entity.LoanComment;
import com.hgo_soft.device_for_all.repository.LoanCommentRepository;
import com.hgo_soft.device_for_all.service.LoanCommentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LoanCommentServiceImpl implements LoanCommentService{
    private final LoanCommentRepository repository;

    public LoanCommentServiceImpl(LoanCommentRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<LoanComment> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<LoanComment> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public LoanComment save(LoanComment entity) {
        return repository.save(entity);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
