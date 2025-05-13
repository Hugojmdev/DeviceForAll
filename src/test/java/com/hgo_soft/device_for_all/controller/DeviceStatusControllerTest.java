package com.hgo_soft.device_for_all.controller;

import com.hgo_soft.device_for_all.dto.DeviceStatusDto;
import com.hgo_soft.device_for_all.entity.DeviceStatus;
import com.hgo_soft.device_for_all.mapper.DeviceStatusMapper;
import com.hgo_soft.device_for_all.service.DeviceStatusService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeviceStatusControllerTest {

    private DeviceStatusService service;
    private DeviceStatusMapper mapper;
    private DeviceStatusController controller;

    @BeforeEach
    void setUp() {
        service = mock(DeviceStatusService.class);
        mapper = mock(DeviceStatusMapper.class);
        controller = new DeviceStatusController(service, mapper);
    }

    @Test
    void testGetAll_ShouldReturnListOfDeviceStatuss() {
        List<DeviceStatus> deviceStatuss = Arrays.asList(
                DeviceStatus.builder().id(1L).build(),
                DeviceStatus.builder().id(2L).build()
        );
        List<DeviceStatusDto> deviceStatusDtos = Arrays.asList(
                DeviceStatusDto.builder().id(1L).build(),
                DeviceStatusDto.builder().id(2L).build()
        );
        when(mapper.toEntityList(anyList())).thenReturn(deviceStatuss);
        when(mapper.toDtoList(anyList())).thenReturn(deviceStatusDtos);

        when(service.findAll()).thenReturn(deviceStatuss);

        ResponseEntity<List<DeviceStatusDto>> response = controller.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        verify(service).findAll();
    }

    @Test
    void testGetAll_ShouldReturnNoContent() {
        List<DeviceStatusDto> deviceStatusDtos = new ArrayList<>();
        List<DeviceStatus> deviceStatuss = new ArrayList<>();
        when(mapper.toEntityList(anyList())).thenReturn(deviceStatuss);
        when(mapper.toDtoList(anyList())).thenReturn(deviceStatusDtos);
        when(service.findAll()).thenReturn(Collections.emptyList());

        ResponseEntity<List<DeviceStatusDto>> response = controller.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testGetById_Found() {
        long id = 1L;
        DeviceStatusDto inputDto = DeviceStatusDto.builder().id(id).build();
        DeviceStatus entityToFound = DeviceStatus.builder().id(id).build();
        DeviceStatusDto dtoToFound = DeviceStatusDto.builder().id(id).build();
        when(mapper.toEntity(inputDto)).thenReturn(entityToFound);
        when(mapper.toDto(entityToFound)).thenReturn(dtoToFound);
        when(service.findById(any())).thenReturn(Optional.of(entityToFound));

        ResponseEntity<DeviceStatusDto> response = controller.getById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(id, response.getBody().getId());
    }

    @Test
    void testGetById_NotFound() {
        when(service.findById(999L)).thenReturn(Optional.empty());

        ResponseEntity<DeviceStatusDto> response = controller.getById(999L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testCreate_ShouldReturnCreatedDeviceStatus() {
        long id = 10L;
        DeviceStatusDto inputDto = new DeviceStatusDto();
        DeviceStatus savedEntity = DeviceStatus.builder().id(id).build();
        DeviceStatusDto savedDto = DeviceStatusDto.builder().id(id).build();
        // simulate mapper and persistence
        when(mapper.toEntity(inputDto)).thenReturn(savedEntity);
        when(mapper.toDto(savedEntity)).thenReturn(savedDto);
        when(service.save(any())).thenReturn(savedEntity);
        ResponseEntity<DeviceStatusDto> response = controller.create(inputDto);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("/api/deviceStatus/10", Objects.requireNonNull(response.getHeaders().getLocation()).toString());
    }

    @Test
    void testUpdate_ShouldReturnUpdatedDeviceStatus() {
        Long id = 5L;
        DeviceStatusDto inputDto = DeviceStatusDto.builder().id(id).build();
        DeviceStatus savedEntity = DeviceStatus.builder().id(id).build();
        DeviceStatusDto savedDto = DeviceStatusDto.builder().id(id).build();
        when(mapper.toEntity(inputDto)).thenReturn(savedEntity);
        when(mapper.toDto(savedEntity)).thenReturn(savedDto);
        when(service.save(any())).thenReturn(savedEntity);
        ResponseEntity<DeviceStatusDto> response = controller.update(id, inputDto);
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