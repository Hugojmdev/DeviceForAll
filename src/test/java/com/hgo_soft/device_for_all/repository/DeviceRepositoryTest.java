package com.hgo_soft.device_for_all.repository;

import com.hgo_soft.device_for_all.entity.Device;
import com.hgo_soft.device_for_all.enums.DeviceStatus;
import com.hgo_soft.device_for_all.enums.DeviceType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
public class DeviceRepositoryTest extends RepositoryTestSetup {

    @Autowired
    DeviceRepository repository;

    @Test
    public void testSave(){
        Device newDevice = Device.builder().id(1L)
                .type(DeviceType.MEDIA_EQUIPMENT)
                .status(DeviceStatus.AVAILABLE)
                .build();
        Device savedDevice = repository.save(newDevice);
        assertNotNull(savedDevice.getId());
    }

    @Test
    void testSaveAndFindById() {
        Device device = Device.builder()
                .id(1L)
                .serialNumber("SN123")
                .model("X2023")
                .type(DeviceType.MOBILE)
                .status(DeviceStatus.AVAILABLE)
                .build();

        Device saved = repository.save(device);

        assertNotNull(saved.getId());

        Optional<Device> found = repository.findById(saved.getId());

        assertTrue(found.isPresent());
        assertEquals("SN123", found.get().getSerialNumber());
    }

    @Test
    void testFindByIdNotFound() {
        Optional<Device> result = repository.findById(999L);
        assertTrue(result.isEmpty());
    }

    @Test
    void testFindAll() {
        List<Device> allDevices = repository.findAll();
        assertEquals(2, allDevices.size());
    }

    @Test
    void testDeleteById() {
        repository.deleteById(1L);
        assertFalse(repository.findById(1L).isPresent());
    }

    @Test
    void testUpdateDevice() {
        Device device = Device.builder()
                .id(321L)
                .serialNumber("SN321")
                .model("M321")
                .type(DeviceType.MEDIA_EQUIPMENT)
                .status(DeviceStatus.AVAILABLE)
                .build();

        Device saved = repository.save(device);
        saved.setModel("m-124");
        Device updated = repository.save(saved);
        assertEquals("m-124", updated.getModel());
    }
    
}
