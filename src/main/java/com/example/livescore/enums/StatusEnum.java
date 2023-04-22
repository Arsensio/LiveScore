package com.example.livescore.enums;

public enum StatusEnum {

    CREATED("CREATED"),
    IN_PROGRESS("IN_PROGRESS"),
    FINISHED("FINISHED");
    private final String status;

    StatusEnum(String s) {
        this.status = s;
    }

    @Override
    public String toString() {
        return this.status;
    }
}
