package ru.dmitryobukhoff.services;

import com.google.gson.Gson;
import ru.dmitryobukhoff.models.ExchangeRate;
import ru.dmitryobukhoff.repositories.ExchangeRateRepositoryImpl;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class ExchangeRateService {

    private ExchangeRateRepositoryImpl exchangeRateRepository = new ExchangeRateRepositoryImpl();

    public void getExchangeRates(HttpServletResponse response) throws IOException {
        List<ExchangeRate> exchangeRates = exchangeRateRepository.findAll();
        PrintWriter printWriter = response.getWriter();
        Gson gson = new Gson();
        if(!exchangeRates.isEmpty()){
            printWriter.println(gson.toJson(exchangeRates));
        }else{
            printWriter.println(gson.toJson("Список обменника пуст!"));
        }
        printWriter.close();
    }
}
