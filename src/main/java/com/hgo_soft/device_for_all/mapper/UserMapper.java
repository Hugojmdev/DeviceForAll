package com.hgo_soft.device_for_all.mapper;

import com.hgo_soft.device_for_all.dto.UserDto;
import com.hgo_soft.device_for_all.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserMapper {
    public UserDto toDto(User user) {
        if (user == null) {
            throw new IllegalStateException("Null value cannot be mapped");
        }
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        return dto;
    }

    public List<UserDto> toDtoList(List<User> users){
        if(users == null) {
            throw new IllegalStateException("Null value cannot be mapped");
        }
        return users.stream().map(this::toDto).toList();
    }

    public User toEntity(UserDto UserDto) {
        if (UserDto == null) {
            throw new IllegalStateException("Null value cannot be mapped");
        }
        User user = new User();
        user.setId(UserDto.getId());
        return user;
    }

    public List<User> toEntityList(List<UserDto> UserDtos){
        if(UserDtos == null) {
            throw new IllegalStateException("Null value cannot be mapped");
        }
        return UserDtos.stream().map(this::toEntity).toList();
    }
}
