package com.hgo_soft.device_for_all.users.mappers;

import com.hgo_soft.device_for_all.users.dtos.TeacherDto;
import com.hgo_soft.device_for_all.users.entities.Teacher;
import com.hgo_soft.device_for_all.common.mapping.MapperConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = MapperConfiguration.class)
public interface TeacherMapper {
    TeacherDto toDto(Teacher teacher);
    List<TeacherDto> toDtoList(List<Teacher> Teachers);
    Teacher toEntity(TeacherDto teacherDto);
    List<Teacher> toEntityList(List<TeacherDto> TeacherDtos);
}
