package com.example.core.enums;

public enum ErrorMessage {

    RESOURCE_NOT_FOUND_EXCEPTION("There is no such %s with id: %s"),
    ILLEGAL_CUP_FORMAT_EXCEPTION("Provided wrong number of teams: %s"),
    UNSUPPORTED_METHOD_EXCEPTION("Method %s in %s class is not implemented"),
    EVENT_RED_CARD_EXCEPTION("Event RED_CARD already exists for playerId: %s"),
    EVENT_SECOND_YELLOW_CARD_EXCEPTION("Event SECOND_YELLOW_CARD already exists for playerId: %s"),
    HAS_RED_CARD_EXCEPTION("Player with id: %s has a red card");

    private String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
