package com.hgo_soft.device_for_all.service;

import com.hgo_soft.device_for_all.entity.Role;
import com.hgo_soft.device_for_all.repository.RoleRepository;
import com.hgo_soft.device_for_all.service.impl.RoleServiceImpl;
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

public class RoleServiceImplTest {
    private RoleRepository repository;
    private RoleServiceImpl service;

    @BeforeEach
    void setUp() {
        repository = mock(RoleRepository.class);
        service = new RoleServiceImpl(repository);
    }

    @Test
    void testFindAll_ShouldReturnRoles() {
        List<Role> roles = Arrays.asList(Role.builder().id(1L).build(), Role.builder().id(2L).build());
        when(repository.findAll()).thenReturn(roles);

        List<Role> result = service.findAll();

        assertEquals(2, result.size());
        verify(repository).findAll();
    }

    @Test
    void testFindById_WhenFound() {
        Role role = Role.builder().id(1L).build();
        when(repository.findById(1L)).thenReturn(Optional.of(role));

        Optional<Role> result = service.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
    }

    @Test
    void testFindById_WhenNotFound() {
        when(repository.findById(999L)).thenReturn(Optional.empty());

        Optional<Role> result = service.findById(999L);

        assertFalse(result.isPresent());
    }

    @Test
    void testSave_ShouldReturnSavedRole() {
        Role toSave = new Role();
        Role saved = Role.builder().id(10L).build();

        when(repository.save(ArgumentMatchers.any())).thenReturn(saved);

        Role result = service.save(toSave);

        assertNotNull(result);
        assertEquals(10L, result.getId());
    }

    @Test
    void testUpdate_ShouldReturnUpdatedRole() {
        Role role = Role.builder().id(3L).build();
        when(repository.save(role)).thenReturn(role);

        Role result = service.save(role);

        assertEquals(3L, result.getId());
    }

    @Test
    void testDeleteById() {
        doNothing().when(repository).deleteById(5L);

        service.deleteById(5L);

        verify(repository, times(1)).deleteById(5L);
    }
}
