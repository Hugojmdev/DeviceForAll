package com.hgo_soft.device_for_all.mapper;

import com.hgo_soft.device_for_all.dto.LoanCommentDto;
import com.hgo_soft.device_for_all.entity.LoanComment;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LoanCommentMapper {
    public LoanCommentDto toDto(LoanComment loanComment) {
        if (loanComment == null) {
            throw new IllegalStateException("Null value cannot be mapped");
        }
        LoanCommentDto dto = new LoanCommentDto();
        dto.setId(loanComment.getId());
        return dto;
    }

    public List<LoanCommentDto> toDtoList(List<LoanComment> loanComments){
        if(loanComments == null) {
            throw new IllegalStateException("Null value cannot be mapped");
        }
        return loanComments.stream().map(this::toDto).toList();
    }

    public LoanComment toEntity(LoanCommentDto loanCommentDto) {
        if (loanCommentDto == null) {
            throw new IllegalStateException("Null value cannot be mapped");
        }
        LoanComment loanComment = new LoanComment();
        loanComment.setId(loanCommentDto.getId());
        return loanComment;
    }

    public List<LoanComment> toEntityList(List<LoanCommentDto> loanCommentDtos){
        if(loanCommentDtos == null) {
            throw new IllegalStateException("Null value cannot be mapped");
        }
        return loanCommentDtos.stream().map(this::toEntity).toList();
    }
}
