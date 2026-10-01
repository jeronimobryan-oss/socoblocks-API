package com.socoblocks.socoblocks.controller;

import com.socoblocks.socoblocks.dto.LoginRequest;
import com.socoblocks.socoblocks.dto.RegisterRequest;
import com.socoblocks.socoblocks.dto.TokenResponse;
import com.socoblocks.socoblocks.dto.UserResponse;
import com.socoblocks.socoblocks.service.AuthService;
import com.socoblocks.socoblocks.service.TokenService;
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
