package com.hgo_soft.device_for_all.users.controllers;

import com.hgo_soft.device_for_all.common.api.AbstractRestController;
import com.hgo_soft.device_for_all.common.security.SecuredRestController;
import com.hgo_soft.device_for_all.users.dtos.CreateUserRequest;
import com.hgo_soft.device_for_all.users.dtos.UpdateUserRequest;
import com.hgo_soft.device_for_all.users.dtos.UserResponse;
import com.hgo_soft.device_for_all.users.mappers.UserMapper;
import com.hgo_soft.device_for_all.users.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SecuredRestController
@RequestMapping("/api/users")
public class UserController extends AbstractRestController {

    private final UserService service;
    private final UserMapper mapper;

    public UserController(UserService service, UserMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PreAuthorize("hasAuthority('USER_READ')")
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAll() {
        return okOrEmpty(mapper.toUserResponseList(service.findAll()));
    }

    @PreAuthorize("hasAuthority('USER_READ')")
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getById(@PathVariable Long id) {
        return okOrNotFound(
                service.findById(id).map(mapper::toUserResponse)
        );
    }

    @PreAuthorize("hasAuthority('USER_READ')")
    @PostMapping
    public ResponseEntity<UserResponse> create(@Valid @RequestBody CreateUserRequest createUserRequest) {
        UserResponse saved = mapper.toUserResponse(service.create(createUserRequest));
        return created("/api/users/" + saved.id(), saved);
    }

    @PreAuthorize("hasAuthority('USER_READ')")
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> update(@PathVariable Long id, @Valid @RequestBody UpdateUserRequest updateUserRequest) {
        UserResponse updated = mapper.toUserResponse(service.update(id, updateUserRequest));
        return okOrNotFound(updated);
    }

    @PreAuthorize("hasAuthority('USER_READ')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteById(id);
        return deletedSuccessfully();
    }
}
