package ru.dmitryobukhoff.services;

import com.google.gson.Gson;
import ru.dmitryobukhoff.models.Currency;
import ru.dmitryobukhoff.repositories.CurrencyRepositoryImpl;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class CurrencyService {
    private static CurrencyRepositoryImpl currencyRepository = new CurrencyRepositoryImpl();

    public void getCurrencies(HttpServletResponse response) throws IOException {
        List<Currency> currencies = currencyRepository.findAll();
        Gson gson = new Gson();
        PrintWriter printWriter = response.getWriter();
        printWriter.println(gson.toJson(currencies));
        printWriter.close();
    }
}
