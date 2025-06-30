package com.hgo_soft.device_for_all.repository;

import com.hgo_soft.device_for_all.entity.Loan;
import com.hgo_soft.device_for_all.entity.UserDetail;
import com.hgo_soft.device_for_all.enums.LoanStatus;
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
public class LoanRepositoryTest extends RepositoryTestSetup {

    @Autowired
    private LoanRepository repository;

    @Test
    void testSave() {
        Loan loan = Loan.builder()
                .userDetail(UserDetail.builder().id(1L).build())
                .status(LoanStatus.REQUESTED)
                .startDate(LocalDate.now())
                .dueDate(LocalDate.now())
                .build();
        Loan saved = repository.save(loan);
        assertNotNull(saved.getId());
    }

    @Test
    void testFindById() {
        Optional<Loan> result = repository.findById(1L);
        assertTrue(result.isPresent());
    }

    @Test
    void testFindAll() {
        List<Loan> list = repository.findAll();
        assertFalse(list.isEmpty());
    }

    @Test
        void testDeleteById() {
        repository.deleteById(2L);
        assertFalse(repository.findById(2L).isPresent());
    }
}