package com.hgo_soft.device_for_all.mapper;

import com.hgo_soft.device_for_all.dto.RoleDto;
import com.hgo_soft.device_for_all.entity.Role;

import java.util.ArrayList;
import java.util.List;

public class RoleMapper {
    public static RoleDto toDto(Role role) {
        if (role == null) {
            throw new IllegalStateException("Null value cannot be mapped");
        }
        RoleDto dto = new RoleDto();
        dto.setId(role.getId());
        dto.setName(role.getName());
        return dto;
    }

    public static List<RoleDto> toDtoList(List<Role> roles){
        if(roles == null) {
            throw new IllegalStateException("Null value cannot be mapped");
        }
        return roles.stream().map(RoleMapper::toDto).toList();
    }

    public static Role toEntity(RoleDto roleDto) {
        if (roleDto == null) {
            throw new IllegalStateException("Null value cannot be mapped");
        }
        Role role = new Role();
        role.setId(roleDto.getId());
        role.setName(roleDto.getName());
        return role;
    }

    public static List<Role> toEntityList(List<RoleDto> roleDtos){
        if(roleDtos == null) {
            throw new IllegalStateException("Null value cannot be mapped");
        }
        return roleDtos.stream().map(RoleMapper::toEntity).toList();
    }
}
