package com.socoblocks.socoblocks.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import com.socoblocks.socoblocks.model.Role;

public record RegisterRequest(
        @NotBlank @Size(min = 3, max = 50) String username,
        @NotBlank @Size(min = 8, max = 100) String password,
        Role role) { //AHora el usuario puede elegir con que tipo de rol puede entrar
}
