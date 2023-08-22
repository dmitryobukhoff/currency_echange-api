package ru.dmitryobukhoff.dtos;

import ru.dmitryobukhoff.models.Currency;

import java.math.BigDecimal;

public record ExchangeCurrencyDTO (
    Currency current,
    Currency target,
    BigDecimal rate,
    BigDecimal amount,
    BigDecimal convertedAmount
){}
