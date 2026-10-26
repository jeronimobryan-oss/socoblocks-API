package com.socoblocks.socoblocks.exception;

public class CartItemNotFoundException extends RuntimeException {
    public CartItemNotFoundException(Long id) {
        super("Cart item not found: " + id);
    }
}
