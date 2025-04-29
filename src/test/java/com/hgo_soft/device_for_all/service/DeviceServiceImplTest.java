package com.hgo_soft.device_for_all.service;
import com.hgo_soft.device_for_all.entity.Device;
import com.hgo_soft.device_for_all.repository.DeviceRepository;
import com.hgo_soft.device_for_all.service.impl.DeviceServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeviceServiceImplTest {

    private DeviceRepository repository;
    private DeviceServiceImpl service;

    @BeforeEach
    void setUp() {
        repository = mock(DeviceRepository.class);
        service = new DeviceServiceImpl(repository);
    }

    @Test
    void testFindAll_ShouldReturnDevices() {
        List<Device> devices = Arrays.asList(Device.builder().id(1L).build(), Device.builder().id(2L).build());
        when(repository.findAll()).thenReturn(devices);

        List<Device> result = service.findAll();

        assertEquals(2, result.size());
        verify(repository).findAll();
    }

    @Test
    void testFindById_WhenFound() {
        Device device = Device.builder().id(1L).build();
        when(repository.findById(1L)).thenReturn(Optional.of(device));

        Optional<Device> result = service.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
    }

    @Test
    void testFindById_WhenNotFound() {
        when(repository.findById(999L)).thenReturn(Optional.empty());

        Optional<Device> result = service.findById(999L);

        assertFalse(result.isPresent());
    }

    @Test
    void testSave_ShouldReturnSavedDevice() {
        Device toSave = new Device();
        Device saved = Device.builder().id(10L).build();

        when(repository.save(ArgumentMatchers.any())).thenReturn(saved);

        Device result = service.save(toSave);

        assertNotNull(result);
        assertEquals(10L, result.getId());
    }

    @Test
    void testUpdate_ShouldReturnUpdatedDevice() {
        Device device = Device.builder().id(3L).build();
        when(repository.save(device)).thenReturn(device);

        Device result = service.save(device);

        assertEquals(3L, result.getId());
    }

    @Test
    void testDeleteById() {
        doNothing().when(repository).deleteById(5L);

        service.deleteById(5L);

        verify(repository, times(1)).deleteById(5L);
    }
}