package com.hgo_soft.device_for_all.auth.mappers;

import com.hgo_soft.device_for_all.auth.dtos.RoleDto;
import com.hgo_soft.device_for_all.auth.entities.Role;
import com.hgo_soft.device_for_all.common.mapping.MapperConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = MapperConfiguration.class)
public interface RoleMapper {
    RoleDto toDto(Role role);
    List<RoleDto> toDtoList(List<Role> roles);
    Role toEntity(RoleDto roleDto);
    List<Role> toEntityList(List<RoleDto> roleDtos);
}
