package com.hgo_soft.device_for_all.controller;

import com.hgo_soft.device_for_all.dto.UserDetailDto;
import com.hgo_soft.device_for_all.mapper.UserDetailMapper;
import com.hgo_soft.device_for_all.service.UserDetailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-details")
public class UserDetailController extends AbstractRestController{

    private final UserDetailService service;
    private final UserDetailMapper mapper;

    public UserDetailController(UserDetailService service, UserDetailMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<UserDetailDto>> getAll() {
        return okOrEmpty(mapper.toDtoList(service.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDetailDto> getById(@PathVariable Long id) {
        return okOrNotFound(
                service.findById(id).map(mapper::toDto)
        );
    }

    @PostMapping
    public ResponseEntity<UserDetailDto> create(@RequestBody UserDetailDto userDetailDto) {
        UserDetailDto saved = mapper.toDto(service.save(mapper.toEntity(userDetailDto)));
        return created("/api/user-details/" + saved.getId(), saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDetailDto> update(@PathVariable Long id, @RequestBody UserDetailDto userDetailDto) {
        userDetailDto.setId(id);
        UserDetailDto updated = mapper.toDto(service.save(mapper.toEntity(userDetailDto)));
        return okOrNotFound(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteById(id);
        return deletedSuccessfully();
    }
}
