package com.socoblocks.socoblocks.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import com.socoblocks.socoblocks.model.Role;

public record RegisterRequest(
        @NotBlank(message = "El correo es obligatorio") @Size(min = 3, max = 50, message = "El correo debe tener entre 3 y 50 caracteres") String username,
        @NotBlank (message = "La contraseña es obligatoria") @Size(min = 8, max = 100, message = "La contraseña debe tener al menos 8 caracteres") String password,
        Role role) { //AHora el usuario puede elegir con qué tipo de rol puede entrar
}
