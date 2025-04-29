package com.hgo_soft.device_for_all.service;

import com.hgo_soft.device_for_all.entity.Teacher;
import com.hgo_soft.device_for_all.repository.TeacherRepository;
import com.hgo_soft.device_for_all.service.impl.TeacherServiceImpl;
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

public class TeacherServiceImplTest {
    private TeacherRepository repository;
    private TeacherServiceImpl service;

    @BeforeEach
    void setUp() {
        repository = mock(TeacherRepository.class);
        service = new TeacherServiceImpl(repository);
    }

    @Test
    void testFindAll_ShouldReturnTeachers() {
        List<Teacher> teachers = Arrays.asList(Teacher.builder().id(1L).build(), Teacher.builder().id(2L).build());
        when(repository.findAll()).thenReturn(teachers);

        List<Teacher> result = service.findAll();

        assertEquals(2, result.size());
        verify(repository).findAll();
    }

    @Test
    void testFindById_WhenFound() {
        Teacher teacher = Teacher.builder().id(1L).build();
        when(repository.findById(1L)).thenReturn(Optional.of(teacher));

        Optional<Teacher> result = service.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
    }

    @Test
    void testFindById_WhenNotFound() {
        when(repository.findById(999L)).thenReturn(Optional.empty());

        Optional<Teacher> result = service.findById(999L);

        assertFalse(result.isPresent());
    }

    @Test
    void testSave_ShouldReturnSavedTeacher() {
        Teacher toSave = new Teacher();
        Teacher saved = Teacher.builder().id(10L).build();

        when(repository.save(ArgumentMatchers.any())).thenReturn(saved);

        Teacher result = service.save(toSave);

        assertNotNull(result);
        assertEquals(10L, result.getId());
    }

    @Test
    void testUpdate_ShouldReturnUpdatedTeacher() {
        Teacher teacher = Teacher.builder().id(3L).build();
        when(repository.save(teacher)).thenReturn(teacher);

        Teacher result = service.save(teacher);

        assertEquals(3L, result.getId());
    }

    @Test
    void testDeleteById() {
        doNothing().when(repository).deleteById(5L);

        service.deleteById(5L);

        verify(repository, times(1)).deleteById(5L);
    }
}
