package com.hgo_soft.device_for_all.auth.dtos;

import jakarta.validation.constraints.NotEmpty;

import java.util.Set;

public record UpdateUserRolesRequest(
        @NotEmpty
        Set<String> roles
) {
}
