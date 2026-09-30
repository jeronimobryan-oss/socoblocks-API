package com.jwtinternalserviceapi.service;

import com.jwtinternalserviceapi.dto.LoginRequest;
import com.jwtinternalserviceapi.dto.TokenResponse;
import com.jwtinternalserviceapi.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public TokenResponse login(LoginRequest request) {
        Authentication auth = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken.unauthenticated(request.username(), request.password()));
        return new TokenResponse(jwtService.generateToken(auth), "Bearer", jwtService.expiresInSeconds());
    }

}
