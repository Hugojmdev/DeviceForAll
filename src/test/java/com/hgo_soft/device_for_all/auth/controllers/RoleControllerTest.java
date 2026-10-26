package com.hgo_soft.device_for_all.auth.controllers;

import com.hgo_soft.device_for_all.auth.entities.Role;
import com.hgo_soft.device_for_all.auth.filters.JwtAuthenticationFilter;
import com.hgo_soft.device_for_all.auth.mappers.RoleMapperImpl;
import com.hgo_soft.device_for_all.auth.services.RoleService;
import com.hgo_soft.device_for_all.common.security.WebMvcSecurityTestConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
        controllers = RoleController.class,
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                classes = JwtAuthenticationFilter.class
        )
)
@Import({WebMvcSecurityTestConfiguration.class, RoleMapperImpl.class})
class RoleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RoleService service;

    @Test
    @WithMockUser(authorities = "ACCESS_MANAGE")
    void getAll_returnsRoles() throws Exception {
        when(service.findAll()).thenReturn(List.of(
                Role.builder().id(1L).name("ADMIN").build(),
                Role.builder().id(2L).name("STAFF").build()
        ));

        mockMvc.perform(get("/api/roles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2));

        verify(service).findAll();
    }

    @Test
    @WithMockUser(authorities = "ACCESS_MANAGE")
    void getAll_whenEmpty_returnsEmptyArray() throws Exception {
        when(service.findAll()).thenReturn(List.of());

        mockMvc.perform(get("/api/roles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    @WithMockUser(authorities = "ACCESS_MANAGE")
    void getById_whenFound_returnsRole() throws Exception {
        when(service.findById(1L)).thenReturn(Optional.of(
                Role.builder().id(1L).name("ADMIN").build()
        ));

        mockMvc.perform(get("/api/roles/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @WithMockUser(authorities = "ACCESS_MANAGE")
    void getById_whenMissing_returnsNotFound() throws Exception {
        when(service.findById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/roles/{id}", 999L))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAll_withoutAuthentication_returnsUnauthorized() throws Exception {
        mockMvc.perform(get("/api/roles"))
                .andExpect(status().isUnauthorized());

        verifyNoInteractions(service);
    }

    @Test
    @WithMockUser(roles = "STAFF")
    void getAll_withNonAdminRole_returnsForbidden() throws Exception {
        mockMvc.perform(get("/api/roles"))
                .andExpect(status().isForbidden());

        verifyNoInteractions(service);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void getAll_withAdminRoleButWithoutAccessManage_returnsForbidden() throws Exception {
        mockMvc.perform(get("/api/roles"))
                .andExpect(status().isForbidden());

        verifyNoInteractions(service);
    }
}
