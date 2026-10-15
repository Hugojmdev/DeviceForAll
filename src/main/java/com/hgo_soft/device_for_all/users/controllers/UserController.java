package com.hgo_soft.device_for_all.users.controllers;

import com.hgo_soft.device_for_all.common.api.AbstractRestController;
import com.hgo_soft.device_for_all.users.dtos.CreateUserRequest;
import com.hgo_soft.device_for_all.users.dtos.UpdateUserRequest;
import com.hgo_soft.device_for_all.users.dtos.UserResponse;
import com.hgo_soft.device_for_all.users.mappers.UserMapper;
import com.hgo_soft.device_for_all.users.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController extends AbstractRestController {

    private final UserService service;
    private final UserMapper mapper;

    public UserController(UserService service, UserMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAll() {
        return okOrEmpty(mapper.toUserResponseList(service.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getById(@PathVariable Long id) {
        return okOrNotFound(
                service.findById(id).map(mapper::toUserResponse)
        );
    }

    @PostMapping
    public ResponseEntity<UserResponse> create(@Valid @RequestBody CreateUserRequest createUserRequest) {
        UserResponse saved = mapper.toUserResponse(service.create(createUserRequest));
        return created("/api/users/" + saved.id(), saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> update(@PathVariable Long id, @Valid @RequestBody UpdateUserRequest updateUserRequest) {
        UserResponse updated = mapper.toUserResponse(service.update(id, updateUserRequest));
        return okOrNotFound(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteById(id);
        return deletedSuccessfully();
    }

    @Secured("ROLE_VIEWER")
    @GetMapping("/username")
    public String getUsername() {
        SecurityContext context = SecurityContextHolder.getContext();
        return context.getAuthentication().getName();
    }

    @Secured({"ROLE_VIEWER", "ROLE_EDITOR"})
    public boolean isValidUsername(String username) {
        return service.findByUsername(username).isPresent();
    }
}
