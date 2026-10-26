package com.hgo_soft.device_for_all.users.controllers;

import com.hgo_soft.device_for_all.auth.filters.JwtAuthenticationFilter;
import com.hgo_soft.device_for_all.common.exceptions.ResourceNotFoundException;
import com.hgo_soft.device_for_all.common.exceptions.handlers.GlobalExceptionHandler;
import com.hgo_soft.device_for_all.common.security.WebMvcSecurityTestConfiguration;
import com.hgo_soft.device_for_all.users.dtos.CreateUserRequest;
import com.hgo_soft.device_for_all.users.dtos.UpdateUserRequest;
import com.hgo_soft.device_for_all.users.entities.User;
import com.hgo_soft.device_for_all.users.mappers.UserMapperImpl;
import com.hgo_soft.device_for_all.users.services.UserService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
        controllers = UserController.class,
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                classes = JwtAuthenticationFilter.class
        )
)
@Import({
        WebMvcSecurityTestConfiguration.class,
        UserMapperImpl.class,
        GlobalExceptionHandler.class
})
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService service;

    @Test
    @WithMockUser(authorities = "USER_READ")
    void getAll_returnsUsers() throws Exception {
        when(service.findAll()).thenReturn(List.of(
                User.builder().id(1L).username("alice").enabled(true).build(),
                User.builder().id(2L).username("bob").enabled(true).build()
        ));

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2));

        verify(service).findAll();
    }

    @Test
    @WithMockUser(authorities = "USER_READ")
    void getAll_whenEmpty_returnsEmptyArray() throws Exception {
        when(service.findAll()).thenReturn(List.of());

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    @WithMockUser(authorities = "USER_READ")
    void getById_whenFound_returnsUser() throws Exception {
        when(service.findById(1L)).thenReturn(Optional.of(
                User.builder().id(1L).username("alice").enabled(true).build()
        ));

        mockMvc.perform(get("/api/users/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @WithMockUser(authorities = "USER_READ")
    void getById_whenMissing_returnsNotFound() throws Exception {
        when(service.findById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/users/{id}", 999L))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(authorities = "USER_READ")
    void create_returnsCreatedUser() throws Exception {
        CreateUserRequest request = new CreateUserRequest(
                "test",
                "test@example.com",
                "validPassword123"
        );
        when(service.create(request)).thenReturn(
                User.builder().id(10L).username("test").email("test@example.com").enabled(true).build()
        );

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "username": "test",
                                  "email": "test@example.com",
                                  "password": "validPassword123"
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(header().string(HttpHeaders.LOCATION, "/api/users/10"))
                .andExpect(jsonPath("$.id").value(10));

        verify(service).create(request);
    }

    @Test
    @WithMockUser(authorities = "USER_READ")
    void update_returnsUpdatedUser() throws Exception {
        UpdateUserRequest request = new UpdateUserRequest("updated-user");
        when(service.update(5L, request)).thenReturn(
                User.builder().id(5L).username("updated-user").enabled(true).build()
        );

        mockMvc.perform(put("/api/users/{id}", 5L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"username": "updated-user"}
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(5));

        verify(service).update(5L, request);
    }

    @Test
    @WithMockUser(authorities = "USER_READ")
    void update_whenUserDoesNotExist_returnsNotFoundApiError() throws Exception {
        UpdateUserRequest request = new UpdateUserRequest("updated-user");
        when(service.update(999L, request))
                .thenThrow(new ResourceNotFoundException("User not found"));

        mockMvc.perform(put("/api/users/{id}", 999L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"username": "updated-user"}
                                """))
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Resource Not Found!!!"));

        verify(service).update(999L, request);
    }

    @Test
    @WithMockUser(authorities = "USER_READ")
    void delete_returnsOk() throws Exception {
        mockMvc.perform(delete("/api/users/{id}", 1L))
                .andExpect(status().isOk());

        verify(service).deleteById(1L);
    }

    @Test
    void getAll_withoutAuthentication_returnsUnauthorized() throws Exception {
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isUnauthorized());

        verifyNoInteractions(service);
    }

    @Test
    @WithMockUser(authorities = "DEVICE_READ")
    void getAll_withoutUserRead_returnsForbidden() throws Exception {
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isForbidden());

        verifyNoInteractions(service);
    }
}
