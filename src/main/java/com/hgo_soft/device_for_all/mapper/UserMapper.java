package com.hgo_soft.device_for_all.mapper;

import com.hgo_soft.device_for_all.dto.UserDto;
import com.hgo_soft.device_for_all.entity.User;

import java.util.List;

public class UserMapper {
    public static UserDto toDto(User user) {
        if (user == null) return null;
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        return dto;
    }

    public static List<UserDto> toDtoList(List<User> users){
        if(users == null) return null;
        return users.stream().map(UserMapper::toDto).toList();
    }

    public static User toEntity(UserDto UserDto) {
        if (UserDto == null) return null;
        User user = new User();
        user.setId(UserDto.getId());
        return user;
    }

    public static List<User> toEntityList(List<UserDto> UserDtos){
        if(UserDtos == null) return null;
        return UserDtos.stream().map(UserMapper::toEntity).toList();
    }
}
