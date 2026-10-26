package com.socoblocks.socoblocks.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record AddCartItemRequest (
        @NotNull(message = "Indique el set") Long productId,
        @Min(value = 1, message = "La cantidad mínima es 1") Integer quantity
) {
    public AddCartItemRequest {
        if (quantity == null) {
            quantity = 1;
        }
    }
}
