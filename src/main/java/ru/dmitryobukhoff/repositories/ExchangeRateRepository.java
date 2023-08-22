package ru.dmitryobukhoff.repositories;

import ru.dmitryobukhoff.models.ExchangeRate;

import java.util.List;
import java.util.Optional;

public interface ExchangeRateRepository {
    void create(ExchangeRate exchangeRate);
    ExchangeRate read(Long id);
    void update(ExchangeRate exchangeRate);
    void delete(ExchangeRate exchangeRate);

    List<ExchangeRate> findAll();
    Optional<ExchangeRate> findRateByCodes(String base, String target);
}
