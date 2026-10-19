package com.hgo_soft.device_for_all.auth.controllers;

import com.hgo_soft.device_for_all.auth.dtos.AuthResponse;
import com.hgo_soft.device_for_all.auth.dtos.LoginRequest;
import com.hgo_soft.device_for_all.auth.services.JwtService;
import com.hgo_soft.device_for_all.common.api.AbstractRestController;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController extends AbstractRestController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()
                )
        );

        String token = jwtService.generateJwtToken(request.username());
        return ResponseEntity.ok(new AuthResponse(token));

    }
}