package com.example.core.exception.exceptions;

import static com.example.core.enums.ErrorMessage.UNSUPPORTED_METHOD_EXCEPTION;

public class UnsupportedMethodException extends RuntimeException {

    private UnsupportedMethodException(String message) {
        super(message);
    }

    public static UnsupportedMethodException build(String methodName, String className) {
        return new UnsupportedMethodException(String.format(UNSUPPORTED_METHOD_EXCEPTION.getMessage(), methodName, className));
    }

}
