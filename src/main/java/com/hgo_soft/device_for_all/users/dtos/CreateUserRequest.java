package com.hgo_soft.device_for_all.users.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateUserRequest(
        @NotBlank
        String username,
        @NotBlank
        @Email
        String email,
        @NotBlank
        @Size(min = 16)
        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        String password
        )
{
    @Override
    public String toString() {
        return "CreateUserRequest[username=" + username
                + ", email=" + email
                + ", password=[REDACTED]]";
    }
}
