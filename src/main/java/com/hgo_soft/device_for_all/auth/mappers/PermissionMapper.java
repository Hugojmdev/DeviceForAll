package com.hgo_soft.device_for_all.auth.mappers;

import com.hgo_soft.device_for_all.auth.dtos.PermissionDto;
import com.hgo_soft.device_for_all.auth.entities.Permission;
import com.hgo_soft.device_for_all.common.mapping.MapperConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = MapperConfiguration.class)
public interface PermissionMapper {
    PermissionDto toDto(Permission permission);
    List<PermissionDto> toDtoList(List<Permission> permissions);
    Permission toEntity(PermissionDto permissionDto);
    List<Permission> toEntityList(List<PermissionDto> permissionDtos);
}
