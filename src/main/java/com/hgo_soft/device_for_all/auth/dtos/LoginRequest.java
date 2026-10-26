package com.hgo_soft.device_for_all.auth.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank
        String username,
        @NotBlank
        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        String password
) {
    @Override
    public String toString() {
        return "LoginRequest[username=" + username
                + ", password=[REDACTED]]";
    }
}
