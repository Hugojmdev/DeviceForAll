package com.hgo_soft.device_for_all.controller;

import com.hgo_soft.device_for_all.dto.UserDto;
import com.hgo_soft.device_for_all.entity.User;
import com.hgo_soft.device_for_all.mapper.UserMapper;
import com.hgo_soft.device_for_all.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public List<UserDto> getAll() {
        return UserMapper.toDtoList(service.findAll());
    }

    @GetMapping("/{id}")
    public UserDto getById(@PathVariable Long id) {
        return UserMapper.toDto(service.findById(id));
    }

    @PostMapping
    public UserDto create(@RequestBody User entity) {
        return UserMapper.toDto(service.save(entity));
    }

    @PutMapping("/{id}")
    public UserDto update(@PathVariable Long id, @RequestBody User entity) {
        entity.setId(id);
        return UserMapper.toDto(service.save(entity));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteById(id);
    }
}
