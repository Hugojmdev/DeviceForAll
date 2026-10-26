package com.hgo_soft.device_for_all.loans.controllers;

import com.hgo_soft.device_for_all.auth.filters.JwtAuthenticationFilter;
import com.hgo_soft.device_for_all.common.security.WebMvcSecurityTestConfiguration;
import com.hgo_soft.device_for_all.loans.entities.LoanComment;
import com.hgo_soft.device_for_all.loans.mappers.LoanCommentMapperImpl;
import com.hgo_soft.device_for_all.loans.services.LoanCommentService;
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

import static org.mockito.ArgumentMatchers.any;
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
        controllers = LoanCommentController.class,
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                classes = JwtAuthenticationFilter.class
        )
)
@Import({WebMvcSecurityTestConfiguration.class, LoanCommentMapperImpl.class})
class LoanCommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LoanCommentService service;

    @Test
    @WithMockUser(authorities = "LOAN_READ_ALL")
    void getAll_returnsLoanComments() throws Exception {
        when(service.findAll()).thenReturn(List.of(
                LoanComment.builder().id(1L).build(),
                LoanComment.builder().id(2L).build()
        ));

        mockMvc.perform(get("/api/loan-comments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2));

        verify(service).findAll();
    }

    @Test
    @WithMockUser(authorities = "LOAN_READ_ALL")
    void getAll_whenEmpty_returnsEmptyArray() throws Exception {
        when(service.findAll()).thenReturn(List.of());

        mockMvc.perform(get("/api/loan-comments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    @WithMockUser(authorities = "LOAN_READ_ALL")
    void getById_whenFound_returnsLoanComment() throws Exception {
        when(service.findById(1L)).thenReturn(Optional.of(LoanComment.builder().id(1L).build()));

        mockMvc.perform(get("/api/loan-comments/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @WithMockUser(authorities = "LOAN_READ_ALL")
    void getById_whenMissing_returnsNotFound() throws Exception {
        when(service.findById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/loan-comments/{id}", 999L))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(authorities = "LOAN_CREATE_OWN")
    void create_returnsCreatedLoanComment() throws Exception {
        when(service.save(any(LoanComment.class))).thenReturn(LoanComment.builder().id(10L).build());

        mockMvc.perform(post("/api/loan-comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"id": 10}
                                """))
                .andExpect(status().isCreated())
                .andExpect(header().string(HttpHeaders.LOCATION, "/api/loan-comments/10"))
                .andExpect(jsonPath("$.id").value(10));
    }

    @Test
    @WithMockUser(authorities = "LOAN_CREATE_OWN")
    void update_returnsUpdatedLoanComment() throws Exception {
        when(service.save(any(LoanComment.class))).thenReturn(LoanComment.builder().id(5L).build());

        mockMvc.perform(put("/api/loan-comments/{id}", 5L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"id": 99}
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(5));
    }

    @Test
    @WithMockUser(authorities = "LOAN_CREATE_OWN")
    void delete_returnsOk() throws Exception {
        mockMvc.perform(delete("/api/loan-comments/{id}", 1L))
                .andExpect(status().isOk());

        verify(service).deleteById(1L);
    }

    @Test
    void getAll_withoutAuthentication_returnsUnauthorized() throws Exception {
        mockMvc.perform(get("/api/loan-comments"))
                .andExpect(status().isUnauthorized());

        verifyNoInteractions(service);
    }

    @Test
    @WithMockUser(authorities = "DEVICE_READ")
    void getAll_withoutLoanReadAll_returnsForbidden() throws Exception {
        mockMvc.perform(get("/api/loan-comments"))
                .andExpect(status().isForbidden());

        verifyNoInteractions(service);
    }
}
