package ru.iteco.account.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ConvertResult {

    private ConverterRequest query;
    private BigDecimal result;

}
