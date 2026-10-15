package com.hgo_soft.device_for_all.users.mappers;

import com.hgo_soft.device_for_all.users.dtos.*;
import com.hgo_soft.device_for_all.users.entities.User;
import com.hgo_soft.device_for_all.common.mapping.MapperConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(config = MapperConfiguration.class)
public interface UserMapper {
    UserResponse toUserResponse(User user);
    User toEntity(CreateUserRequest createUserRequest);
    void updateEntity(UpdateUserRequest updateUserRequest, @MappingTarget User user);
    List<UserResponse> toUserResponseList(List<User> users);
}
