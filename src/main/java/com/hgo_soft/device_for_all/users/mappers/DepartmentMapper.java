package com.hgo_soft.device_for_all.users.mappers;

import com.hgo_soft.device_for_all.users.dtos.DepartmentDto;
import com.hgo_soft.device_for_all.users.entities.Department;
import com.hgo_soft.device_for_all.common.mapping.MapperConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = MapperConfiguration.class)
public interface DepartmentMapper {
    DepartmentDto toDto(Department department);
    Department toEntity(DepartmentDto dto);
    List<DepartmentDto> toDtoList(List<Department> departments);
    List<Department> toEntityList(List<DepartmentDto> dtos);
}
