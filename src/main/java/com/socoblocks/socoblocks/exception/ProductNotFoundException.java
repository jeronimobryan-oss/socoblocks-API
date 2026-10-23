package com.socoblocks.socoblocks.exception;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(Long id) {
        super("Product not found: " + id);
    }
}
