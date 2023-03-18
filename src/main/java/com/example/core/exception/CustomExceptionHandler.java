package com.example.core.exception;

import com.example.core.exception.exceptions.HeaderAbsentException;
import com.example.core.exception.exceptions.JwtAuthenticationException;
import com.example.core.exception.exceptions.ResourceNotFoundException;
import com.example.core.exception.exceptions.UserForbiddenException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static java.time.LocalDateTime.now;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = ResourceNotFoundException.class)
    protected ResponseEntity<Object> handleNotFound(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new CustomErrorBody(ex.getMessage(), now()));
    }

    @ExceptionHandler(value = UserForbiddenException.class)
    protected ResponseEntity<Object> handleUserForbidden(UserForbiddenException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new CustomErrorBody(ex.getMessage(), now()));
    }

    @ExceptionHandler(value = JwtAuthenticationException.class)
    protected ResponseEntity<Object> handleJwtAuthException(JwtAuthenticationException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new CustomErrorBody(ex.getMessage(), now()));
    }

    @ExceptionHandler(value = HeaderAbsentException.class)
    protected ResponseEntity<Object> handleAbsentHeader(HeaderAbsentException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new CustomErrorBody(ex.getMessage(), now()));
    }

    @ExceptionHandler(value = UsernameNotFoundException.class)
    protected ResponseEntity<Object> handleUserNotFound(UsernameNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new CustomErrorBody(ex.getMessage(), now()));
    }
}
