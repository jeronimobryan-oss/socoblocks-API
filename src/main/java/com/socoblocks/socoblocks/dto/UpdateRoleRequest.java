package com.socoblocks.socoblocks.dto;

import com.socoblocks.socoblocks.model.Role;
import jakarta.validation.constraints.NotNull;

public record UpdateRoleRequest(@NotNull Role role) {
}
