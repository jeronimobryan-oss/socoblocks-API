package com.socoblocks.socoblocks.exception;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(Long id) {
        super("No se encontró el set con el id " + id);
    }
}
