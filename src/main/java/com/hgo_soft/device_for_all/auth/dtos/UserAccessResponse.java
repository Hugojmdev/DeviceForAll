package com.hgo_soft.device_for_all.auth.dtos;

import java.util.Set;

public record UserAccessResponse(
        Long userId,
        String username,
        Set<RoleResponse> roles,
        Set<PermissionResponse> permissions
) {
}
