package com.hgo_soft.device_for_all.devices.services;
import com.hgo_soft.device_for_all.common.exceptions.DeviceConflictException;
import com.hgo_soft.device_for_all.common.exceptions.ResourceNotFoundException;
import com.hgo_soft.device_for_all.devices.dtos.CreateDeviceRequest;
import com.hgo_soft.device_for_all.devices.dtos.DeviceTypeRequest;
import com.hgo_soft.device_for_all.devices.dtos.RetireDeviceRequest;
import com.hgo_soft.device_for_all.devices.dtos.UpdateDeviceRequest;
import com.hgo_soft.device_for_all.devices.entities.Device;
import com.hgo_soft.device_for_all.devices.entities.DeviceType;
import com.hgo_soft.device_for_all.devices.enums.DeviceCondition;
import com.hgo_soft.device_for_all.devices.enums.DeviceStatus;
import com.hgo_soft.device_for_all.devices.mappers.DeviceMapper;
import com.hgo_soft.device_for_all.devices.repositories.DeviceRepository;
import com.hgo_soft.device_for_all.devices.repositories.DeviceTypeRepository;
import com.hgo_soft.device_for_all.devices.services.impl.DeviceServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeviceServiceImplTest {

    private DeviceRepository repository;
    private DeviceTypeRepository deviceTypeRepository;
    private DeviceServiceImpl service;
    private DeviceMapper mapper;

    @BeforeEach
    void setUp() {
        repository = mock(DeviceRepository.class);
        deviceTypeRepository = mock(DeviceTypeRepository.class);
        mapper = mock(DeviceMapper.class);
        service = new DeviceServiceImpl(repository, deviceTypeRepository, mapper);
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

        Device result = service.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void testFindById_WhenNotFoundThrowException(){
        Long deviceId = 999L;
        when(repository.findById(deviceId)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> service.findById(deviceId));
        assertEquals("Device not found -> " + deviceId, exception.getMessage());
        verify(repository).findById(deviceId);
    }

    @Test
    void testFindAllActiveDeviceTypes_ShouldReturnActiveDeviceTypes() {
        List<DeviceType> deviceTypes = List.of(
                DeviceType.builder().id(1L).name("Laptop").build(),
                DeviceType.builder().id(2L).name("Monitor").build()
        );
        when(deviceTypeRepository.findAllByActive(true)).thenReturn(deviceTypes);

        List<DeviceType> result = service.findAllActiveDeviceTypes();

        assertEquals(2, result.size());
        verify(deviceTypeRepository).findAllByActive(true);
    }

    @Test
    void testCreate_shouldReturnSavedDeviceWhenDeviceTypeExists() {
        DeviceTypeRequest deviceTypeRequest =DeviceTypeRequest.builder()
                .id(1L)
                .name("test-type")
                .description("test")
                .build();
        CreateDeviceRequest request = CreateDeviceRequest.builder()
                .type(deviceTypeRequest)
                .name("test-device")
                .description("test")
                .serialNumber("000-111-222")
                .model("Legion-y180")
                .make("Lenovo")
                .physicalCondition(DeviceCondition.FAIR)
                .acquiredOn(LocalDate.now())
                .warrantyExpiresOn(LocalDate.now())
                .build();
        DeviceType deviceType = DeviceType.builder()
                .id(1L)
                .name("test-type")
                .build();
        Device device = Device.builder()
                .id(1L)
                .name("test-device")
                .build();

        when(repository.save(ArgumentMatchers.any())).thenReturn(device);
        when(deviceTypeRepository.findById(1L)).thenReturn(Optional.of(deviceType));
        when(mapper.toEntity(request)).thenReturn(device);

        Device result = service.create(request);

        assertNotNull(result);
        assertEquals(device.getId(), result.getId());
        assertSame(deviceType, result.getType());
        assertEquals(DeviceStatus.MAINTENANCE, result.getStatus());
        verify(deviceTypeRepository).findById(1L);
        verify(deviceTypeRepository, never()).save(any(DeviceType.class));
        verify(mapper, never()).toDeviceTypeEntity(any(DeviceTypeRequest.class));
    }

    @Test
    void testCreate_shouldUseDeviceTypeNameWhenIdDoesNotExist() {
        DeviceTypeRequest deviceTypeRequest = DeviceTypeRequest.builder()
                .id(999L)
                .name("Laptop")
                .build();
        CreateDeviceRequest request = CreateDeviceRequest.builder()
                .type(deviceTypeRequest)
                .name("test-device")
                .serialNumber("000-111-333")
                .model("Legion-y180")
                .make("Lenovo")
                .physicalCondition(DeviceCondition.GOOD)
                .build();
        DeviceType deviceType = DeviceType.builder()
                .id(2L)
                .name("Laptop")
                .build();
        Device device = Device.builder().id(2L).build();

        when(mapper.toEntity(request)).thenReturn(device);
        when(deviceTypeRepository.findById(999L)).thenReturn(Optional.empty());
        when(deviceTypeRepository.findByNameIgnoreCase("Laptop")).thenReturn(Optional.of(deviceType));
        when(repository.save(device)).thenReturn(device);

        Device result = service.create(request);

        assertSame(deviceType, result.getType());
        assertEquals(DeviceStatus.MAINTENANCE, result.getStatus());
        verify(deviceTypeRepository).findById(999L);
        verify(deviceTypeRepository).findByNameIgnoreCase("Laptop");
        verify(deviceTypeRepository, never()).save(any(DeviceType.class));
    }

    @Test
    void testCreate_shouldCreateDeviceTypeWhenItDoesNotExist() {
        DeviceTypeRequest deviceTypeRequest = DeviceTypeRequest.builder()
                .name("  Projector  ")
                .description("Video projector")
                .build();
        CreateDeviceRequest request = CreateDeviceRequest.builder()
                .type(deviceTypeRequest)
                .name("test-device")
                .serialNumber("000-111-444")
                .model("PX-1")
                .make("Test")
                .physicalCondition(DeviceCondition.NEW)
                .build();
        DeviceType newDeviceType = DeviceType.builder()
                .name("  Projector  ")
                .description("Video projector")
                .build();
        DeviceType savedDeviceType = DeviceType.builder()
                .id(5L)
                .name("Projector")
                .description("Video projector")
                .build();
        Device device = Device.builder().id(3L).build();

        when(mapper.toEntity(request)).thenReturn(device);
        when(deviceTypeRepository.findByNameIgnoreCase("Projector")).thenReturn(Optional.empty());
        when(mapper.toDeviceTypeEntity(deviceTypeRequest)).thenReturn(newDeviceType);
        when(deviceTypeRepository.save(newDeviceType)).thenReturn(savedDeviceType);
        when(repository.save(device)).thenReturn(device);

        Device result = service.create(request);

        assertSame(savedDeviceType, result.getType());
        assertEquals("Projector", newDeviceType.getName());
        assertEquals(DeviceStatus.MAINTENANCE, result.getStatus());
        verify(deviceTypeRepository).save(newDeviceType);
    }

    @Test
    void testCreate_WhenDeviceTypeIsInactive_ShouldThrowException() {
        DeviceTypeRequest deviceTypeRequest = DeviceTypeRequest.builder()
                .id(4L)
                .build();
        CreateDeviceRequest request = CreateDeviceRequest.builder()
                .type(deviceTypeRequest)
                .name("test-device")
                .serialNumber("000-111-555")
                .model("X1")
                .make("Test")
                .physicalCondition(DeviceCondition.GOOD)
                .build();
        DeviceType inactiveDeviceType = DeviceType.builder()
                .id(4L)
                .name("Inactive type")
                .active(false)
                .build();
        Device device = Device.builder().build();

        when(mapper.toEntity(request)).thenReturn(device);
        when(deviceTypeRepository.findById(4L)).thenReturn(Optional.of(inactiveDeviceType));

        assertThrows(DeviceConflictException.class, () -> service.create(request));

        verify(repository, never()).save(any(Device.class));
        verify(deviceTypeRepository, never()).save(any(DeviceType.class));
    }

    @Test
    void testUpdate_shouldReturnUpdatedDevice() {
        Long deviceId = 3L;
        UpdateDeviceRequest request = UpdateDeviceRequest.builder()
                .name("updated-device")
                .description("updated-device")
                .physicalCondition(DeviceCondition.FAIR)
                .build();
        Device device = Device.builder().id(deviceId).build();
        when(repository.findById(deviceId)).thenReturn(Optional.of(device));
        when(repository.save(device)).thenReturn(device);
        Device result = service.update(deviceId, request);

        assertEquals(3L, result.getId());
        verify(mapper).updateEntity(request, device);
        verifyNoInteractions(deviceTypeRepository);
        verify(repository).save(device);
    }

    @Test
    void testUpdate_shouldChangeDeviceTypeWhenProvided() {
        Long deviceId = 3L;
        DeviceTypeRequest typeRequest = DeviceTypeRequest.builder().id(2L).build();
        UpdateDeviceRequest request = UpdateDeviceRequest.builder()
                .type(typeRequest)
                .build();
        DeviceType currentType = DeviceType.builder().id(1L).name("Laptop").build();
        DeviceType updatedType = DeviceType.builder().id(2L).name("Monitor").build();
        Device device = Device.builder().id(deviceId).type(currentType).build();
        when(repository.findById(deviceId)).thenReturn(Optional.of(device));
        when(deviceTypeRepository.findById(2L)).thenReturn(Optional.of(updatedType));
        when(repository.save(device)).thenReturn(device);

        Device result = service.update(deviceId, request);

        assertSame(updatedType, result.getType());
        verify(mapper).updateEntity(request, device);
        verify(deviceTypeRepository).findById(2L);
        verify(repository).save(device);
    }

    @Test
    void testRetire_shouldRetireDevice() {
        Long deviceId = 5L;
        RetireDeviceRequest request = RetireDeviceRequest.builder()
                .retirementReason("Broken")
                .build();
        Device device = Device.builder()
                .id(deviceId)
                .status(DeviceStatus.AVAILABLE)
                .build();
        when(repository.findById(deviceId)).thenReturn(Optional.of(device));
        when(repository.save(device)).thenReturn(device);

        Device result = service.retire(deviceId, request);

        assertEquals(DeviceStatus.RETIRED, result.getStatus());
        assertNotNull(result.getRetiredAt());
        verify(mapper).retireDevice(request, device);
        verify(repository).save(device);
    }

    @Test
    void testRetire_WhenDeviceIsAlreadyRetired_ShouldThrowException() {
        Long deviceId = 5L;
        RetireDeviceRequest request = RetireDeviceRequest.builder().retirementReason("Broken").build();
        Device device = Device.builder().id(deviceId).status(DeviceStatus.RETIRED).build();
        when(repository.findById(deviceId)).thenReturn(Optional.of(device));

        assertThrows(DeviceConflictException.class, () -> service.retire(deviceId, request));

        verify(repository, never()).save(any(Device.class));
        verify(mapper, never()).retireDevice(any(), any());
    }

    @Test
    void testRetire_WhenDeviceIsOnLoan_ShouldThrowException() {
        Long deviceId = 5L;
        RetireDeviceRequest request = RetireDeviceRequest.builder().retirementReason("Broken").build();
        Device device = Device.builder().id(deviceId).status(DeviceStatus.ON_LOAN).build();
        when(repository.findById(deviceId)).thenReturn(Optional.of(device));

        assertThrows(DeviceConflictException.class, () -> service.retire(deviceId, request));

        verify(repository, never()).save(any(Device.class));
    }

    @Test
    void testRetire_WhenDeviceIsReserved_ShouldThrowException() {
        Long deviceId = 5L;
        RetireDeviceRequest request = RetireDeviceRequest.builder().retirementReason("Broken").build();
        Device device = Device.builder().id(deviceId).status(DeviceStatus.RESERVED).build();
        when(repository.findById(deviceId)).thenReturn(Optional.of(device));

        assertThrows(DeviceConflictException.class, () -> service.retire(deviceId, request));

        verify(repository, never()).save(any(Device.class));
    }

    @Test
    void testRetire_WhenDeviceIsLost_ShouldThrowException() {
        Long deviceId = 5L;
        RetireDeviceRequest request = RetireDeviceRequest.builder().retirementReason("Broken").build();
        Device device = Device.builder().id(deviceId).status(DeviceStatus.LOST).build();
        when(repository.findById(deviceId)).thenReturn(Optional.of(device));

        assertThrows(DeviceConflictException.class, () -> service.retire(deviceId, request));

        verify(repository, never()).save(any(Device.class));
    }
}
