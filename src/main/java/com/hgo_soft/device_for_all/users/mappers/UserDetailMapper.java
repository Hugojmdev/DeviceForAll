package com.hgo_soft.device_for_all.users.mappers;

import com.hgo_soft.device_for_all.users.dtos.UserDetailDto;
import com.hgo_soft.device_for_all.users.entities.UserDetail;
import com.hgo_soft.device_for_all.common.mapping.MapperConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = MapperConfiguration.class)
public interface UserDetailMapper {
    UserDetailDto toDto(UserDetail userDetail);
    List<UserDetailDto> toDtoList(List<UserDetail> userDetails);
    UserDetail toEntity(UserDetailDto userDetailDto);
    List<UserDetail> toEntityList(List<UserDetailDto> userDetailDtos);
}
