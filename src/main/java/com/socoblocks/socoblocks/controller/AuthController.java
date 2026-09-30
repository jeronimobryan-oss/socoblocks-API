package com.jwtinternalserviceapi.controller;

import com.jwtinternalserviceapi.dto.LoginRequest;
import com.jwtinternalserviceapi.dto.RegisterRequest;
import com.jwtinternalserviceapi.dto.TokenResponse;
import com.jwtinternalserviceapi.dto.UserResponse;
import com.jwtinternalserviceapi.service.AuthService;
import com.jwtinternalserviceapi.service.TokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final TokenService tokenService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(tokenService.login(request));
    }
}
