package ru.iteco.account.service;

import ru.iteco.account.model.AllCurrencyExchange;
import ru.iteco.account.model.ConvertResult;
import ru.iteco.account.model.ConverterRequest;

public interface CurrencyServiceApi {

    AllCurrencyExchange getAllExchange();

    ConvertResult convert(ConverterRequest request);

}
