package com.example.core.exception.exceptions;

import com.example.livescore.web.players.SavePlayerDTO;

public class EqualPlayersException extends RuntimeException {

    private EqualPlayersException(String message) {
        super(message);
    }

    public static EqualPlayersException withEqualPlayersData(SavePlayerDTO player) {
        return new EqualPlayersException("Данный игрок уже был введен! Пожалуйста перепроверьте следующие данные:\n" +
                player.toString());
    }
}
