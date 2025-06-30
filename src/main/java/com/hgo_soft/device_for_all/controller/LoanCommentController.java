package com.hgo_soft.device_for_all.controller;

import com.hgo_soft.device_for_all.dto.LoanCommentDto;
import com.hgo_soft.device_for_all.mapper.LoanCommentMapper;
import com.hgo_soft.device_for_all.service.LoanCommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loan-comments")
public class LoanCommentController extends AbstractRestController {
    private final LoanCommentService service;
    private final LoanCommentMapper mapper;

    public LoanCommentController(LoanCommentService service, LoanCommentMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<LoanCommentDto>> getAll() {
        return okOrEmpty(mapper.toDtoList(service.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoanCommentDto> getById(@PathVariable Long id) {
        return okOrNotFound(
                service.findById(id).map(mapper::toDto)
        );
    }

    @PostMapping
    public ResponseEntity<LoanCommentDto> create(@RequestBody LoanCommentDto loanCommentDto) {
        LoanCommentDto saved = mapper.toDto(service.save(mapper.toEntity(loanCommentDto)));
        return created("/api/loan-comments/" + saved.getId(), saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LoanCommentDto> update(@PathVariable Long id, @RequestBody LoanCommentDto loanCommentDto) {
        loanCommentDto.setId(id);
        LoanCommentDto updated = mapper.toDto(service.save(mapper.toEntity(loanCommentDto)));
        return okOrNotFound(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteById(id);
        return deletedSuccessfully();
    }
}
