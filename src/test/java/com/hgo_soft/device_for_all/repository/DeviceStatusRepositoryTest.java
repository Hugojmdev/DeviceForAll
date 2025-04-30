package com.hgo_soft.device_for_all.repository;

import com.hgo_soft.device_for_all.entity.DeviceStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
public class DeviceStatusRepositoryTest {

    @Autowired
    private DeviceStatusRepository repository;

    @Test
    void testSave() {
        DeviceStatus deviceStatus = DeviceStatus.builder().statusName("Available").build();
        DeviceStatus saved = repository.save(deviceStatus);
        assertNotNull(saved.getId());
    }

    @Test
    void testFindById() {
        DeviceStatus deviceStatus = DeviceStatus.builder().statusName("Available").build();
        DeviceStatus saved = repository.save(deviceStatus);
        Optional<DeviceStatus> result = repository.findById(saved.getId());
        assertTrue(result.isPresent());
    }

    @Test
    void testFindAll() {
        repository.save(DeviceStatus.builder().statusName("Available").build());
        repository.save(DeviceStatus.builder().statusName("Unavailable").build());
        List<DeviceStatus> list = repository.findAll();
        assertTrue(list.size() >= 2);
    }

    @Test
    void testDeleteById() {
        DeviceStatus deviceStatus = DeviceStatus.builder().statusName("Available").build();
        DeviceStatus saved = repository.save(deviceStatus);
        repository.deleteById(saved.getId());
        assertFalse(repository.findById(saved.getId()).isPresent());
    }
}
