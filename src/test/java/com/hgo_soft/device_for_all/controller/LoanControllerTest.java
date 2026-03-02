package com.hgo_soft.device_for_all.controller;


import com.hgo_soft.device_for_all.dto.LoanDto;
import com.hgo_soft.device_for_all.entity.Loan;
import com.hgo_soft.device_for_all.mapper.LoanMapper;
import com.hgo_soft.device_for_all.mapper.LoanMapperImpl;
import com.hgo_soft.device_for_all.service.LoanService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

public class LoanControllerTest {

    private LoanService service;
    private LoanMapper mapper;
    private LoanController controller;

    @BeforeEach
    void setUp() {
        service = mock(LoanService.class);
        mapper = new LoanMapperImpl();
        controller = new LoanController(service, mapper);
    }

    @Test
    void testGetAll_ShouldReturnListOfLoans() {
        List<Loan> loans = Arrays.asList(
                Loan.builder().id(1L).build(),
                Loan.builder().id(2L).build()
        );
        when(service.findAll()).thenReturn(loans);

        ResponseEntity<List<LoanDto>> response = controller.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        verify(service).findAll();
    }

    @Test
    void testGetAll_ShouldReturnNoContent() {
        when(service.findAll()).thenReturn(Collections.emptyList());
        ResponseEntity<List<LoanDto>> response = controller.getAll();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isEmpty());
    }

    @Test
    void testGetById_Found() {
        long id = 1L;
        Loan entityToFound = Loan.builder().id(id).build();
        when(service.findById(any())).thenReturn(Optional.of(entityToFound));

        ResponseEntity<LoanDto> response = controller.getById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(id, response.getBody().getId());
    }

    @Test
    void testGetById_NotFound() {
        when(service.findById(999L)).thenReturn(Optional.empty());

        ResponseEntity<LoanDto> response = controller.getById(999L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testCreate_ShouldReturnCreatedLoan() {
        long id = 10L;
        LoanDto inputDto = new LoanDto();
        Loan savedEntity = Loan.builder().id(id).build();
        when(service.save(any())).thenReturn(savedEntity);
        ResponseEntity<LoanDto> response = controller.create(inputDto);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("/api/loans/10", Objects.requireNonNull(response.getHeaders().getLocation()).toString());
    }

    @Test
    void testUpdate_ShouldReturnUpdatedLoan() {
        Long id = 5L;
        LoanDto inputDto = LoanDto.builder().id(id).build();
        Loan savedEntity = Loan.builder().id(id).build();
        when(service.save(any())).thenReturn(savedEntity);
        ResponseEntity<LoanDto> response = controller.update(id, inputDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(id, response.getBody().getId());
    }

    @Test
    void testDelete_ShouldReturnOk() {
        doNothing().when(service).deleteById(1L);

        ResponseEntity<Void> response = controller.delete(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(service).deleteById(1L);
    }
}