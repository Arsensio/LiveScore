package com.example.livescore.enums;

public enum EventNames {
    // event enum ID = 1
    GOAL("GOAL"),

    // 2
    ASSIST("ASSIST"),

    // 3
    YELLOW_CARD("YELLOW_CARD"),

    // 4
    RED_CARD("RED_CARD"),

    //5
    PENALTY("PENALTY"),

    //6
    SCORE_PENALTY("SCORE_PENALTY"),

    //7
    MISS_PENALTY("MISS_PENALTY");

    private String eventName;

    EventNames(String eventName) {
        this.eventName = eventName;
    }

    public static String getEventNameById(Long id) {
        return switch (id.intValue()) {
            case 1 -> GOAL.getEventName();
            case 2 -> ASSIST.getEventName();
            case 3 -> YELLOW_CARD.getEventName();
            case 4 -> RED_CARD.getEventName();
            case 5 -> PENALTY.getEventName();
            case 6 -> SCORE_PENALTY.getEventName();
            case 7 -> MISS_PENALTY.getEventName();
            default -> throw new IllegalStateException("Unexpected value: " + id);
        };
    }

    public static EventNames getEventById(Long id) {
        return switch (id.intValue()) {
            case 1 -> GOAL;
            case 2 -> ASSIST;
            case 3 -> YELLOW_CARD;
            case 4 -> RED_CARD;
            case 5 -> PENALTY;
            case 6 -> SCORE_PENALTY;
            case 7 -> MISS_PENALTY;
            default -> throw new IllegalStateException("Unexpected value: " + id);
        };
    }

    public String getEventName() {
        return this.eventName;
    }
}
