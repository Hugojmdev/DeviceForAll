package com.hgo_soft.device_for_all.service;

import com.hgo_soft.device_for_all.entity.Permission;
import com.hgo_soft.device_for_all.repository.PermissionRepository;
import com.hgo_soft.device_for_all.service.impl.PermissionServiceImpl;
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

public class PermissionServiceImplTest {
    private PermissionRepository repository;
    private PermissionServiceImpl service;

    @BeforeEach
    void setUp() {
        repository = mock(PermissionRepository.class);
        service = new PermissionServiceImpl(repository);
    }

    @Test
    void testFindAll_ShouldReturnPermissions() {
        List<Permission> permissions = Arrays.asList(Permission.builder().id(1L).build(), Permission.builder().id(2L).build());
        when(repository.findAll()).thenReturn(permissions);

        List<Permission> result = service.findAll();

        assertEquals(2, result.size());
        verify(repository).findAll();
    }

    @Test
    void testFindById_WhenFound() {
        Permission permission = Permission.builder().id(1L).build();
        when(repository.findById(1L)).thenReturn(Optional.of(permission));

        Optional<Permission> result = service.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
    }

    @Test
    void testFindById_WhenNotFound() {
        when(repository.findById(999L)).thenReturn(Optional.empty());

        Optional<Permission> result = service.findById(999L);

        assertFalse(result.isPresent());
    }

    @Test
    void testSave_ShouldReturnSavedPermission() {
        Permission toSave = new Permission();
        Permission saved = Permission.builder().id(10L).build();

        when(repository.save(ArgumentMatchers.any())).thenReturn(saved);

        Permission result = service.save(toSave);

        assertNotNull(result);
        assertEquals(10L, result.getId());
    }

    @Test
    void testUpdate_ShouldReturnUpdatedPermission() {
        Permission permission = Permission.builder().id(3L).build();
        when(repository.save(permission)).thenReturn(permission);

        Permission result = service.save(permission);

        assertEquals(3L, result.getId());
    }

    @Test
    void testDeleteById() {
        doNothing().when(repository).deleteById(5L);

        service.deleteById(5L);

        verify(repository, times(1)).deleteById(5L);
    }
}