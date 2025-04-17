package com.hgo_soft.device_for_all.mapper;

import com.hgo_soft.device_for_all.dto.TeacherDto;
import com.hgo_soft.device_for_all.entity.Teacher;

import java.util.ArrayList;
import java.util.List;

public class TeacherMapper {
    public static TeacherDto toDto(Teacher teacher) {
        if (teacher == null) {
            throw new IllegalStateException("Null value cannot be mapped");
        }
        TeacherDto dto = new TeacherDto();
        dto.setId(teacher.getId());
        return dto;
    }

    public static List<TeacherDto> toDtoList(List<Teacher> Teachers){
        if(Teachers == null) {
            throw new IllegalStateException("Null value cannot be mapped");
        }
        return Teachers.stream().map(TeacherMapper::toDto).toList();
    }

    public static Teacher toEntity(TeacherDto teacherDto) {
        if (teacherDto == null) {
            throw new IllegalStateException("Null value cannot be mapped");
        }
        Teacher teacher = new Teacher();
        teacher.setId(teacherDto.getId());
        return teacher;
    }

    public static List<Teacher> toEntityList(List<TeacherDto> TeacherDtos){
        if(TeacherDtos == null) {
            throw new IllegalStateException("Null value cannot be mapped");
        }
        return TeacherDtos.stream().map(TeacherMapper::toEntity).toList();
    }
}
