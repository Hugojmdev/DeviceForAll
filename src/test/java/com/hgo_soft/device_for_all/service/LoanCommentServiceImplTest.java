package com.hgo_soft.device_for_all.service;

import com.hgo_soft.device_for_all.entity.LoanComment;
import com.hgo_soft.device_for_all.repository.LoanCommentRepository;
import com.hgo_soft.device_for_all.service.impl.LoanCommentServiceImpl;
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

public class LoanCommentServiceImplTest {
    private LoanCommentRepository repository;
    private LoanCommentServiceImpl service;

    @BeforeEach
    void setUp() {
        repository = mock(LoanCommentRepository.class);
        service = new LoanCommentServiceImpl(repository);
    }

    @Test
    void testFindAll_ShouldReturnLoanComments() {
        List<LoanComment> loanComments = Arrays.asList(LoanComment.builder().id(1L).build(), LoanComment.builder().id(2L).build());
        when(repository.findAll()).thenReturn(loanComments);

        List<LoanComment> result = service.findAll();

        assertEquals(2, result.size());
        verify(repository).findAll();
    }

    @Test
    void testFindById_WhenFound() {
        LoanComment loanComment = LoanComment.builder().id(1L).build();
        when(repository.findById(1L)).thenReturn(Optional.of(loanComment));

        Optional<LoanComment> result = service.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
    }

    @Test
    void testFindById_WhenNotFound() {
        when(repository.findById(999L)).thenReturn(Optional.empty());

        Optional<LoanComment> result = service.findById(999L);

        assertFalse(result.isPresent());
    }

    @Test
    void testSave_ShouldReturnSavedLoanComment() {
        LoanComment toSave = new LoanComment();
        LoanComment saved = LoanComment.builder().id(10L).build();

        when(repository.save(ArgumentMatchers.any())).thenReturn(saved);

        LoanComment result = service.save(toSave);

        assertNotNull(result);
        assertEquals(10L, result.getId());
    }

    @Test
    void testUpdate_ShouldReturnUpdatedLoanComment() {
        LoanComment loanComment = LoanComment.builder().id(3L).build();
        when(repository.save(loanComment)).thenReturn(loanComment);

        LoanComment result = service.save(loanComment);

        assertEquals(3L, result.getId());
    }

    @Test
    void testDeleteById() {
        doNothing().when(repository).deleteById(5L);

        service.deleteById(5L);

        verify(repository, times(1)).deleteById(5L);
    }
}