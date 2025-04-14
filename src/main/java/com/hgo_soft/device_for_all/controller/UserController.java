package com.hgo_soft.device_for_all.controller;

import com.hgo_soft.device_for_all.dto.UserDto;
import com.hgo_soft.device_for_all.mapper.UserMapper;
import com.hgo_soft.device_for_all.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController extends AbstractRestController{

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAll() {
        return okOrEmpty(UserMapper.toDtoList(service.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getById(@PathVariable Long id) {
        return okOrNotFound(UserMapper.toDto(service.findById(id)));
    }

    @PostMapping
    public ResponseEntity<UserDto> create(@RequestBody UserDto userDto) {
        UserDto saved = UserMapper.toDto(service.save(UserMapper.toEntity(userDto)));
        return created("/api/users/" + saved.getId(), saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> update(@PathVariable Long id, @RequestBody UserDto userDto) {
        userDto.setId(id);
        UserDto updated = UserMapper.toDto(service.save(UserMapper.toEntity(userDto)));
        return okOrNotFound(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteById(id);
        return deletedSuccessfully();
    }
}
