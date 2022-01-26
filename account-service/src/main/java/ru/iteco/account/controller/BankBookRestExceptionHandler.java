package ru.iteco.account.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.iteco.account.model.exception.BankBookNotFoundException;
import ru.iteco.account.model.exception.BankBookNumberCannotBeModifiedException;
import ru.iteco.account.model.exception.BankBookWithCurrencyAlreadyHaveException;
import ru.iteco.account.model.dto.ErrorDto;

@RestControllerAdvice
public class BankBookRestExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(BankBookNotFoundException.class)
    public ErrorDto handleBankBookNotFoundException(BankBookNotFoundException exception) {
        return new ErrorDto(HttpStatus.NOT_FOUND.name(), exception.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BankBookNumberCannotBeModifiedException.class)
    public ErrorDto handleBankBookNumberCannotBeModifiedException(BankBookNumberCannotBeModifiedException exception) {
        return new ErrorDto(HttpStatus.BAD_REQUEST.name(), exception.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BankBookWithCurrencyAlreadyHaveException.class)
    public ErrorDto handleBankBookWithCurrencyAlreadyHaveException(BankBookWithCurrencyAlreadyHaveException exception) {
        return new ErrorDto(HttpStatus.BAD_REQUEST.name(), exception.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingPathVariableException.class)
    public ErrorDto handleMissingPathVariableException(MissingPathVariableException exception) {
        return new ErrorDto(HttpStatus.BAD_REQUEST.name(), exception.getMessage());
    }

}
