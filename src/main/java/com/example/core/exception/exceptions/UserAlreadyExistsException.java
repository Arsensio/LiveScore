package com.example.core.exception.exceptions;

public class UserAlreadyExistsException extends RuntimeException {

    private UserAlreadyExistsException(String message) {
        super(message);
    }

    public static UserAlreadyExistsException build(String username) {
        return new UserAlreadyExistsException(String.format("User with username %s already exists!", username));
    }
}
