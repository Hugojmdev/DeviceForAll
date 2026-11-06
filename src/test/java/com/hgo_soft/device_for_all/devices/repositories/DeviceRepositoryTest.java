package com.hgo_soft.device_for_all.devices.repositories;

import com.hgo_soft.device_for_all.common.audit.JpaAuditingConfig;
import com.hgo_soft.device_for_all.common.audit.JpaAuditingTestConfig;
import com.hgo_soft.device_for_all.devices.entities.Device;
import com.hgo_soft.device_for_all.devices.entities.DeviceType;
import com.hgo_soft.device_for_all.devices.enums.DeviceCondition;
import com.hgo_soft.device_for_all.devices.enums.DeviceStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@Import({
        JpaAuditingConfig.class,
        JpaAuditingTestConfig.class
})
public class DeviceRepositoryTest {

    @Autowired
    DeviceRepository repository;
    @Autowired
    DeviceTypeRepository typeRepository;

    @Test
    public void testSave(){
        Device device = buildDevice("00-11-22");
        typeRepository.save(device.getType());
        repository.save(device);
        assertNotNull(device.getId());
    }

    @Test
    void testSaveAndFindById() {
        Device device = buildDevice("00-11-33");
        typeRepository.save(device.getType());
        repository.save(device);
        assertNotNull(device.getId());
        Long deviceId = device.getId();
        Optional<Device> found = repository.findById(deviceId);
        assertTrue(found.isPresent());
    }

    @Test
    void testFindByIdNotFound() {
        Device device = buildDevice("00-22-22");
        typeRepository.save(device.getType());
        repository.save(device);
        assertNotNull(device.getId());
        Optional<Device> result = repository.findById(999L);
        assertTrue(result.isEmpty());
    }

    @Test
    void testFindAllDeviceTypesByActive() {
        DeviceType activeType = DeviceType.builder()
                .name("Laptop")
                .active(true)
                .build();
        DeviceType inactiveType = DeviceType.builder()
                .name("Legacy device type")
                .active(false)
                .build();
        typeRepository.save(activeType);
        typeRepository.save(inactiveType);

        List<DeviceType> activeTypes = typeRepository.findAllByActive(true);
        List<DeviceType> inactiveTypes = typeRepository.findAllByActive(false);

        assertEquals(1, activeTypes.size());
        assertEquals("Laptop", activeTypes.getFirst().getName());
        assertEquals(1, inactiveTypes.size());
        assertEquals("Legacy device type", inactiveTypes.getFirst().getName());
    }

    @Test
    void testFindDeviceTypeByNameIgnoreCase() {
        DeviceType deviceType = DeviceType.builder()
                .name("Projector")
                .build();
        typeRepository.save(deviceType);

        Optional<DeviceType> result = typeRepository.findByNameIgnoreCase("projector");

        assertTrue(result.isPresent());
        assertEquals(deviceType.getId(), result.get().getId());
    }

    private Device buildDevice(String serialNumber) {
        return Device.builder()
                .type(DeviceType.builder().name("test-type").build())
                .name("test-device")
                .serialNumber(serialNumber)
                .model("X2023")
                .make("test-make")
                .description("testing")
                .status(DeviceStatus.AVAILABLE)
                .physicalCondition(DeviceCondition.GOOD)
                .build();
    }
}
