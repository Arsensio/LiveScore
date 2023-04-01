package com.example.core.exception.exceptions;


import static com.example.core.enums.ErrorMessage.RESOURCE_NOT_FOUND_EXCEPTION;

public class ResourceNotFoundException extends RuntimeException {

    private ResourceNotFoundException(String message) {
        super(message);
    }

    public static <I> ResourceNotFoundException build(I id, String entityName) {
        return new ResourceNotFoundException(String.format(
                RESOURCE_NOT_FOUND_EXCEPTION.getMessage(), entityName, id)
        );
    }
}
