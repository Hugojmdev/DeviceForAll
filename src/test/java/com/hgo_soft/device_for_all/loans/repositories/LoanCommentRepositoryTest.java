package com.hgo_soft.device_for_all.loans.repositories;

import com.hgo_soft.device_for_all.loans.entities.Loan;
import com.hgo_soft.device_for_all.loans.entities.LoanComment;
import com.hgo_soft.device_for_all.loans.enums.LoanStatus;
import com.hgo_soft.device_for_all.users.entities.User;
import com.hgo_soft.device_for_all.users.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

@DataJpaTest
@ActiveProfiles("test")
public class LoanCommentRepositoryTest {

    @Autowired
    private LoanCommentRepository repository;
    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    void testSave() {
        LoanComment loanComment = buildLoanComment("test-loan-comment");
        repository.saveAndFlush(loanComment);
        assertNotNull(loanComment.getId());
    }

    @Test
    void testFindById() {
        LoanComment loanComment = buildLoanComment("test-loan-comment");
        repository.saveAndFlush(loanComment);
        Long loanCommentId = loanComment.getId();
        Optional<LoanComment> result = repository.findById(loanCommentId);
        assertTrue(result.isPresent());
        assertNotNull(result.get().getId());
        assertEquals(loanCommentId, result.get().getId());
    }

    @Test
    void testFindAll() {
        LoanComment loanComment = buildLoanComment("test-loan-comment");
        repository.saveAndFlush(loanComment);
        Loan loan = loanComment.getLoan();
        LoanComment loanComment2 = LoanComment.builder()
                .loan(loan)
                .comment("second-test-comment")
                .createdAt(LocalDateTime.now())
                .build();
        repository.saveAndFlush(loanComment2);
        List<LoanComment> loanComments = repository.findAll();
        assertFalse(loanComments.isEmpty());
        assertEquals(2, loanComments.size());
    }

    @Test
    void testDeleteById() {
        repository.deleteById(2L);
        assertFalse(repository.findById(2L).isPresent());
    }

    private LoanComment buildLoanComment(String comment) {
        User user = User.builder()
                .username("test-user")
                .email("test" + "@example.com")
                .passwordHash("hashed-password")
                .enabled(true)
                .build();
        userRepository.saveAndFlush(user);

        Loan loan = Loan.builder()
                .user(user)
                .status(LoanStatus.REQUESTED)
                .startDate(LocalDate.now())
                .dueDate(LocalDate.now())
                .build();
        loanRepository.saveAndFlush(loan);

        return LoanComment.builder()
                .loan(loan)
                .createdAt(LocalDateTime.now())
                .comment(comment)
                .build();
    }
}
