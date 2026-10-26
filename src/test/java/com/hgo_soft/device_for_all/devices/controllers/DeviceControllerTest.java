package com.hgo_soft.device_for_all.devices.controllers;

import com.hgo_soft.device_for_all.auth.filters.JwtAuthenticationFilter;
import com.hgo_soft.device_for_all.common.security.WebMvcSecurityTestConfiguration;
import com.hgo_soft.device_for_all.devices.entities.Device;
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
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
        when(service.findById(1L)).thenReturn(Optional.of(Device.builder().id(1L).build()));

        mockMvc.perform(get("/api/devices/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @WithMockUser(authorities = "DEVICE_READ")
    void getById_whenMissing_returnsNotFound() throws Exception {
        when(service.findById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/devices/{id}", 999L))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(authorities = "DEVICE_CREATE")
    void create_returnsCreatedDevice() throws Exception {
        mockMvc.perform(post("/api/devices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"id": 10}
                                """))
                .andExpect(status().isCreated())
                .andExpect(header().string(HttpHeaders.LOCATION, "/api/devices/10"))
                .andExpect(jsonPath("$.id").value(10));
    }

    @Test
    @WithMockUser(authorities = "DEVICE_UPDATE")
    void update_returnsUpdatedDevice() throws Exception {
        mockMvc.perform(put("/api/devices/{id}", 5L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"id": 99}
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(5));
    }

    @Test
    @WithMockUser(authorities = "DEVICE_RETIRE")
    void delete_returnsOk() throws Exception {
        mockMvc.perform(delete("/api/devices/{id}", 1L))
                .andExpect(status().isOk());

        verify(service).deleteById(1L);
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
    void delete_withOnlyDeviceRead_returnsForbiddenWithoutCallingService() throws Exception {
        mockMvc.perform(delete("/api/devices/{id}", 1L))
                .andExpect(status().isForbidden());

        verifyNoInteractions(service);
    }
}
