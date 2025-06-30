package com.hgo_soft.device_for_all.repository;

import com.hgo_soft.device_for_all.entity.Loan;
import com.hgo_soft.device_for_all.entity.LoanComment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

@DataJpaTest
@ActiveProfiles("test")
public class LoanCommentRepositoryTest extends RepositoryTestSetup{

    @Autowired
    private LoanCommentRepository repository;

    @Test
    void testSave() {
        LoanComment loanComment = LoanComment.builder()
                .comment("Test comment")
                .loan(Loan.builder().id(1L).build())
                .creationDate(LocalDate.now())
                .build();
        LoanComment saved = repository.save(loanComment);
        assertNotNull(saved.getId());
    }

    @Test
    void testFindById() {
        Optional<LoanComment> result = repository.findById(1L);
        assertTrue(result.isPresent());
    }

    @Test
    void testFindAll() {
        List<LoanComment> list = repository.findAll();
        assertFalse(list.isEmpty());
    }

    @Test
    void testDeleteById() {
        repository.deleteById(2L);
        assertFalse(repository.findById(2L).isPresent());
    }
}
