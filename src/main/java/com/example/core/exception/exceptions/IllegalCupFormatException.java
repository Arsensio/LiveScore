package com.example.core.exception.exceptions;

import static com.example.core.enums.ErrorMessage.ILLEGAL_CUP_FORMAT_EXCEPTION;

public class IllegalCupFormatException extends RuntimeException {

    private IllegalCupFormatException(String message) {
        super(message);
    }

    public static <I> IllegalCupFormatException build(int teamNumber) {
        return new IllegalCupFormatException(String.format(ILLEGAL_CUP_FORMAT_EXCEPTION.getMessage(), teamNumber));
    }
}
