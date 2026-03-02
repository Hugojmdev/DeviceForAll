package com.hgo_soft.device_for_all.mapper;

import com.hgo_soft.device_for_all.dto.LoanDto;
import com.hgo_soft.device_for_all.entity.Loan;
import com.hgo_soft.device_for_all.mapper.config.MapperConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = MapperConfiguration.class)
public interface LoanMapper {
    LoanDto toDto(Loan loan);
    List<LoanDto> toDtoList(List<Loan> loans);
    Loan toEntity(LoanDto loanDto);
    List<Loan> toEntityList(List<LoanDto> loanDtos);
}
