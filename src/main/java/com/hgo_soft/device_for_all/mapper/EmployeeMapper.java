package com.hgo_soft.device_for_all.mapper;

import com.hgo_soft.device_for_all.dto.EmployeeDto;
import com.hgo_soft.device_for_all.entity.Employee;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EmployeeMapper {
    public EmployeeDto toDto(Employee employee) {
        if (employee == null) {
            throw new IllegalStateException("Null value cannot be mapped");
        }
        EmployeeDto dto = new EmployeeDto();
        dto.setId(employee.getId());
        return dto;
    }

    public List<EmployeeDto> toDtoList(List<Employee> employees){
        if(employees == null) {
            throw new IllegalStateException("Null value cannot be mapped");
        }
        return employees.stream().map(this::toDto).toList();
    }

    public Employee toEntity(EmployeeDto employeeDto) {
        if (employeeDto == null) {
            throw new IllegalStateException("Null value cannot be mapped");
        }
        Employee employee = new Employee();
        employee.setId(employeeDto.getId());
        return employee;
    }

    public List<Employee> toEntityList(List<EmployeeDto> employeeDtos){
        if(employeeDtos == null)  {
            throw new IllegalStateException("Null value cannot be mapped");
        }
        return employeeDtos.stream().map(this::toEntity).toList();
    }
}
