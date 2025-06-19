package com.hgo_soft.device_for_all.service;

import com.hgo_soft.device_for_all.entity.LoanComment;

import java.util.List;
import java.util.Optional;

public interface LoanCommentService {
    List<LoanComment> findAll();
    Optional<LoanComment> findById(Long id);
    LoanComment save(LoanComment entity);
    void deleteById(Long id);
}
