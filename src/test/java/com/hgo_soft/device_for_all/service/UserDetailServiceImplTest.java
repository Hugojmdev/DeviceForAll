package com.hgo_soft.device_for_all.service;

import com.hgo_soft.device_for_all.entity.UserDetail;
import com.hgo_soft.device_for_all.repository.UserDetailRepository;
import com.hgo_soft.device_for_all.service.impl.UserDetailServiceImpl;
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

public class UserDetailServiceImplTest {
    private UserDetailRepository repository;
    private UserDetailServiceImpl service;

    @BeforeEach
    void setUp() {
        repository = mock(UserDetailRepository.class);
        service = new UserDetailServiceImpl(repository);
    }

    @Test
    void testFindAll_ShouldReturnUserDetails() {
        List<UserDetail> userDetails = Arrays.asList(UserDetail.builder().id(1L).build(), UserDetail.builder().id(2L).build());
        when(repository.findAll()).thenReturn(userDetails);

        List<UserDetail> result = service.findAll();

        assertEquals(2, result.size());
        verify(repository).findAll();
    }

    @Test
    void testFindById_WhenFound() {
        UserDetail userDetail = UserDetail.builder().id(1L).build();
        when(repository.findById(1L)).thenReturn(Optional.of(userDetail));

        Optional<UserDetail> result = service.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
    }

    @Test
    void testFindById_WhenNotFound() {
        when(repository.findById(999L)).thenReturn(Optional.empty());

        Optional<UserDetail> result = service.findById(999L);

        assertFalse(result.isPresent());
    }

    @Test
    void testSave_ShouldReturnSavedUserDetail() {
        UserDetail toSave = new UserDetail();
        UserDetail saved = UserDetail.builder().id(10L).build();

        when(repository.save(ArgumentMatchers.any())).thenReturn(saved);

        UserDetail result = service.save(toSave);

        assertNotNull(result);
        assertEquals(10L, result.getId());
    }

    @Test
    void testUpdate_ShouldReturnUpdatedUserDetail() {
        UserDetail userDetail = UserDetail.builder().id(3L).build();
        when(repository.save(userDetail)).thenReturn(userDetail);

        UserDetail result = service.save(userDetail);

        assertEquals(3L, result.getId());
    }

    @Test
    void testDeleteById() {
        doNothing().when(repository).deleteById(5L);

        service.deleteById(5L);

        verify(repository, times(1)).deleteById(5L);
    }
}