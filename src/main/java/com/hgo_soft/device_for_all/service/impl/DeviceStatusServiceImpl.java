package com.hgo_soft.device_for_all.service.impl;

import com.hgo_soft.device_for_all.entity.DeviceStatus;
import com.hgo_soft.device_for_all.exception.ResourceNotFoundException;
import com.hgo_soft.device_for_all.repository.DeviceStatusRepository;
import com.hgo_soft.device_for_all.service.DeviceStatusService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DeviceStatusServiceImpl implements DeviceStatusService {

    private final DeviceStatusRepository repository;

    public DeviceStatusServiceImpl(DeviceStatusRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<DeviceStatus> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<DeviceStatus> findById(Long id) {
        return repository.findById(id)/*.orElse(new DeviceStatus());*//*.orElseThrow(() -> new ResourceNotFoundException("DeviceStatus not found with id: " + id))*/;
    }

    @Override
    public DeviceStatus save(DeviceStatus entity) {
        return repository.save(entity);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
