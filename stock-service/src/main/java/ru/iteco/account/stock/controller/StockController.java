package ru.iteco.account.stock.controller;

import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import ru.iteco.account.stock.model.HistoricalQuotesRequest;
import ru.iteco.account.stock.model.HistoricalQuotesResponse;
import ru.iteco.account.stock.model.StockQuotes;
import ru.iteco.account.stock.service.StockServiceApi;

@RestController
@RequiredArgsConstructor
@Slf4j
public class StockController {

    private final StockServiceApi stockServiceApi;

    @PostMapping(value = "/get-stock-quotes", produces = "application/json", consumes = "application/x-www-form-urlencoded")
    public StockQuotes getStockQuotes(@RequestBody String tickets,
                                      @RequestHeader Map<String, String> headers) {
        log.info("headers: {}", headers);
        log.info("body: {}", tickets);
        tickets = tickets.substring(5);
        List<String> ticket = List.of(tickets.split(","));
        return stockServiceApi.getStockQuotesByTicket(ticket);
    }

    @PostMapping("/get-historical-quotes")
    public HistoricalQuotesResponse getStockQuotes(@RequestBody HistoricalQuotesRequest request) {
        return stockServiceApi.getHistoricalQuotes(request);
    }

}