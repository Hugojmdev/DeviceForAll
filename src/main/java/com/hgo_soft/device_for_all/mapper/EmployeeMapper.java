package com.hgo_soft.device_for_all.mapper;

import com.hgo_soft.device_for_all.dto.EmployeeDto;
import com.hgo_soft.device_for_all.entity.Employee;
import com.hgo_soft.device_for_all.mapper.config.MapperConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = MapperConfiguration.class)
public interface EmployeeMapper {
    EmployeeDto toDto(Employee employee);
    List<EmployeeDto> toDtoList(List<Employee> employees);
    Employee toEntity(EmployeeDto employeeDto);
    List<Employee> toEntityList(List<EmployeeDto> employeeDtos);
}
