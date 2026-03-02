package com.hgo_soft.device_for_all.mapper;

import com.hgo_soft.device_for_all.dto.TeacherDto;
import com.hgo_soft.device_for_all.entity.Teacher;
import com.hgo_soft.device_for_all.mapper.config.MapperConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = MapperConfiguration.class)
public interface TeacherMapper {
    TeacherDto toDto(Teacher teacher);
    List<TeacherDto> toDtoList(List<Teacher> Teachers);
    Teacher toEntity(TeacherDto teacherDto);
    List<Teacher> toEntityList(List<TeacherDto> TeacherDtos);
}
