package com.hgo_soft.device_for_all.mapper;

import com.hgo_soft.device_for_all.dto.UserDetailDto;
import com.hgo_soft.device_for_all.entity.UserDetail;
import com.hgo_soft.device_for_all.mapper.config.MapperConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = MapperConfiguration.class)
public interface UserDetailMapper {
    UserDetailDto toDto(UserDetail userDetail);
    List<UserDetailDto> toDtoList(List<UserDetail> userDetails);
    UserDetail toEntity(UserDetailDto userDetailDto);
    List<UserDetail> toEntityList(List<UserDetailDto> userDetailDtos);
}
