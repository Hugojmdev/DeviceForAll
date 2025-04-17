package com.hgo_soft.device_for_all.mapper;

import com.hgo_soft.device_for_all.dto.UserDto;
import com.hgo_soft.device_for_all.entity.User;

import java.util.ArrayList;
import java.util.List;

public class UserMapper {
    public static UserDto toDto(User user) {
        if (user == null) {
            throw new IllegalStateException("Null value cannot be mapped");
        }
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        return dto;
    }

    public static List<UserDto> toDtoList(List<User> users){
        if(users == null) {
            throw new IllegalStateException("Null value cannot be mapped");
        }
        return users.stream().map(UserMapper::toDto).toList();
    }

    public static User toEntity(UserDto UserDto) {
        if (UserDto == null) {
            throw new IllegalStateException("Null value cannot be mapped");
        }
        User user = new User();
        user.setId(UserDto.getId());
        return user;
    }

    public static List<User> toEntityList(List<UserDto> UserDtos){
        if(UserDtos == null) {
            throw new IllegalStateException("Null value cannot be mapped");
        }
        return UserDtos.stream().map(UserMapper::toEntity).toList();
    }
}
