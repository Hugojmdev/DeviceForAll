package com.hgo_soft.device_for_all.mapper;

import com.hgo_soft.device_for_all.dto.EmployeeDto;
import com.hgo_soft.device_for_all.entity.Employee;

import java.util.ArrayList;
import java.util.List;

public class EmployeeMapper {
    public static EmployeeDto toDto(Employee employee) {
        if (employee == null) {
            throw new IllegalStateException("Null value cannot be mapped");
        }
        EmployeeDto dto = new EmployeeDto();
        dto.setId(employee.getId());
        return dto;
    }

    public static List<EmployeeDto> toDtoList(List<Employee> employees){
        if(employees == null) {
            throw new IllegalStateException("Null value cannot be mapped");
        }
        return employees.stream().map(EmployeeMapper::toDto).toList();
    }

    public static Employee toEntity(EmployeeDto employeeDto) {
        if (employeeDto == null) {
            throw new IllegalStateException("Null value cannot be mapped");
        }
        Employee employee = new Employee();
        employee.setId(employeeDto.getId());
        return employee;
    }

    public static List<Employee> toEntityList(List<EmployeeDto> employeeDtos){
        if(employeeDtos == null)  {
            throw new IllegalStateException("Null value cannot be mapped");
        }
        return employeeDtos.stream().map(EmployeeMapper::toEntity).toList();
    }
}
