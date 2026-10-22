package com.hgo_soft.device_for_all.auth.mappers;

import com.hgo_soft.device_for_all.auth.dtos.PermissionResponse;
import com.hgo_soft.device_for_all.auth.entities.Permission;
import com.hgo_soft.device_for_all.common.mapping.MapperConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = MapperConfiguration.class)
public interface PermissionMapper {
    PermissionResponse toResponse(Permission permission);
    List<PermissionResponse> toResponseList(List<Permission> permissions);
}
