package com.hgo_soft.device_for_all.repository;

import com.hgo_soft.device_for_all.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {
}
