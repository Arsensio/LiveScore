package com.example.core.exception;

import com.example.core.exception.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = ResourceNotFoundException.class)
    protected ResponseEntity<Object> handleNotFound(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CustomErrorBody(ex.getMessage(), LocalDateTime.now()));
    }

    @ExceptionHandler(value = IllegalCupFormatException.class)
    protected ResponseEntity<Object> handleIllegalCup(IllegalCupFormatException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CustomErrorBody(ex.getMessage(), LocalDateTime.now()));
    }

    @ExceptionHandler(value = UnsupportedMethodException.class)
    protected ResponseEntity<Object> handleUnsupportedMethod(UnsupportedMethodException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CustomErrorBody(ex.getMessage(), LocalDateTime.now()));
    }

    @ExceptionHandler(value = EqualPlayersException.class)
    protected ResponseEntity<Object> handleEqualPlayers(EqualPlayersException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CustomErrorBody(ex.getMessage(), LocalDateTime.now()));
    }

    @ExceptionHandler(value = PlayerNullFieldsException.class)
    protected ResponseEntity<Object> handlePlayerNullFields(PlayerNullFieldsException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CustomErrorBody(ex.getMessage(), LocalDateTime.now()));
    }

    @ExceptionHandler(value = EventException.class)
    protected ResponseEntity<Object> handleEventAlreadyExists(EventException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CustomErrorBody(ex.getMessage(), LocalDateTime.now()));
    }

    @ExceptionHandler(value = InvalidTournamentTypeException.class)
    protected ResponseEntity<Object> handleInvalidTournamentType(InvalidTournamentTypeException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CustomErrorBody(ex.getMessage(), LocalDateTime.now()));
    }
}
