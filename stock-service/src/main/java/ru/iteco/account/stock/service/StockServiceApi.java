package ru.iteco.account.stock.service;

import ru.iteco.account.stock.model.HistoricalQuotesRequest;
import ru.iteco.account.stock.model.HistoricalQuotesResponse;
import ru.iteco.account.stock.model.StockQuotes;
import java.util.List;

public interface StockServiceApi {

    StockQuotes getStockQuotesByTicket(List<String> tickets);

    HistoricalQuotesResponse getHistoricalQuotes(HistoricalQuotesRequest request);

}
