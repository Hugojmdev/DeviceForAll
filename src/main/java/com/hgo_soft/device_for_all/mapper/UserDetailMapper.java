package com.hgo_soft.device_for_all.mapper;

import com.hgo_soft.device_for_all.dto.UserDetailDto;
import com.hgo_soft.device_for_all.entity.UserDetail;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserDetailMapper {
    public UserDetailDto toDto(UserDetail userDetail) {
        if (userDetail == null) {
            throw new IllegalStateException("Null value cannot be mapped");
        }
        UserDetailDto dto = new UserDetailDto();
        dto.setId(userDetail.getId());
        return dto;
    }

    public List<UserDetailDto> toDtoList(List<UserDetail> userDetails){
        if(userDetails == null) {
            throw new IllegalStateException("Null value cannot be mapped");
        }
        return userDetails.stream().map(this::toDto).toList();
    }

    public UserDetail toEntity(UserDetailDto userDetailDto) {
        if (userDetailDto == null) {
            throw new IllegalStateException("Null value cannot be mapped");
        }
        UserDetail userDetail = new UserDetail();
        userDetail.setId(userDetailDto.getId());
        return userDetail;
    }

    public List<UserDetail> toEntityList(List<UserDetailDto> userDetailDtos){
        if(userDetailDtos == null) {
            throw new IllegalStateException("Null value cannot be mapped");
        }
        return userDetailDtos.stream().map(this::toEntity).toList();
    }
}
