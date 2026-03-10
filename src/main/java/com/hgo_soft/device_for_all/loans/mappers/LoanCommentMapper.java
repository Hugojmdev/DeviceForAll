package com.hgo_soft.device_for_all.loans.mappers;

import com.hgo_soft.device_for_all.loans.dtos.LoanCommentDto;
import com.hgo_soft.device_for_all.loans.entities.LoanComment;
import com.hgo_soft.device_for_all.common.mapping.MapperConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = MapperConfiguration.class)
public interface LoanCommentMapper {
    LoanCommentDto toDto(LoanComment loanComment);
    List<LoanCommentDto> toDtoList(List<LoanComment> loanComments);
    LoanComment toEntity(LoanCommentDto loanCommentDto);
    List<LoanComment> toEntityList(List<LoanCommentDto> loanCommentDtos);
}
