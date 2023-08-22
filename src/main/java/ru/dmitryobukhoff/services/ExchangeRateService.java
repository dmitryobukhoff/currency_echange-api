package ru.dmitryobukhoff.services;

import com.google.gson.Gson;
import ru.dmitryobukhoff.models.ExchangeRate;
import ru.dmitryobukhoff.repositories.CurrencyRepositoryImpl;
import ru.dmitryobukhoff.repositories.ExchangeRateRepositoryImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;

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

    public void getExchangeRate(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String url = request.getPathInfo();
        if(url == null || url.length() == 1){
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Коды валютных пар отсутствуют в запросе.");
            return;
        }else if(url.length() < 7){
            response.sendError(HttpServletResponse.SC_LENGTH_REQUIRED, "Коды валютных пар неверны");
            return;
        }
        String base = url.substring(1,4);
        String target = url.substring(4);
        Optional<ExchangeRate> exchangeRate = exchangeRateRepository.findRateByCodes(base, target);
        if(exchangeRate.isEmpty()){
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Обменный курс для пары не найден.");
            return;
        }
        Gson gson = new Gson();
        PrintWriter printWriter = response.getWriter();
        printWriter.println(gson.toJson(exchangeRate));
    }
}
