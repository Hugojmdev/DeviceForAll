package com.hgo_soft.device_for_all.users.mappers;

import com.hgo_soft.device_for_all.users.dtos.UserProfileDto;
import com.hgo_soft.device_for_all.users.entities.UserProfile;
import com.hgo_soft.device_for_all.common.mapping.MapperConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = MapperConfiguration.class)
public interface UserProfileMapper {
    UserProfileDto toDto(UserProfile userProfile);
    List<UserProfileDto> toDtoList(List<UserProfile> userProfiles);
    UserProfile toEntity(UserProfileDto userProfileDto);
    List<UserProfile> toEntityList(List<UserProfileDto> userProfileDtos);
}
