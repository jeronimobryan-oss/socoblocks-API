package com.socoblocks.socoblocks.dto;

import com.socoblocks.socoblocks.model.Role;

public record UserResponse(Long id, String username, Role role) { }
