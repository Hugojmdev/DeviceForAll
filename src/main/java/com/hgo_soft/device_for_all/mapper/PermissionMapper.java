package com.hgo_soft.device_for_all.mapper;

import com.hgo_soft.device_for_all.dto.PermissionDto;
import com.hgo_soft.device_for_all.entity.Permission;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PermissionMapper {
    public PermissionDto toDto(Permission permission) {
        if (permission == null) {
            throw new IllegalStateException("Null value cannot be mapped");
        }
        PermissionDto dto = new PermissionDto();
        dto.setId(permission.getId());
        return dto;
    }

    public List<PermissionDto> toDtoList(List<Permission> permissions){
        if(permissions == null) {
            throw new IllegalStateException("Null value cannot be mapped");
        }
        return permissions.stream().map(this::toDto).toList();
    }

    public Permission toEntity(PermissionDto permissionDto) {
        if (permissionDto == null) {
            throw new IllegalStateException("Null value cannot be mapped");
        }
        Permission permission = new Permission();
        permission.setId(permissionDto.getId());
        return permission;
    }

    public List<Permission> toEntityList(List<PermissionDto> permissionDtos){
        if(permissionDtos == null) {
            throw new IllegalStateException("Null value cannot be mapped");
        }
        return permissionDtos.stream().map(this::toEntity).toList();
    }
}
