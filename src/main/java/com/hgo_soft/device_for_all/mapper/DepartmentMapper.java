package com.hgo_soft.device_for_all.mapper;

import com.hgo_soft.device_for_all.dto.DepartmentDto;
import com.hgo_soft.device_for_all.entity.Department;
import com.hgo_soft.device_for_all.mapper.config.MapperConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = MapperConfiguration.class)
public interface DepartmentMapper {
    DepartmentDto toDto(Department department);
    Department toEntity(DepartmentDto dto);
    List<DepartmentDto> toDtoList(List<Department> departments);
    List<Department> toEntityList(List<DepartmentDto> dtos);
}
