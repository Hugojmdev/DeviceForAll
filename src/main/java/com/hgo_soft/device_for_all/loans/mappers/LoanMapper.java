package com.hgo_soft.device_for_all.loans.mappers;

import com.hgo_soft.device_for_all.loans.dtos.LoanDto;
import com.hgo_soft.device_for_all.loans.entities.Loan;
import com.hgo_soft.device_for_all.common.mapping.MapperConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = MapperConfiguration.class)
public interface LoanMapper {
    LoanDto toDto(Loan loan);
    List<LoanDto> toDtoList(List<Loan> loans);
    Loan toEntity(LoanDto loanDto);
    List<Loan> toEntityList(List<LoanDto> loanDtos);
}
