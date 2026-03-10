package com.hgo_soft.device_for_all.users.mappers;

import com.hgo_soft.device_for_all.users.dtos.UserDto;
import com.hgo_soft.device_for_all.users.entities.User;
import com.hgo_soft.device_for_all.common.mapping.MapperConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = MapperConfiguration.class)
public interface UserMapper {
    UserDto toDto(User user);
    List<UserDto> toDtoList(List<User> users);
    User toEntity(UserDto UserDto);
    List<User> toEntityList(List<UserDto> UserDtos);
}
