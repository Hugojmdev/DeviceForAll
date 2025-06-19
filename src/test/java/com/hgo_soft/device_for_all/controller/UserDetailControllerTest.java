package com.hgo_soft.device_for_all.controller;


import com.hgo_soft.device_for_all.dto.UserDetailDto;
import com.hgo_soft.device_for_all.entity.UserDetail;
import com.hgo_soft.device_for_all.mapper.UserDetailMapper;
import com.hgo_soft.device_for_all.service.UserDetailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

class UserDetailControllerTest {

    private UserDetailService service;
    private UserDetailMapper mapper;
    private UserDetailController controller;

    @BeforeEach
    void setUp() {
        service = mock(UserDetailService.class);
        mapper = mock(UserDetailMapper.class);
        controller = new UserDetailController(service, mapper);
    }

    @Test
    void testGetAll_ShouldReturnListOfUserDetails() {
        List<UserDetail> userDetails = Arrays.asList(
                UserDetail.builder().id(1L).build(),
                UserDetail.builder().id(2L).build()
        );
        List<UserDetailDto> userDetailDtos = Arrays.asList(
                UserDetailDto.builder().id(1L).build(),
                UserDetailDto.builder().id(2L).build()
        );
        when(mapper.toEntityList(anyList())).thenReturn(userDetails);
        when(mapper.toDtoList(anyList())).thenReturn(userDetailDtos);

        when(service.findAll()).thenReturn(userDetails);

        ResponseEntity<List<UserDetailDto>> response = controller.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        verify(service).findAll();
    }

    @Test
    void testGetAll_ShouldReturnNoContent() {
        List<UserDetailDto> userDetailDtos = new ArrayList<>();
        List<UserDetail> userDetails = new ArrayList<>();
        when(mapper.toEntityList(anyList())).thenReturn(userDetails);
        when(mapper.toDtoList(anyList())).thenReturn(userDetailDtos);
        when(service.findAll()).thenReturn(Collections.emptyList());

        ResponseEntity<List<UserDetailDto>> response = controller.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testGetById_Found() {
        long id = 1L;
        UserDetailDto inputDto = UserDetailDto.builder().id(id).build();
        UserDetail entityToFound = UserDetail.builder().id(id).build();
        UserDetailDto dtoToFound = UserDetailDto.builder().id(id).build();
        when(mapper.toEntity(inputDto)).thenReturn(entityToFound);
        when(mapper.toDto(entityToFound)).thenReturn(dtoToFound);
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
        UserDetailDto savedDto = UserDetailDto.builder().id(id).build();
        // simulate mapper and persistence
        when(mapper.toEntity(inputDto)).thenReturn(savedEntity);
        when(mapper.toDto(savedEntity)).thenReturn(savedDto);
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
        UserDetailDto savedDto = UserDetailDto.builder().id(id).build();
        when(mapper.toEntity(inputDto)).thenReturn(savedEntity);
        when(mapper.toDto(savedEntity)).thenReturn(savedDto);
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
