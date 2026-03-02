package com.hgo_soft.device_for_all.controller;
import com.hgo_soft.device_for_all.dto.DeviceDto;
import com.hgo_soft.device_for_all.entity.Device;
import com.hgo_soft.device_for_all.mapper.DeviceMapper;
import com.hgo_soft.device_for_all.mapper.DeviceMapperImpl;
import com.hgo_soft.device_for_all.service.DeviceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeviceControllerTest {

    //@MockBean
    private DeviceService service;
    private DeviceController controller;

    @BeforeEach
    void setUp() {
        service = mock(DeviceService.class);
        //@Autowired
        DeviceMapper mapper = new DeviceMapperImpl();
        controller = new DeviceController(service, mapper);
    }

    @Test
    void testGetAll_ShouldReturnListOfDevices() {
        List<Device> devices = Arrays.asList(
                Device.builder().id(1L).build(),
                Device.builder().id(2L).build()
        );

        when(service.findAll()).thenReturn(devices);

        ResponseEntity<List<DeviceDto>> response = controller.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        verify(service).findAll();
    }

    @Test
    void testGetAll_ShouldReturnNoContent() {
        when(service.findAll()).thenReturn(Collections.emptyList());

        ResponseEntity<List<DeviceDto>> response = controller.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isEmpty());
    }

    @Test
    void testGetById_Found() {
        long id = 1L;
        Device entityToFound = Device.builder().id(id).build();
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
        DeviceDto inputDto = DeviceDto.builder().id(id).build();
        Device savedEntity = Device.builder().id(id).build();
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