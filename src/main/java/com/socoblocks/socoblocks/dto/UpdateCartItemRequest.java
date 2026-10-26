package com.socoblocks.socoblocks.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record UpdateCartItemRequest(
        @NotNull(message = "Indique el set")
        @Min(value = 1, message = "La cantidad mínima es 1") Integer quantity
) {
}
