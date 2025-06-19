package com.hgo_soft.device_for_all.mapper;

import com.hgo_soft.device_for_all.dto.DepartmentDto;
import com.hgo_soft.device_for_all.entity.Department;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DepartmentMapper {

    public DepartmentDto toDto(Department department) {
        if (department == null) {
            throw new IllegalStateException("Null value cannot be mapped");
        }
        DepartmentDto dto = new DepartmentDto();
        dto.setId(department.getId());
        return dto;
    }

    public List<DepartmentDto> toDtoList(List<Department> departments){
        if(departments == null) {
            throw new IllegalStateException("Null value cannot be mapped");
        }
        return departments.stream().map(this::toDto).toList();
    }

    public Department toEntity(DepartmentDto departmentDto) {
        if (departmentDto == null) {
            throw new IllegalStateException("Null value cannot be mapped");
        }
        Department department = new Department();
        department.setId(departmentDto.getId());
        return department;
    }

    public List<Department> toEntityList(List<DepartmentDto> departmentDtos){
        if(departmentDtos == null) {
            throw new IllegalStateException("Null value cannot be mapped");
        }
        return departmentDtos.stream().map(this::toEntity).toList();
    }
}
