package ru.iteco.account.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.iteco.account.model.dto.ErrorDto;
import ru.iteco.account.model.exception.UserNotFoundException;

@RestControllerAdvice
public class UserRestExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorDto> handleUserNotFoundException(UserNotFoundException exception) {
        ErrorDto errorDto = new ErrorDto("NOT_FOUND", exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDto);
    }

}
