package ru.iteco.account.currency.controller;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import ru.iteco.account.currency.model.AllCurrencyExchange;
import ru.iteco.account.currency.model.ConvertResult;
import ru.iteco.account.currency.model.ConverterRequest;
import ru.iteco.account.currency.service.ExchangeApi;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CurrencyController {

    private final ExchangeApi exchangeApi;

    @PostMapping("/convert")
    public ConvertResult convertAmount(@RequestBody ConverterRequest converterRequest,
                                       @RequestHeader Map<String, String> headers) {
        log.info("Request with headers: {}", headers);
        return exchangeApi.convert(converterRequest);
    }

    @GetMapping("/all-exchange")
    public AllCurrencyExchange getAllExchange(@RequestHeader Map<String, String> headers,
                                              @AuthenticationPrincipal Jwt jwt) {
        log.info("Request with headers: {}", headers);
        log.info("JWT: {}", jwt.getClaims());
        return exchangeApi.getAllCurrencyExchange();
    }

}
