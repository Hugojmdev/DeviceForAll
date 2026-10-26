package com.hgo_soft.device_for_all.devices.repositories;

import com.hgo_soft.device_for_all.devices.entities.Device;
import com.hgo_soft.device_for_all.devices.entities.DeviceType;
import com.hgo_soft.device_for_all.devices.enums.DeviceStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
public class DeviceRepositoryTest {

    @Autowired
    DeviceRepository repository;

    @Test
    public void testSave(){
        Device device = buildDevice("00-11-22");
        repository.save(device);
        assertNotNull(device.getId());
    }

    @Test
    void testSaveAndFindById() {
        Device device = buildDevice("00-11-33");
        repository.save(device);
        assertNotNull(device.getId());
        Long deviceId = device.getId();
        Optional<Device> found = repository.findById(deviceId);
        assertTrue(found.isPresent());
    }

    @Test
    void testFindByIdNotFound() {
        Device device = buildDevice("00-22-22");
        repository.save(device);
        assertNotNull(device.getId());
        Optional<Device> result = repository.findById(999L);
        assertTrue(result.isEmpty());
    }

    /*@Test
    void testFindAll() {
        Device device = buildDevice("00-11-22");
        repository.save(device);
        Device device2 = buildDevice("00-11-33");
        repository.save(device2);
        List<Device> allDevices = repository.findAll();
        assertEquals(2, allDevices.size());
    }*/

    @Test
    void testDeleteById() {
        Device device = buildDevice("00-11-22");
        repository.save(device);
        Long deviceId = device.getId();
        repository.deleteById(deviceId);
        assertFalse(repository.findById(deviceId).isPresent());
    }

    /*@Test
    void testUpdateDevice() {
        Device device = Device.builder()
                .id(321L)
                .name("saved-device")
                .serialNumber("SN321")
                .model("M321")
                .deviceType(DeviceType.builder().build())
                .status(DeviceStatus.AVAILABLE)
                .build();

        Device saved = repository.save(device);
        saved.setModel("m-124");
        Device updated = repository.save(saved);
        assertEquals("m-124", updated.getModel());
    }*/

    private Device buildDevice(String serialNumber) {
        return Device.builder()
                .name("test-device")
                .serialNumber(serialNumber)
                .model("X2023")
                .deviceType(DeviceType.builder().build())
                .status(DeviceStatus.AVAILABLE)
                .build();
    }
}
