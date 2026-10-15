package com.hgo_soft.device_for_all.users.dtos;

import jakarta.validation.constraints.NotBlank;

public record UpdateUserRequest(

        @NotBlank String username
) {}
