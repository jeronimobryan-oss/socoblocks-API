package com.socoblocks.socoblocks.dto;

public record TokenResponse(String accessToken, String tokenType, long expiresIn) {
}
