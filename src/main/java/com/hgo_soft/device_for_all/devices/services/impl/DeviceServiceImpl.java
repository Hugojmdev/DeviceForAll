package com.hgo_soft.device_for_all.devices.services.impl;

import com.hgo_soft.device_for_all.common.exceptions.DeviceConflictException;
import com.hgo_soft.device_for_all.common.exceptions.ResourceNotFoundException;
import com.hgo_soft.device_for_all.devices.dtos.CreateDeviceRequest;
import com.hgo_soft.device_for_all.devices.dtos.DeviceTypeRequest;
import com.hgo_soft.device_for_all.devices.dtos.RetireDeviceRequest;
import com.hgo_soft.device_for_all.devices.dtos.UpdateDeviceRequest;
import com.hgo_soft.device_for_all.devices.entities.Device;
import com.hgo_soft.device_for_all.devices.entities.DeviceType;
import com.hgo_soft.device_for_all.devices.enums.DeviceStatus;
import com.hgo_soft.device_for_all.devices.mappers.DeviceMapper;
import com.hgo_soft.device_for_all.devices.repositories.DeviceRepository;
import com.hgo_soft.device_for_all.devices.repositories.DeviceTypeRepository;
import com.hgo_soft.device_for_all.devices.services.DeviceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Set;

@Service
public class DeviceServiceImpl implements DeviceService {

    private final DeviceRepository repository;
    private final DeviceTypeRepository deviceTypeRepository;
    private final DeviceMapper mapper;

    public DeviceServiceImpl(
            DeviceRepository repository,
            DeviceTypeRepository deviceTypeRepository,
            DeviceMapper mapper) {
        this.repository = repository;
        this.deviceTypeRepository = deviceTypeRepository;
        this.mapper = mapper;
    }

    private Device save(Device device){
        return repository.save(device);
    }

    private DeviceType getDeviceTypeByRequest(DeviceTypeRequest request){
        if(request == null){
            return null;
        }
        DeviceType deviceType = null;
        if(request.id() != null){
            deviceType = deviceTypeRepository.findById(request.id()).orElse(null);
        }

        if(deviceType == null && request.name() != null && !request.name().isBlank()){
            deviceType = deviceTypeRepository.findByNameIgnoreCase(request.name().trim()).orElse(null);
        }

        if(deviceType != null && deviceType.getId() != null && !deviceType.getActive()){
            throw new DeviceConflictException("Device Type is Inactive: " + deviceType.getName());
        }
        return deviceType;
    }

    private DeviceType createDeviceType(DeviceTypeRequest deviceTypeRequest){
        if (deviceTypeRequest.name() == null || deviceTypeRequest.name().isBlank()) {
            throw new DeviceConflictException(
                    "Device type name is required when an ID is not provided"
            );
        }
        DeviceType deviceType = mapper.toDeviceTypeEntity(deviceTypeRequest);
        deviceType.setName(deviceTypeRequest.name().trim());
        return deviceTypeRepository.save(deviceType);
    }

    private DeviceType resolveDeviceType(DeviceTypeRequest deviceTypeRequest){
        if(deviceTypeRequest == null){
            throw new DeviceConflictException("Device Type is required");
        }
        DeviceType deviceType = getDeviceTypeByRequest(deviceTypeRequest);

        //Create device type if not exists
        if(deviceType == null){
            deviceType = createDeviceType(deviceTypeRequest);
        }

        return deviceType ;
    }

    @Override
    public List<Device> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Device findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Device not found -> " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<DeviceType> findAllActiveDeviceTypes(){
        return deviceTypeRepository.findAllByActive(true);
    }

    @Override
    @Transactional
    public Device create(CreateDeviceRequest request) {
        Device device = mapper.toEntity(request);
        device.setType(resolveDeviceType(request.type()));
        device.setStatus(DeviceStatus.MAINTENANCE);
        return repository.save(device);
    }

    @Override
    @Transactional
    public Device update(Long id, UpdateDeviceRequest request) {
        Device device = findById(id);
        mapper.updateEntity(request, device);
        if(request.type() != null){
            device.setType(resolveDeviceType(request.type()));
        }
        return save(device);
    }

    @Override
    @Transactional
    public Device retire(Long id, RetireDeviceRequest request) {
        Device device = findById(id);
        if (device.getStatus() == DeviceStatus.RETIRED) {
            throw new DeviceConflictException("Device with id= " + id + " is already retired");
        }
        Set<DeviceStatus> preventiveStatus = Set.of(DeviceStatus.ON_LOAN, DeviceStatus.RESERVED, DeviceStatus.LOST);
        if (preventiveStatus.contains(device.getStatus())) {
            throw new DeviceConflictException("Unable to retire a device ON LOAN, RESERVED or LOST");
        }
        device.setStatus(DeviceStatus.RETIRED);
        device.setRetiredAt(Instant.now());
        mapper.retireDevice(request, device);
        return save(device);
    }

}
