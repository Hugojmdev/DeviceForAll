package com.hgo_soft.device_for_all.mapper;

import com.hgo_soft.device_for_all.dto.StudentDto;
import com.hgo_soft.device_for_all.entity.Student;
import com.hgo_soft.device_for_all.mapper.config.MapperConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = MapperConfiguration.class)
public interface StudentMapper {
    StudentDto toDto(Student student);
    List<StudentDto> toDtoList(List<Student> students);
    Student toEntity(StudentDto studentDto);
    List<Student> toEntityList(List<StudentDto> studentDtos);
}
