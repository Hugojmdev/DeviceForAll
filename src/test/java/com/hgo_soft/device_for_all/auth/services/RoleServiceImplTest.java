package com.hgo_soft.device_for_all.auth.services;

import com.hgo_soft.device_for_all.auth.entities.Role;
import com.hgo_soft.device_for_all.auth.repositories.RoleRepository;
import com.hgo_soft.device_for_all.auth.services.impl.RoleServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

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
}

