package com.hgo_soft.device_for_all.service.impl;

import com.hgo_soft.device_for_all.entity.Device;
import com.hgo_soft.device_for_all.exception.ResourceNotFoundException;
import com.hgo_soft.device_for_all.repository.DeviceRepository;
import com.hgo_soft.device_for_all.service.DeviceService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DeviceServiceImpl implements DeviceService {

    private final DeviceRepository repository;

    public DeviceServiceImpl(DeviceRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Device> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Device> findById(Long id) {
        return repository.findById(id)/*.orElse(new Device());*//*.orElseThrow(() -> new ResourceNotFoundException("Device not found with id: " + id))*/;
    }

    @Override
    public Device save(Device entity) {
        return repository.save(entity);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
