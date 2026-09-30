package com.jwtinternalserviceapi.dto;

import com.jwtinternalserviceapi.model.Role;
import jakarta.validation.constraints.NotNull;

public record UpdateRoleRequest(@NotNull Role role) {
}
