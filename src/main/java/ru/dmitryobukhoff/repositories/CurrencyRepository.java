package ru.dmitryobukhoff.repositories;

import ru.dmitryobukhoff.models.Currency;

import java.util.List;

public interface CurrencyRepository {
    void create(Currency currency);
    Currency read(Long id);
    void update(Currency currency);
    void delete(Currency currency);

    List<Currency> findAll();
}
