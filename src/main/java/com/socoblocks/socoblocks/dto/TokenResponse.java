package com.jwtinternalserviceapi.dto;

public record TokenResponse(String accessToken, String tokenType, long expiresIn) {
}
