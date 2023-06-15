package com.example.core.exception.exceptions;

import static com.example.core.enums.ErrorMessage.INVALID_TOURNAMENT_TYPE_EXCEPTION;

public class InvalidTournamentTypeException extends RuntimeException {

    private InvalidTournamentTypeException(String message) {
        super(message);
    }

    public static InvalidTournamentTypeException build(long tournamentId) {
        return new InvalidTournamentTypeException(String.format(INVALID_TOURNAMENT_TYPE_EXCEPTION.getMessage(), tournamentId));
    }
}
