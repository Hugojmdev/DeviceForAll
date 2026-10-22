package com.hgo_soft.device_for_all.auth.mappers;

import com.hgo_soft.device_for_all.auth.dtos.RoleResponse;
import com.hgo_soft.device_for_all.auth.entities.Role;
import com.hgo_soft.device_for_all.common.mapping.MapperConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = MapperConfiguration.class)
public interface RoleMapper {
    RoleResponse toResponse(Role role);
    List<RoleResponse> toResponseList(List<Role> roles);
}
