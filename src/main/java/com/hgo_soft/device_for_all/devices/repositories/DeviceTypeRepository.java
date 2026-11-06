package com.hgo_soft.device_for_all.devices.repositories;

import com.hgo_soft.device_for_all.devices.entities.DeviceType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DeviceTypeRepository extends JpaRepository<DeviceType, Long> {

    List<DeviceType> findAllByActive(Boolean active);
    Optional<DeviceType> findByNameIgnoreCase(String name);
}
