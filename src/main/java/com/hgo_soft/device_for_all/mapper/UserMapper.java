package com.hgo_soft.device_for_all.mapper;

import com.hgo_soft.device_for_all.dto.UserDto;
import com.hgo_soft.device_for_all.entity.User;
import com.hgo_soft.device_for_all.mapper.config.MapperConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = MapperConfiguration.class)
public interface UserMapper {
    UserDto toDto(User user);
    List<UserDto> toDtoList(List<User> users);
    User toEntity(UserDto UserDto);
    List<User> toEntityList(List<UserDto> UserDtos);
}
