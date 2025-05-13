package com.hgo_soft.device_for_all.service;

import com.hgo_soft.device_for_all.entity.Student;
import com.hgo_soft.device_for_all.repository.StudentRepository;
import com.hgo_soft.device_for_all.service.impl.StudentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class StudentServiceImplTest {
    private StudentRepository repository;
    private StudentServiceImpl service;

    @BeforeEach
    void setUp() {
        repository = mock(StudentRepository.class);
        service = new StudentServiceImpl(repository);
    }

    @Test
    void testFindAll_ShouldReturnStudents() {
        List<Student> students = Arrays.asList(Student.builder().id(1L).build(), Student.builder().id(2L).build());
        when(repository.findAll()).thenReturn(students);

        List<Student> result = service.findAll();

        assertEquals(2, result.size());
        verify(repository).findAll();
    }

    @Test
    void testFindById_WhenFound() {
        Student student = Student.builder().id(1L).build();
        when(repository.findById(1L)).thenReturn(Optional.of(student));

        Optional<Student> result = service.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
    }

    @Test
    void testFindById_WhenNotFound() {
        when(repository.findById(999L)).thenReturn(Optional.empty());

        Optional<Student> result = service.findById(999L);

        assertFalse(result.isPresent());
    }

    @Test
    void testSave_ShouldReturnSavedStudent() {
        Student toSave = new Student();
        Student saved = Student.builder().id(10L).build();

        when(repository.save(ArgumentMatchers.any())).thenReturn(saved);

        Student result = service.save(toSave);

        assertNotNull(result);
        assertEquals(10L, result.getId());
    }

    @Test
    void testUpdate_ShouldReturnUpdatedStudent() {
        Student student = Student.builder().id(3L).build();
        when(repository.save(student)).thenReturn(student);

        Student result = service.save(student);

        assertEquals(3L, result.getId());
    }

    @Test
    void testDeleteById() {
        doNothing().when(repository).deleteById(5L);

        service.deleteById(5L);

        verify(repository, times(1)).deleteById(5L);
    }
}
