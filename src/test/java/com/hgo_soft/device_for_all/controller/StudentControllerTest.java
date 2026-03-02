package com.hgo_soft.device_for_all.controller;

import com.hgo_soft.device_for_all.dto.StudentDto;
import com.hgo_soft.device_for_all.entity.Student;
import com.hgo_soft.device_for_all.mapper.StudentMapper;
import com.hgo_soft.device_for_all.mapper.StudentMapperImpl;
import com.hgo_soft.device_for_all.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

public class StudentControllerTest {
    
    private StudentService service;
    private StudentController controller;

    @BeforeEach
    void setUp() {
        service = mock(StudentService.class);
        StudentMapper mapper = new StudentMapperImpl();
        controller = new StudentController(service, mapper);
    }

    @Test
    void testGetAll_ShouldReturnListOfStudents() {
        List<Student> students = Arrays.asList(
                Student.builder().id(1L).build(),
                Student.builder().id(2L).build()
        );
        when(service.findAll()).thenReturn(students);

        ResponseEntity<List<StudentDto>> response = controller.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        verify(service).findAll();
    }

    @Test
    void testGetAll_ShouldReturnNoContent() {
        when(service.findAll()).thenReturn(Collections.emptyList());

        ResponseEntity<List<StudentDto>> response = controller.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isEmpty());

    }

    @Test
    void testGetById_Found() {
        long id = 1L;
        Student entityToFound = Student.builder().id(id).build();
        when(service.findById(any())).thenReturn(Optional.of(entityToFound));

        ResponseEntity<StudentDto> response = controller.getById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(id, response.getBody().getId());
    }

    @Test
    void testGetById_NotFound() {
        when(service.findById(999L)).thenReturn(Optional.empty());

        ResponseEntity<StudentDto> response = controller.getById(999L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testCreate_ShouldReturnCreatedStudent() {
        long id = 10L;
        StudentDto inputDto = new StudentDto();
        Student savedEntity = Student.builder().id(id).build();
        when(service.save(any())).thenReturn(savedEntity);
        ResponseEntity<StudentDto> response = controller.create(inputDto);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("/api/students/10", Objects.requireNonNull(response.getHeaders().getLocation()).toString());
    }

    @Test
    void testUpdate_ShouldReturnUpdatedStudent() {
        Long id = 5L;
        StudentDto inputDto = StudentDto.builder().id(id).build();
        Student savedEntity = Student.builder().id(id).build();
        when(service.save(any())).thenReturn(savedEntity);
        ResponseEntity<StudentDto> response = controller.update(id, inputDto);
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
