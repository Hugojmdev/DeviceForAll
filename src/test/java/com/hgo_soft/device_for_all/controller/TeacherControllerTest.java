package com.hgo_soft.device_for_all.controller;

import com.hgo_soft.device_for_all.dto.TeacherDto;
import com.hgo_soft.device_for_all.entity.Teacher;
import com.hgo_soft.device_for_all.mapper.TeacherMapper;
import com.hgo_soft.device_for_all.service.TeacherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

public class TeacherControllerTest {
    private TeacherService service;
    private TeacherMapper mapper;
    private TeacherController controller;

    @BeforeEach
    void setUp() {
        service = mock(TeacherService.class);
        mapper = mock(TeacherMapper.class);
        controller = new TeacherController(service, mapper);
    }

    @Test
    void testGetAll_ShouldReturnListOfTeachers() {
        List<Teacher> teachers = Arrays.asList(
                Teacher.builder().id(1L).build(),
                Teacher.builder().id(2L).build()
        );
        List<TeacherDto> teacherDtos = Arrays.asList(
                TeacherDto.builder().id(1L).build(),
                TeacherDto.builder().id(2L).build()
        );
        when(mapper.toEntityList(anyList())).thenReturn(teachers);
        when(mapper.toDtoList(anyList())).thenReturn(teacherDtos);

        when(service.findAll()).thenReturn(teachers);

        ResponseEntity<List<TeacherDto>> response = controller.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        verify(service).findAll();
    }

    @Test
    void testGetAll_ShouldReturnNoContent() {
        List<TeacherDto> teacherDtos = new ArrayList<>();
        List<Teacher> teachers = new ArrayList<>();
        when(mapper.toEntityList(anyList())).thenReturn(teachers);
        when(mapper.toDtoList(anyList())).thenReturn(teacherDtos);
        when(service.findAll()).thenReturn(Collections.emptyList());

        ResponseEntity<List<TeacherDto>> response = controller.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testGetById_Found() {
        long id = 1L;
        TeacherDto inputDto = TeacherDto.builder().id(id).build();
        Teacher entityToFound = Teacher.builder().id(id).build();
        TeacherDto dtoToFound = TeacherDto.builder().id(id).build();
        when(mapper.toEntity(inputDto)).thenReturn(entityToFound);
        when(mapper.toDto(entityToFound)).thenReturn(dtoToFound);
        when(service.findById(any())).thenReturn(Optional.of(entityToFound));

        ResponseEntity<TeacherDto> response = controller.getById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(id, response.getBody().getId());
    }

    @Test
    void testGetById_NotFound() {
        when(service.findById(999L)).thenReturn(Optional.empty());

        ResponseEntity<TeacherDto> response = controller.getById(999L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testCreate_ShouldReturnCreatedTeacher() {
        long id = 10L;
        TeacherDto inputDto = new TeacherDto();
        Teacher savedEntity = Teacher.builder().id(id).build();
        TeacherDto savedDto = TeacherDto.builder().id(id).build();
        // simulate mapper and persistence
        when(mapper.toEntity(inputDto)).thenReturn(savedEntity);
        when(mapper.toDto(savedEntity)).thenReturn(savedDto);
        when(service.save(any())).thenReturn(savedEntity);
        ResponseEntity<TeacherDto> response = controller.create(inputDto);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("/api/teachers/10", Objects.requireNonNull(response.getHeaders().getLocation()).toString());
    }

    @Test
    void testUpdate_ShouldReturnUpdatedTeacher() {
        Long id = 5L;
        TeacherDto inputDto = TeacherDto.builder().id(id).build();
        Teacher savedEntity = Teacher.builder().id(id).build();
        TeacherDto savedDto = TeacherDto.builder().id(id).build();
        when(mapper.toEntity(inputDto)).thenReturn(savedEntity);
        when(mapper.toDto(savedEntity)).thenReturn(savedDto);
        when(service.save(any())).thenReturn(savedEntity);
        ResponseEntity<TeacherDto> response = controller.update(id, inputDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(id, response.getBody().getId());
    }

    @Test
    void testDelete_ShouldReturnOk() {
        doNothing().when(service).deleteById(1L);

        ResponseEntity<Void> response = controller.delete(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(service).deleteById(1L);
    }
}
