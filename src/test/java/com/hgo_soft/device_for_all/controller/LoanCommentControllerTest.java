package com.hgo_soft.device_for_all.controller;


import com.hgo_soft.device_for_all.dto.LoanCommentDto;
import com.hgo_soft.device_for_all.entity.LoanComment;
import com.hgo_soft.device_for_all.mapper.LoanCommentMapper;
import com.hgo_soft.device_for_all.service.LoanCommentService;
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

class LoanCommentControllerTest {

    private LoanCommentService service;
    private LoanCommentMapper mapper;
    private LoanCommentController controller;

    @BeforeEach
    void setUp() {
        service = mock(LoanCommentService.class);
        mapper = mock(LoanCommentMapper.class);
        controller = new LoanCommentController(service, mapper);
    }

    @Test
    void testGetAll_ShouldReturnListOfLoanComments() {
        List<LoanComment> loanComments = Arrays.asList(
                LoanComment.builder().id(1L).build(),
                LoanComment.builder().id(2L).build()
        );
        List<LoanCommentDto> loanCommentDtos = Arrays.asList(
                LoanCommentDto.builder().id(1L).build(),
                LoanCommentDto.builder().id(2L).build()
        );
        when(mapper.toEntityList(anyList())).thenReturn(loanComments);
        when(mapper.toDtoList(anyList())).thenReturn(loanCommentDtos);

        when(service.findAll()).thenReturn(loanComments);

        ResponseEntity<List<LoanCommentDto>> response = controller.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        verify(service).findAll();
    }

    @Test
    void testGetAll_ShouldReturnNoContent() {
        List<LoanCommentDto> loanCommentDtos = new ArrayList<>();
        List<LoanComment> loanComments = new ArrayList<>();
        when(mapper.toEntityList(anyList())).thenReturn(loanComments);
        when(mapper.toDtoList(anyList())).thenReturn(loanCommentDtos);
        when(service.findAll()).thenReturn(Collections.emptyList());

        ResponseEntity<List<LoanCommentDto>> response = controller.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testGetById_Found() {
        long id = 1L;
        LoanCommentDto inputDto = LoanCommentDto.builder().id(id).build();
        LoanComment entityToFound = LoanComment.builder().id(id).build();
        LoanCommentDto dtoToFound = LoanCommentDto.builder().id(id).build();
        when(mapper.toEntity(inputDto)).thenReturn(entityToFound);
        when(mapper.toDto(entityToFound)).thenReturn(dtoToFound);
        when(service.findById(any())).thenReturn(Optional.of(entityToFound));

        ResponseEntity<LoanCommentDto> response = controller.getById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(id, response.getBody().getId());
    }

    @Test
    void testGetById_NotFound() {
        when(service.findById(999L)).thenReturn(Optional.empty());

        ResponseEntity<LoanCommentDto> response = controller.getById(999L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testCreate_ShouldReturnCreatedLoanComment() {
        long id = 10L;
        LoanCommentDto inputDto = new LoanCommentDto();
        LoanComment savedEntity = LoanComment.builder().id(id).build();
        LoanCommentDto savedDto = LoanCommentDto.builder().id(id).build();
        // simulate mapper and persistence
        when(mapper.toEntity(inputDto)).thenReturn(savedEntity);
        when(mapper.toDto(savedEntity)).thenReturn(savedDto);
        when(service.save(any())).thenReturn(savedEntity);
        ResponseEntity<LoanCommentDto> response = controller.create(inputDto);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("/api/loan-comments/10", Objects.requireNonNull(response.getHeaders().getLocation()).toString());
    }

    @Test
    void testUpdate_ShouldReturnUpdatedLoanComment() {
        Long id = 5L;
        LoanCommentDto inputDto = LoanCommentDto.builder().id(id).build();
        LoanComment savedEntity = LoanComment.builder().id(id).build();
        LoanCommentDto savedDto = LoanCommentDto.builder().id(id).build();
        when(mapper.toEntity(inputDto)).thenReturn(savedEntity);
        when(mapper.toDto(savedEntity)).thenReturn(savedDto);
        when(service.save(any())).thenReturn(savedEntity);
        ResponseEntity<LoanCommentDto> response = controller.update(id, inputDto);
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