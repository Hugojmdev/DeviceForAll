package com.hgo_soft.device_for_all.mapper;

import com.hgo_soft.device_for_all.dto.LoanCommentDto;
import com.hgo_soft.device_for_all.entity.LoanComment;
import com.hgo_soft.device_for_all.mapper.config.MapperConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = MapperConfiguration.class)
public interface LoanCommentMapper {
    LoanCommentDto toDto(LoanComment loanComment);
    List<LoanCommentDto> toDtoList(List<LoanComment> loanComments);
    LoanComment toEntity(LoanCommentDto loanCommentDto);
    List<LoanComment> toEntityList(List<LoanCommentDto> loanCommentDtos);
}
