package com.example.core.exception.exceptions;

import com.example.core.enums.ErrorMessage;

public class EventException extends RuntimeException {
    private EventException(String message) {
        super(message);
    }

    public static EventException build(ErrorMessage errorMessage, Long playerId) {
        return new EventException(String.format(errorMessage.getMessage(), playerId));
    }
}
