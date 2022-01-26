package ru.iteco.account.currency.service;

import ru.iteco.account.currency.model.AllCurrencyExchange;
import ru.iteco.account.currency.model.ConvertResult;
import ru.iteco.account.currency.model.ConverterRequest;

public interface ExchangeApi {

     ConvertResult convert(ConverterRequest request);

     AllCurrencyExchange getAllCurrencyExchange();

}
