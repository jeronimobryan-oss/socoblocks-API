package com.socoblocks.socoblocks.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record AddCartItemRequest (
        @NotNull Long productId,
        @Min(1) Integer quantity
) {
    public AddCartItemRequest {
        if (quantity == null) {
            quantity = 1;
        }
    }
}
