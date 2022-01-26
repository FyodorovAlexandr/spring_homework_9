package ru.iteco.account.service;

import ru.iteco.account.model.dto.BankBookDto;
import ru.iteco.account.model.dto.UserDto;

import java.math.BigDecimal;

public interface TransactionService {

    Boolean transferBetweenBankBook(BankBookDto sourceBankBook, BankBookDto targetBankBook, BigDecimal amount);
    Boolean transferBetweenUser(UserDto sourceUser, UserDto targetUser, BigDecimal amount, String currency);

}
