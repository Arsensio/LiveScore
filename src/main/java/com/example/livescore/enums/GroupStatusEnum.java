package com.example.livescore.enums;

public enum GroupStatusEnum {

    CREATED("CREATED"),
    IN_PROGRESS("IN_PROGRESS"),
    FINISHED("FINISHED");
    private final String status;

    GroupStatusEnum(String s) {
        this.status = s;
    }

    @Override
    public String toString() {
        return this.status;
    }
}
