package com.hgo_soft.device_for_all.auth.dtos;

import lombok.Data;

@Data
public class LoginRequest {

    private String username;
    private String password;
}