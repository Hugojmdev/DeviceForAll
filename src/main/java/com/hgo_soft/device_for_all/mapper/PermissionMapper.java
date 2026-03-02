package com.hgo_soft.device_for_all.mapper;

import com.hgo_soft.device_for_all.dto.PermissionDto;
import com.hgo_soft.device_for_all.entity.Permission;
import com.hgo_soft.device_for_all.mapper.config.MapperConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = MapperConfiguration.class)
public interface PermissionMapper {
    PermissionDto toDto(Permission permission);
    List<PermissionDto> toDtoList(List<Permission> permissions);
    Permission toEntity(PermissionDto permissionDto);
    List<Permission> toEntityList(List<PermissionDto> permissionDtos);
}
