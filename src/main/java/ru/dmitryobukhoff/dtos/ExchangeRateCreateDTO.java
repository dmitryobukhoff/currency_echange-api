package ru.dmitryobukhoff.dtos;

import java.math.BigDecimal;

public record ExchangeRateCreateDTO(
        String base,
        String target,
        BigDecimal rate
) {
}
