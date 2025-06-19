package com.hgo_soft.device_for_all.service;

import com.hgo_soft.device_for_all.entity.Loan;
import com.hgo_soft.device_for_all.repository.LoanRepository;
import com.hgo_soft.device_for_all.service.impl.LoanServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class LoanServiceImplTest {
    private LoanRepository repository;
    private LoanServiceImpl service;

    @BeforeEach
    void setUp() {
        repository = mock(LoanRepository.class);
        service = new LoanServiceImpl(repository);
    }

    @Test
    void testFindAll_ShouldReturnLoans() {
        List<Loan> loans = Arrays.asList(Loan.builder().id(1L).build(), Loan.builder().id(2L).build());
        when(repository.findAll()).thenReturn(loans);

        List<Loan> result = service.findAll();

        assertEquals(2, result.size());
        verify(repository).findAll();
    }

    @Test
    void testFindById_WhenFound() {
        Loan loan = Loan.builder().id(1L).build();
        when(repository.findById(1L)).thenReturn(Optional.of(loan));

        Optional<Loan> result = service.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
    }

    @Test
    void testFindById_WhenNotFound() {
        when(repository.findById(999L)).thenReturn(Optional.empty());

        Optional<Loan> result = service.findById(999L);

        assertFalse(result.isPresent());
    }

    @Test
    void testSave_ShouldReturnSavedLoan() {
        Loan toSave = new Loan();
        Loan saved = Loan.builder().id(10L).build();

        when(repository.save(ArgumentMatchers.any())).thenReturn(saved);

        Loan result = service.save(toSave);

        assertNotNull(result);
        assertEquals(10L, result.getId());
    }

    @Test
    void testUpdate_ShouldReturnUpdatedLoan() {
        Loan loan = Loan.builder().id(3L).build();
        when(repository.save(loan)).thenReturn(loan);

        Loan result = service.save(loan);

        assertEquals(3L, result.getId());
    }

    @Test
    void testDeleteById() {
        doNothing().when(repository).deleteById(5L);

        service.deleteById(5L);

        verify(repository, times(1)).deleteById(5L);
    }
}