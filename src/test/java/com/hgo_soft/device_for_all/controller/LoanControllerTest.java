package com.hgo_soft.device_for_all.controller;


import com.hgo_soft.device_for_all.dto.LoanDto;
import com.hgo_soft.device_for_all.entity.Loan;
import com.hgo_soft.device_for_all.mapper.LoanMapper;
import com.hgo_soft.device_for_all.service.LoanService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

class LoanControllerTest {

    private LoanService service;
    private LoanMapper mapper;
    private LoanController controller;

    @BeforeEach
    void setUp() {
        service = mock(LoanService.class);
        mapper = mock(LoanMapper.class);
        controller = new LoanController(service, mapper);
    }

    @Test
    void testGetAll_ShouldReturnListOfLoans() {
        List<Loan> loans = Arrays.asList(
                Loan.builder().id(1L).build(),
                Loan.builder().id(2L).build()
        );
        List<LoanDto> loanDtos = Arrays.asList(
                LoanDto.builder().id(1L).build(),
                LoanDto.builder().id(2L).build()
        );
        when(mapper.toEntityList(anyList())).thenReturn(loans);
        when(mapper.toDtoList(anyList())).thenReturn(loanDtos);

        when(service.findAll()).thenReturn(loans);

        ResponseEntity<List<LoanDto>> response = controller.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        verify(service).findAll();
    }

    @Test
    void testGetAll_ShouldReturnNoContent() {
        List<LoanDto> loanDtos = new ArrayList<>();
        List<Loan> loans = new ArrayList<>();
        when(mapper.toEntityList(anyList())).thenReturn(loans);
        when(mapper.toDtoList(anyList())).thenReturn(loanDtos);
        when(service.findAll()).thenReturn(Collections.emptyList());

        ResponseEntity<List<LoanDto>> response = controller.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testGetById_Found() {
        long id = 1L;
        LoanDto inputDto = LoanDto.builder().id(id).build();
        Loan entityToFound = Loan.builder().id(id).build();
        LoanDto dtoToFound = LoanDto.builder().id(id).build();
        when(mapper.toEntity(inputDto)).thenReturn(entityToFound);
        when(mapper.toDto(entityToFound)).thenReturn(dtoToFound);
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
        LoanDto savedDto = LoanDto.builder().id(id).build();
        // simulate mapper and persistence
        when(mapper.toEntity(inputDto)).thenReturn(savedEntity);
        when(mapper.toDto(savedEntity)).thenReturn(savedDto);
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
        LoanDto savedDto = LoanDto.builder().id(id).build();
        when(mapper.toEntity(inputDto)).thenReturn(savedEntity);
        when(mapper.toDto(savedEntity)).thenReturn(savedDto);
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