package com.hgo_soft.device_for_all.users.mappers;

import com.hgo_soft.device_for_all.users.dtos.EmployeeDto;
import com.hgo_soft.device_for_all.users.entities.Employee;
import com.hgo_soft.device_for_all.common.mapping.MapperConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = MapperConfiguration.class)
public interface EmployeeMapper {
    EmployeeDto toDto(Employee employee);
    List<EmployeeDto> toDtoList(List<Employee> employees);
    Employee toEntity(EmployeeDto employeeDto);
    List<Employee> toEntityList(List<EmployeeDto> employeeDtos);
}
