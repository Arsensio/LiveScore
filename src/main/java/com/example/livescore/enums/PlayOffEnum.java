package com.example.livescore.enums;

public enum PlayOffEnum {

    ROUND_OF_32("Round of 32"),
    ROUND_OF_16("Round of 16"),
    QUARTER_FINAL("Quarter-Finals"),
    SEMI_FINAL("Semi-Finals"),
    FINAL("Final");


    private final String name;

    PlayOffEnum(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
