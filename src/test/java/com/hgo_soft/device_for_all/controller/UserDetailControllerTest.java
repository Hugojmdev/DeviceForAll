package com.hgo_soft.device_for_all.controller;


import com.hgo_soft.device_for_all.dto.UserDetailDto;
import com.hgo_soft.device_for_all.entity.UserDetail;
import com.hgo_soft.device_for_all.mapper.UserDetailMapper;
import com.hgo_soft.device_for_all.mapper.UserDetailMapperImpl;
import com.hgo_soft.device_for_all.service.UserDetailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

public class UserDetailControllerTest {

    private UserDetailService service;
    private UserDetailController controller;

    @BeforeEach
    void setUp() {
        service = mock(UserDetailService.class);
        UserDetailMapper mapper = new UserDetailMapperImpl();
        controller = new UserDetailController(service, mapper);
    }

    @Test
    void testGetAll_ShouldReturnListOfUserDetails() {
        List<UserDetail> userDetails = Arrays.asList(
                UserDetail.builder().id(1L).build(),
                UserDetail.builder().id(2L).build()
        );
        when(service.findAll()).thenReturn(userDetails);

        ResponseEntity<List<UserDetailDto>> response = controller.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        verify(service).findAll();
    }

    @Test
    void testGetAll_ShouldReturnNoContent() {
        when(service.findAll()).thenReturn(Collections.emptyList());

        ResponseEntity<List<UserDetailDto>> response = controller.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isEmpty());
    }

    @Test
    void testGetById_Found() {
        long id = 1L;
        UserDetail entityToFound = UserDetail.builder().id(id).build();
        when(service.findById(any())).thenReturn(Optional.of(entityToFound));

        ResponseEntity<UserDetailDto> response = controller.getById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(id, response.getBody().getId());
    }

    @Test
    void testGetById_NotFound() {
        when(service.findById(999L)).thenReturn(Optional.empty());

        ResponseEntity<UserDetailDto> response = controller.getById(999L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testCreate_ShouldReturnCreatedUserDetail() {
        long id = 10L;
        UserDetailDto inputDto = new UserDetailDto();
        UserDetail savedEntity = UserDetail.builder().id(id).build();
        when(service.save(any())).thenReturn(savedEntity);
        ResponseEntity<UserDetailDto> response = controller.create(inputDto);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("/api/user-details/10", Objects.requireNonNull(response.getHeaders().getLocation()).toString());
    }

    @Test
    void testUpdate_ShouldReturnUpdatedUserDetail() {
        Long id = 5L;
        UserDetailDto inputDto = UserDetailDto.builder().id(id).build();
        UserDetail savedEntity = UserDetail.builder().id(id).build();
        when(service.save(any())).thenReturn(savedEntity);
        ResponseEntity<UserDetailDto> response = controller.update(id, inputDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(id, response.getBody().getId());
    }

    @Test
    void testDelete_ShouldReturnOk() {
        doNothing().when(service).deleteById(1L);

        ResponseEntity<Void> response = controller.delete(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(service).deleteById(1L);
    }
}
