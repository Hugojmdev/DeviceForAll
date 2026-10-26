package com.hgo_soft.device_for_all.auth.dtos;

import java.util.Set;


public record RoleResponse(
        Long id,
        String name,
        Set<PermissionResponse> permissions
) {
}
