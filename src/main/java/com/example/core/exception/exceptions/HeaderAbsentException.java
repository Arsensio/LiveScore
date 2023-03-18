package com.example.core.exception.exceptions;

public class HeaderAbsentException extends RuntimeException {

    private HeaderAbsentException(String message) {
        super(message);
    }

    public static HeaderAbsentException build(String header) {
        return new HeaderAbsentException("Necessary header \"" + header + "\" absent!");
    }
}
