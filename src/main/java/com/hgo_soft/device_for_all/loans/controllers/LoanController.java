package com.hgo_soft.device_for_all.loans.controllers;

import com.hgo_soft.device_for_all.common.api.AbstractRestController;
import com.hgo_soft.device_for_all.common.security.SecuredRestController;
import com.hgo_soft.device_for_all.loans.dtos.LoanDto;
import com.hgo_soft.device_for_all.loans.mappers.LoanMapper;
import com.hgo_soft.device_for_all.loans.services.LoanService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SecuredRestController
@RequestMapping("/api/loans")
public class LoanController extends AbstractRestController {

    private final LoanService service;
    private final LoanMapper mapper;

    public LoanController(LoanService service, LoanMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PreAuthorize("hasAuthority('LOAN_READ_ALL')")
    @GetMapping
    public ResponseEntity<List<LoanDto>> getAll() {
        return okOrEmpty(mapper.toDtoList(service.findAll()));
    }

    @PreAuthorize("hasAuthority('LOAN_READ_OWN')")
    @GetMapping("/{id}")
    public ResponseEntity<LoanDto> getById(@PathVariable Long id) {
        return okOrNotFound(
                service.findById(id).map(mapper::toDto)
        );
    }

    @PreAuthorize("hasAuthority('LOAN_CREATE_OWN')")
    @PostMapping
    public ResponseEntity<LoanDto> create(@RequestBody LoanDto loanDto) {
        LoanDto saved = mapper.toDto(service.save(mapper.toEntity(loanDto)));
        return created("/api/loans/" + saved.getId(), saved);
    }

    @PreAuthorize("hasAuthority('LOAN_CREATE_OWN')")
    @PutMapping("/{id}")
    public ResponseEntity<LoanDto> update(@PathVariable Long id, @RequestBody LoanDto loanDto) {
        loanDto.setId(id);
        LoanDto updated = mapper.toDto(service.save(mapper.toEntity(loanDto)));
        return okOrNotFound(updated);
    }

    @PreAuthorize("hasAuthority('LOAN_CREATE_OWN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteById(id);
        return deletedSuccessfully();
    }
}
