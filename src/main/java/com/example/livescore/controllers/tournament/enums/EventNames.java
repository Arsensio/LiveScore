package com.example.livescore.controllers.tournament.enums;

public enum EventNames {
    // event enum ID = 1
    GOAL,

    // 2
    ASSIST,

    // 3
    YELLOW_CARD,

    // 4
    RED_CARD;

    public static EventNames getEventById(Long id) {
        return switch (id.intValue()) {
            case 1 -> GOAL;
            case 2 -> ASSIST;
            case 3 -> YELLOW_CARD;
            case 4 -> RED_CARD;
            default -> throw new IllegalStateException("Unexpected value: " + id);
        };
    }
}
