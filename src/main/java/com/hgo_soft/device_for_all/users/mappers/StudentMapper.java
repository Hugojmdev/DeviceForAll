package com.hgo_soft.device_for_all.users.mappers;

import com.hgo_soft.device_for_all.users.dtos.StudentDto;
import com.hgo_soft.device_for_all.users.entities.Student;
import com.hgo_soft.device_for_all.common.mapping.MapperConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = MapperConfiguration.class)
public interface StudentMapper {
    StudentDto toDto(Student student);
    List<StudentDto> toDtoList(List<Student> students);
    Student toEntity(StudentDto studentDto);
    List<Student> toEntityList(List<StudentDto> studentDtos);
}
