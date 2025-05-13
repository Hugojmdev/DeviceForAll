package com.hgo_soft.device_for_all.service;

import com.hgo_soft.device_for_all.entity.DeviceStatus;
import com.hgo_soft.device_for_all.repository.DeviceStatusRepository;
import com.hgo_soft.device_for_all.service.impl.DeviceStatusServiceImpl;
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

public class DeviceStatusServiceImplTest {
    private DeviceStatusRepository repository;
    private DeviceStatusServiceImpl service;

    @BeforeEach
    void setUp() {
        repository = mock(DeviceStatusRepository.class);
        service = new DeviceStatusServiceImpl(repository);
    }

    @Test
    void testFindAll_ShouldReturnDeviceStatuss() {
        List<DeviceStatus> deviceStatuses = Arrays.asList(DeviceStatus.builder().id(1L).build(), DeviceStatus.builder().id(2L).build());
        when(repository.findAll()).thenReturn(deviceStatuses);

        List<DeviceStatus> result = service.findAll();

        assertEquals(2, result.size());
        verify(repository).findAll();
    }

    @Test
    void testFindById_WhenFound() {
        DeviceStatus deviceStatus = DeviceStatus.builder().id(1L).build();
        when(repository.findById(1L)).thenReturn(Optional.of(deviceStatus));

        Optional<DeviceStatus> result = service.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
    }

    @Test
    void testFindById_WhenNotFound() {
        when(repository.findById(999L)).thenReturn(Optional.empty());

        Optional<DeviceStatus> result = service.findById(999L);

        assertFalse(result.isPresent());
    }

    @Test
    void testSave_ShouldReturnSavedDeviceStatus() {
        DeviceStatus toSave = new DeviceStatus();
        DeviceStatus saved = DeviceStatus.builder().id(10L).build();

        when(repository.save(ArgumentMatchers.any())).thenReturn(saved);

        DeviceStatus result = service.save(toSave);

        assertNotNull(result);
        assertEquals(10L, result.getId());
    }

    @Test
    void testUpdate_ShouldReturnUpdatedDeviceStatus() {
        DeviceStatus deviceStatus = DeviceStatus.builder().id(3L).build();
        when(repository.save(deviceStatus)).thenReturn(deviceStatus);

        DeviceStatus result = service.save(deviceStatus);

        assertEquals(3L, result.getId());
    }

    @Test
    void testDeleteById() {
        doNothing().when(repository).deleteById(5L);

        service.deleteById(5L);

        verify(repository, times(1)).deleteById(5L);
    }
}
