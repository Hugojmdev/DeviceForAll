package com.hgo_soft.device_for_all.devices.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hgo_soft.device_for_all.auth.filters.JwtAuthenticationFilter;
import com.hgo_soft.device_for_all.common.exceptions.DeviceConflictException;
import com.hgo_soft.device_for_all.common.exceptions.ResourceNotFoundException;
import com.hgo_soft.device_for_all.common.security.WebMvcSecurityTestConfiguration;
import com.hgo_soft.device_for_all.devices.dtos.CreateDeviceRequest;
import com.hgo_soft.device_for_all.devices.dtos.DeviceTypeRequest;
import com.hgo_soft.device_for_all.devices.dtos.RetireDeviceRequest;
import com.hgo_soft.device_for_all.devices.dtos.UpdateDeviceRequest;
import com.hgo_soft.device_for_all.devices.entities.Device;
import com.hgo_soft.device_for_all.devices.entities.DeviceType;
import com.hgo_soft.device_for_all.devices.enums.DeviceCondition;
import com.hgo_soft.device_for_all.devices.mappers.DeviceMapperImpl;
import com.hgo_soft.device_for_all.devices.services.DeviceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
        controllers = DeviceController.class,
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                classes = JwtAuthenticationFilter.class
        )
)
@Import({WebMvcSecurityTestConfiguration.class, DeviceMapperImpl.class})
class DeviceControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private DeviceService service;

    @Test
    @WithMockUser(authorities = "DEVICE_READ")
    void getAll_returnsDevices() throws Exception {
        when(service.findAll()).thenReturn(List.of(
                Device.builder().id(1L).build(),
                Device.builder().id(2L).build()
        ));

        mockMvc.perform(get("/api/devices"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2));

        verify(service).findAll();
    }

    @Test
    @WithMockUser(authorities = "DEVICE_READ")
    void getAll_whenEmpty_returnsEmptyArray() throws Exception {
        when(service.findAll()).thenReturn(List.of());

        mockMvc.perform(get("/api/devices"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    @WithMockUser(authorities = "DEVICE_READ")
    void getById_whenFound_returnsDevice() throws Exception {
        when(service.findById(1L)).thenReturn(Device.builder().id(1L).build());

        mockMvc.perform(get("/api/devices/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @WithMockUser(authorities = "DEVICE_READ")
    void getById_whenMissing_returnsNotFound() throws Exception {
        Long deviceId = 999L;
        when(service.findById(deviceId)).thenThrow(new ResourceNotFoundException("Device not found -> " + deviceId));

        mockMvc.perform(get("/api/devices/{id}", deviceId))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(authorities = "DEVICE_READ")
    void getActiveDeviceTypes_returnsDeviceTypes() throws Exception {
        when(service.findAllActiveDeviceTypes()).thenReturn(List.of(
                DeviceType.builder().id(1L).name("Laptop").build(),
                DeviceType.builder().id(2L).name("Monitor").build()
        ));

        mockMvc.perform(get("/api/devices/device-types"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Laptop"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Monitor"));

        verify(service).findAllActiveDeviceTypes();
    }

    @Test
    @WithMockUser(authorities = "DEVICE_CREATE")
    void create_returnsCreatedDeviceWhenDeviceTypeDoesNotExist() throws Exception {
        CreateDeviceRequest request = CreateDeviceRequest.builder()
                .type(DeviceTypeRequest.builder().name("test-type").build())
                .name("test-device")
                .serialNumber("000-000100-22SS")
                .model("Legion X3")
                .make("Lenovo")
                .physicalCondition(DeviceCondition.GOOD)
                .build();
        Device device = Device.builder().id(1L).build();
        when(service.create(any(CreateDeviceRequest.class))).thenReturn(device);

        mockMvc.perform(post("/api/devices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(header().string(HttpHeaders.LOCATION, "/api/devices/1"))
                .andExpect(jsonPath("$.id").value(1));

        verify(service).create(any(CreateDeviceRequest.class));
    }

    @Test
    @WithMockUser(authorities = "DEVICE_CREATE")
    void create_returnsCreatedDeviceWhenDeviceTypeExists() throws Exception {
        CreateDeviceRequest request = CreateDeviceRequest.builder()
                .type(DeviceTypeRequest.builder().id(3L).build())
                .name("test-device")
                .serialNumber("000-000100-22SS")
                .model("Legion X3")
                .make("Lenovo")
                .physicalCondition(DeviceCondition.GOOD)
                .build();
        DeviceType deviceType = DeviceType.builder().id(3L).build();
        Device device = Device.builder().id(1L).type(deviceType).build();
        when(service.create(any(CreateDeviceRequest.class))).thenReturn(device);

        mockMvc.perform(post("/api/devices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(header().string(HttpHeaders.LOCATION, "/api/devices/1"))
                .andExpect(jsonPath("$.id").value(1));

        verify(service).create(any(CreateDeviceRequest.class));
    }

    @Test
    @WithMockUser(authorities = "DEVICE_UPDATE")
    void update_returnsUpdatedDevice() throws Exception {
        UpdateDeviceRequest request = UpdateDeviceRequest.builder()
                .name("test-new-device-name")
                .build();
        DeviceType deviceType = DeviceType.builder().id(3L).build();
        Device device = Device.builder().id(5L).type(deviceType).build();
        when(service.update(anyLong(), any(UpdateDeviceRequest.class))).thenReturn(device);
        mockMvc.perform(patch("/api/devices/{id}", 5L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(5));

        verify(service).update(anyLong(), any(UpdateDeviceRequest.class));
    }

    @Test
    @WithMockUser(authorities = "DEVICE_RETIRE")
    void retire_returnsOk() throws Exception {
        RetireDeviceRequest request = RetireDeviceRequest.builder().retirementReason("Broken").build();
        Device device = Device.builder().id(5L).build();
        when(service.retire(anyLong(), any(RetireDeviceRequest.class))).thenReturn(device);
        mockMvc.perform(patch("/api/devices/retire/{id}", 5L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(5L));

        verify(service).retire(anyLong(), any(RetireDeviceRequest.class));
    }

    @Test
    @WithMockUser(authorities = "DEVICE_CREATE")
    void create_withoutDeviceType_returnsBadRequest() throws Exception {
        CreateDeviceRequest request = CreateDeviceRequest.builder()
                .name("test-device")
                .serialNumber("000-000100-22SS")
                .model("Legion X3")
                .make("Lenovo")
                .physicalCondition(DeviceCondition.GOOD)
                .build();

        mockMvc.perform(post("/api/devices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(service);
    }

    @Test
    @WithMockUser(authorities = "DEVICE_RETIRE")
    void retire_withoutReason_returnsBadRequest() throws Exception {
        RetireDeviceRequest request = RetireDeviceRequest.builder()
                .retirementReason(" ")
                .build();

        mockMvc.perform(patch("/api/devices/retire/{id}", 5L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(service);
    }

    @Test
    @WithMockUser(authorities = "DEVICE_RETIRE")
    void retire_whenDeviceConflicts_returnsConflict() throws Exception {
        RetireDeviceRequest request = RetireDeviceRequest.builder()
                .retirementReason("Broken")
                .build();
        when(service.retire(anyLong(), any(RetireDeviceRequest.class)))
                .thenThrow(new DeviceConflictException("Device is already retired"));

        mockMvc.perform(patch("/api/devices/retire/{id}", 5L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict());
    }

    @Test
    void getAll_withoutAuthentication_returnsUnauthorized() throws Exception {
        mockMvc.perform(get("/api/devices"))
                .andExpect(status().isUnauthorized());

        verifyNoInteractions(service);
    }

    @Test
    @WithMockUser(authorities = "DEVICE_CREATE")
    void getAll_withoutDeviceRead_returnsForbidden() throws Exception {
        mockMvc.perform(get("/api/devices"))
                .andExpect(status().isForbidden());

        verifyNoInteractions(service);
    }

    @Test
    @WithMockUser(authorities = "DEVICE_READ")
    void create_withoutDeviceCreate_returnsForbidden() throws Exception {
        CreateDeviceRequest request = CreateDeviceRequest.builder()
                .type(DeviceTypeRequest.builder().name("test-type").build())
                .name("test-device")
                .serialNumber("000-000100-22SS")
                .model("Legion X3")
                .make("Lenovo")
                .physicalCondition(DeviceCondition.GOOD)
                .build();

        mockMvc.perform(post("/api/devices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isForbidden());

        verifyNoInteractions(service);
    }

    @Test
    @WithMockUser(authorities = "DEVICE_READ")
    void update_withoutDeviceUpdate_returnsForbidden() throws Exception {
        UpdateDeviceRequest request = UpdateDeviceRequest.builder()
                .name("updated-device")
                .build();

        mockMvc.perform(patch("/api/devices/{id}", 5L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isForbidden());

        verifyNoInteractions(service);
    }

    @Test
    @WithMockUser(authorities = "DEVICE_READ")
    void retire_withoutDeviceRetire_returnsForbidden() throws Exception {
        RetireDeviceRequest request = RetireDeviceRequest.builder()
                .retirementReason("Broken")
                .build();

        mockMvc.perform(patch("/api/devices/retire/{id}", 5L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isForbidden());

        verifyNoInteractions(service);
    }

}
