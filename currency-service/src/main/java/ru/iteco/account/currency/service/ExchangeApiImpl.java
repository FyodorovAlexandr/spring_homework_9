package ru.iteco.account.currency.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.iteco.account.currency.model.AllCurrencyExchange;
import ru.iteco.account.currency.model.ConvertResult;
import ru.iteco.account.currency.model.ConverterRequest;

import java.math.BigDecimal;

@Service
class ExchangeApiImpl implements ExchangeApi {

    private final RestTemplate restTemplate;
    private final String token;
    private final String urlConvert;
    private final String urlAllExchange;

    public ExchangeApiImpl(@Qualifier("restTemplateExchange") RestTemplate restTemplate,
                           @Value("${currency.token}") String token,
                           @Value("${currency.url.convert}") String urlConvert,
                           @Value("${currency.url.all-exchange}") String urlAllExchange) {
        this.restTemplate = restTemplate;
        this.token = token;
        this.urlConvert = urlConvert;
        this.urlAllExchange = urlAllExchange;
    }

    @Override
    public ConvertResult convert(ConverterRequest request) {
////        ResponseEntity<ConvertResult> responseEntity = restTemplate.getForEntity(
////                String.format(urlConvert, token, request.getFrom(), request.getTo(), request.getAmount().toString()),
////                ConvertResult.class);
//        return responseEntity.getBody();
        return ConvertResult.builder()
                .result(new BigDecimal(10))
                .query(
                        ConverterRequest.builder()
                                .from(request.getFrom())
                                .to(request.getTo())
                                .amount(request.getAmount())
                                .build()
                )
                .build();
    }

    @Override
    public AllCurrencyExchange getAllCurrencyExchange() {
        ResponseEntity<AllCurrencyExchange> responseEntity = restTemplate.getForEntity(
                String.format(urlAllExchange, token),
                AllCurrencyExchange.class);
        return responseEntity.getBody();
    }

}
