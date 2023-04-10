package com.example.core.exception.exceptions;

import com.example.livescore.web.players.SavePlayerDTO;

public class PlayerNullFieldsException extends RuntimeException {

    private PlayerNullFieldsException(String message) {
        super(message);
    }

    public static PlayerNullFieldsException withPlayerData(SavePlayerDTO playerDTO) {
        return new PlayerNullFieldsException("Данные игрока должны быть полностью заполненными! " +
                "Данные следующего игрока не заполнены полностью: " + playerDTO.toString());
    }
}
