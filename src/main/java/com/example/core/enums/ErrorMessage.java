package com.example.core.enums;

public enum ErrorMessage {

    RESOURCE_NOT_FOUND_EXCEPTION("There is no such %s with id: %s");

    private String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage(){
        return this.message;
    }
}
