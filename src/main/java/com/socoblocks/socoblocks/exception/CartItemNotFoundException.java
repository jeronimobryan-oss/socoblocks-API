package com.socoblocks.socoblocks.exception;

public class CartItemNotFoundException extends RuntimeException {
    public CartItemNotFoundException(Long id) {
        super("Item del carrito no encontrado: " + id);
    }
}
