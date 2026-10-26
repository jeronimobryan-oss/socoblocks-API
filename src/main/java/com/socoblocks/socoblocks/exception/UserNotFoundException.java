package com.socoblocks.socoblocks.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(Long id) {
        super("No se encontró el usuario con el id " + id);
    }
}
