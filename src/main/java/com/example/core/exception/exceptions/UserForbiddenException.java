package com.example.core.exception.exceptions;

public class UserForbiddenException extends RuntimeException {

    public UserForbiddenException(String message) {
        super(message);
    }
}
