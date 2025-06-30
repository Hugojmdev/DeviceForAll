package com.hgo_soft.device_for_all.controller;

import com.hgo_soft.device_for_all.dto.LoanDto;
import com.hgo_soft.device_for_all.mapper.LoanMapper;
import com.hgo_soft.device_for_all.service.LoanService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loans")
public class LoanController extends AbstractRestController{

    private final LoanService service;
    private final LoanMapper mapper;

    public LoanController(LoanService service, LoanMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<LoanDto>> getAll() {
        return okOrEmpty(mapper.toDtoList(service.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoanDto> getById(@PathVariable Long id) {
        return okOrNotFound(
                service.findById(id).map(mapper::toDto)
        );
    }

    @PostMapping
    public ResponseEntity<LoanDto> create(@RequestBody LoanDto loanDto) {
        LoanDto saved = mapper.toDto(service.save(mapper.toEntity(loanDto)));
        return created("/api/loans/" + saved.getId(), saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LoanDto> update(@PathVariable Long id, @RequestBody LoanDto loanDto) {
        loanDto.setId(id);
        LoanDto updated = mapper.toDto(service.save(mapper.toEntity(loanDto)));
        return okOrNotFound(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteById(id);
        return deletedSuccessfully();
    }
}
