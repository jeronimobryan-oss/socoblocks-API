package com.jwtinternalserviceapi.dto;

import com.jwtinternalserviceapi.model.AppUser;
import com.jwtinternalserviceapi.model.Role;

public record UserResponse(Long id, String username, Role role) { }
