package com.socoblocks.socoblocks.exception;

public class UsernameAlreadyExistsException extends RuntimeException {

    public UsernameAlreadyExistsException(String username) {
        super("Ya existe una cuenta con el correo " + username);
    }
}
