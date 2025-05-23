package com.hgo_soft.device_for_all.controller;
import com.hgo_soft.device_for_all.dto.DeviceDto;
import com.hgo_soft.device_for_all.entity.Device;
import com.hgo_soft.device_for_all.mapper.DeviceMapper;
import com.hgo_soft.device_for_all.service.DeviceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeviceControllerTest {

    private DeviceService service;
    private DeviceMapper mapper;
    private DeviceController controller;

    @BeforeEach
    void setUp() {
        service = mock(DeviceService.class);
        mapper = mock(DeviceMapper.class);
        controller = new DeviceController(service, mapper);
    }

    @Test
    void testGetAll_ShouldReturnListOfDevices() {
        List<Device> devices = Arrays.asList(
                Device.builder().id(1L).build(),
                Device.builder().id(2L).build()
        );
        List<DeviceDto> deviceDtos = Arrays.asList(
                DeviceDto.builder().id(1L).build(),
                DeviceDto.builder().id(2L).build()
        );
        when(mapper.toEntityList(anyList())).thenReturn(devices);
        when(mapper.toDtoList(anyList())).thenReturn(deviceDtos);

        when(service.findAll()).thenReturn(devices);

        ResponseEntity<List<DeviceDto>> response = controller.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        verify(service).findAll();
    }

    @Test
    void testGetAll_ShouldReturnNoContent() {
        List<DeviceDto> deviceDtos = new ArrayList<>();
        List<Device> devices = new ArrayList<>();
        when(mapper.toEntityList(anyList())).thenReturn(devices);
        when(mapper.toDtoList(anyList())).thenReturn(deviceDtos);
        when(service.findAll()).thenReturn(Collections.emptyList());

        ResponseEntity<List<DeviceDto>> response = controller.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testGetById_Found() {
        long id = 1L;
        DeviceDto inputDto = DeviceDto.builder().id(id).build();
        Device entityToFound = Device.builder().id(id).build();
        DeviceDto dtoToFound = DeviceDto.builder().id(id).build();
        when(mapper.toEntity(inputDto)).thenReturn(entityToFound);
        when(mapper.toDto(entityToFound)).thenReturn(dtoToFound);
        when(service.findById(any())).thenReturn(Optional.of(entityToFound));

        ResponseEntity<DeviceDto> response = controller.getById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(id, response.getBody().getId());
    }

    @Test
    void testGetById_NotFound() {
        when(service.findById(999L)).thenReturn(Optional.empty());

        ResponseEntity<DeviceDto> response = controller.getById(999L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testCreate_ShouldReturnCreatedDevice() {
        long id = 10L;
        DeviceDto inputDto = new DeviceDto();
        Device savedEntity = Device.builder().id(id).build();
        DeviceDto savedDto = DeviceDto.builder().id(id).build();
        // simulate mapper and persistence
        when(mapper.toEntity(inputDto)).thenReturn(savedEntity);
        when(mapper.toDto(savedEntity)).thenReturn(savedDto);
        when(service.save(any())).thenReturn(savedEntity);
        ResponseEntity<DeviceDto> response = controller.create(inputDto);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("/api/devices/10", Objects.requireNonNull(response.getHeaders().getLocation()).toString());
    }

    @Test
    void testUpdate_ShouldReturnUpdatedDevice() {
        Long id = 5L;
        DeviceDto inputDto = DeviceDto.builder().id(id).build();
        Device savedEntity = Device.builder().id(id).build();
        DeviceDto savedDto = DeviceDto.builder().id(id).build();
        when(mapper.toEntity(inputDto)).thenReturn(savedEntity);
        when(mapper.toDto(savedEntity)).thenReturn(savedDto);
        when(service.save(any())).thenReturn(savedEntity);
        ResponseEntity<DeviceDto> response = controller.update(id, inputDto);
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