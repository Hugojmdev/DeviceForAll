package com.hgo_soft.device_for_all.users.dtos;

public record UserResponse(
        Long id,
        String username,
        String email,
        boolean enabled
) {}
