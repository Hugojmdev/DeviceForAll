package com.hgo_soft.device_for_all.mapper;

import com.hgo_soft.device_for_all.dto.LoanDto;
import com.hgo_soft.device_for_all.entity.Loan;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LoanMapper {
    public LoanDto toDto(Loan loan) {
        if (loan == null) {
            throw new IllegalStateException("Null value cannot be mapped");
        }
        LoanDto dto = new LoanDto();
        dto.setId(loan.getId());
        return dto;
    }

    public List<LoanDto> toDtoList(List<Loan> loans){
        if(loans == null) {
            throw new IllegalStateException("Null value cannot be mapped");
        }
        return loans.stream().map(this::toDto).toList();
    }

    public Loan toEntity(LoanDto loanDto) {
        if (loanDto == null) {
            throw new IllegalStateException("Null value cannot be mapped");
        }
        Loan loan = new Loan();
        loan.setId(loanDto.getId());
        return loan;
    }

    public List<Loan> toEntityList(List<LoanDto> loanDtos){
        if(loanDtos == null) {
            throw new IllegalStateException("Null value cannot be mapped");
        }
        return loanDtos.stream().map(this::toEntity).toList();
    }
}
