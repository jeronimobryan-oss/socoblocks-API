package com.socoblocks.socoblocks.dto;

import org.springframework.http.HttpStatus;
import java.time.Instant;
import java.util.Map;
public record ApiError(int status, String message, Map<String, String> errors, Instant timestamp) {
    public static ApiError of(HttpStatus status, String message) {
        return new ApiError(status.value(), message, Map.of(), Instant.now());
    }

    public static ApiError of(HttpStatus status, String message, Map<String, String> errors) {
        return new ApiError(status.value(), message, errors, Instant.now());
    }
}
