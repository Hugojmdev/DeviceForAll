package com.hgo_soft.device_for_all.loans.controllers;

import com.hgo_soft.device_for_all.auth.filters.JwtAuthenticationFilter;
import com.hgo_soft.device_for_all.common.security.WebMvcSecurityTestConfiguration;
import com.hgo_soft.device_for_all.loans.entities.Loan;
import com.hgo_soft.device_for_all.loans.mappers.LoanMapperImpl;
import com.hgo_soft.device_for_all.loans.services.LoanService;
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
        controllers = LoanController.class,
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                classes = JwtAuthenticationFilter.class
        )
)
@Import({WebMvcSecurityTestConfiguration.class, LoanMapperImpl.class})
class LoanControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LoanService service;

    @Test
    @WithMockUser(authorities = "LOAN_READ_ALL")
    void getAll_returnsLoans() throws Exception {
        when(service.findAll()).thenReturn(List.of(
                Loan.builder().id(1L).build(),
                Loan.builder().id(2L).build()
        ));

        mockMvc.perform(get("/api/loans"))
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

        mockMvc.perform(get("/api/loans"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    @WithMockUser(authorities = "LOAN_READ_OWN")
    void getById_whenFound_returnsLoan() throws Exception {
        when(service.findById(1L)).thenReturn(Optional.of(Loan.builder().id(1L).build()));

        mockMvc.perform(get("/api/loans/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @WithMockUser(authorities = "LOAN_READ_OWN")
    void getById_whenMissing_returnsNotFound() throws Exception {
        when(service.findById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/loans/{id}", 999L))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(authorities = "LOAN_CREATE_OWN")
    void create_returnsCreatedLoan() throws Exception {
        when(service.save(any(Loan.class))).thenReturn(Loan.builder().id(10L).build());

        mockMvc.perform(post("/api/loans")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"id": 10}
                                """))
                .andExpect(status().isCreated())
                .andExpect(header().string(HttpHeaders.LOCATION, "/api/loans/10"))
                .andExpect(jsonPath("$.id").value(10));
    }

    @Test
    @WithMockUser(authorities = "LOAN_CREATE_OWN")
    void update_returnsUpdatedLoan() throws Exception {
        when(service.save(any(Loan.class))).thenReturn(Loan.builder().id(5L).build());

        mockMvc.perform(put("/api/loans/{id}", 5L)
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
        mockMvc.perform(delete("/api/loans/{id}", 1L))
                .andExpect(status().isOk());

        verify(service).deleteById(1L);
    }

    @Test
    void getAll_withoutAuthentication_returnsUnauthorized() throws Exception {
        mockMvc.perform(get("/api/loans"))
                .andExpect(status().isUnauthorized());

        verifyNoInteractions(service);
    }

    @Test
    @WithMockUser(authorities = "DEVICE_READ")
    void getAll_withoutLoanReadAll_returnsForbidden() throws Exception {
        mockMvc.perform(get("/api/loans"))
                .andExpect(status().isForbidden());

        verifyNoInteractions(service);
    }
}
