package com.hgo_soft.device_for_all.mapper;

import com.hgo_soft.device_for_all.dto.StudentDto;
import com.hgo_soft.device_for_all.entity.Student;

import java.util.ArrayList;
import java.util.List;

public class StudentMapper {
    public static StudentDto toDto(Student student) {
        if (student == null) {
            throw new IllegalStateException("Null value cannot be mapped");
        }
        StudentDto dto = new StudentDto();
        dto.setId(student.getId());
        return dto;
    }

    public static List<StudentDto> toDtoList(List<Student> students){
        if(students == null) {
            throw new IllegalStateException("Null value cannot be mapped");
        }
        return students.stream().map(StudentMapper::toDto).toList();
    }

    public static Student toEntity(StudentDto studentDto) {
        if (studentDto == null) {
            throw new IllegalStateException("Null value cannot be mapped");
        }
        Student student = new Student();
        student.setId(studentDto.getId());
        return student;
    }

    public static List<Student> toEntityList(List<StudentDto> studentDtos){
        if(studentDtos == null) {
            throw new IllegalStateException("Null value cannot be mapped");
        }
        return studentDtos.stream().map(StudentMapper::toEntity).toList();
    }
}
