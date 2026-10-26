package com.hgo_soft.device_for_all.loans.repositories;

import com.hgo_soft.device_for_all.loans.entities.Loan;
import com.hgo_soft.device_for_all.users.entities.User;
import com.hgo_soft.device_for_all.loans.enums.LoanStatus;
import com.hgo_soft.device_for_all.users.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
public class LoanRepositoryTest {

    @Autowired
    private LoanRepository repository;
    @Autowired
    private UserRepository userRepository;

    @Test
    void testSave() {
        Loan loan = buildLoan();
        Loan saved = repository.save(loan);
        assertNotNull(saved.getId());
    }

    @Test
    void testFindById() {
        Loan loan = buildLoan();
        repository.saveAndFlush(loan);
        Long loanId = loan.getId();
        Optional<Loan> result = repository.findById(loanId);
        assertTrue(result.isPresent());
    }

    @Test
    void testFindAll() {
        Loan loan  = buildLoan();
        repository.saveAndFlush(loan);

        User user = loan.getUser();
        Loan loan2 = Loan.builder()
                .user(user)
                .status(LoanStatus.REQUESTED)
                .startDate(LocalDate.now())
                .dueDate(LocalDate.now())
                .build();
        repository.saveAndFlush(loan2);

        List<Loan> loanList = repository.findAll();
        assertFalse(loanList.isEmpty());
        assertEquals(2, loanList.size());

    }

    @Test
        void testDeleteById() {
        repository.deleteById(2L);
        assertFalse(repository.findById(2L).isPresent());
    }

    private Loan buildLoan() {
        User user = User.builder()
                .username("user-test")
                .email("test" + "@example.com")
                .passwordHash("hashed-password")
                .enabled(true)
                .build();
        userRepository.saveAndFlush(user);
        return Loan.builder()
                .user(user)
                .status(LoanStatus.REQUESTED)
                .startDate(LocalDate.now())
                .dueDate(LocalDate.now())
                .build();
    }
}