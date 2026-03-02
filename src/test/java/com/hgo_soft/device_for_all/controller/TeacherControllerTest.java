package com.hgo_soft.device_for_all.controller;

import com.hgo_soft.device_for_all.dto.TeacherDto;
import com.hgo_soft.device_for_all.entity.Teacher;
import com.hgo_soft.device_for_all.mapper.TeacherMapper;
import com.hgo_soft.device_for_all.mapper.TeacherMapperImpl;
import com.hgo_soft.device_for_all.service.TeacherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

public class TeacherControllerTest {
    private TeacherService service;
    private TeacherController controller;

    @BeforeEach
    void setUp() {
        service = mock(TeacherService.class);
        TeacherMapper mapper = new TeacherMapperImpl();
        controller = new TeacherController(service, mapper);
    }

    @Test
    void testGetAll_ShouldReturnListOfTeachers() {
        List<Teacher> teachers = Arrays.asList(
                Teacher.builder().id(1L).build(),
                Teacher.builder().id(2L).build()
        );
        when(service.findAll()).thenReturn(teachers);

        ResponseEntity<List<TeacherDto>> response = controller.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        verify(service).findAll();
    }

    @Test
    void testGetAll_ShouldReturnNoContent() {
        when(service.findAll()).thenReturn(Collections.emptyList());

        ResponseEntity<List<TeacherDto>> response = controller.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isEmpty());
    }

    @Test
    void testGetById_Found() {
        long id = 1L;
        Teacher entityToFound = Teacher.builder().id(id).build();
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
