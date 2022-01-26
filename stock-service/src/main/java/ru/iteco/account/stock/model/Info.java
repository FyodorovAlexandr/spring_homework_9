package ru.iteco.account.stock.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Info {

    private String ticker;
    private String name;
    private String currency;
    private BigDecimal price;

}
