package ru.iteco.account.controller;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.iteco.account.model.dto.ErrorDto;

import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ValidationExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorDto handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        String errorMessage = exception.getBindingResult().getAllErrors().stream().map(objectError -> {
            String field = ((FieldError) objectError).getField();
            return field + ": " + objectError.getDefaultMessage();
        }).collect(Collectors.joining("; "));
        return ErrorDto.builder().status(HttpStatus.BAD_REQUEST.name()).message(errorMessage).build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ErrorDto handleConstraintViolationException(ConstraintViolationException exception) {
        return ErrorDto.builder().status(HttpStatus.BAD_REQUEST.name()).message(exception.getMessage()).build();
    }

}
