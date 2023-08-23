package ru.dmitryobukhoff.services;

import com.google.gson.Gson;
import ru.dmitryobukhoff.dtos.ExchangeRateCreateDTO;
import ru.dmitryobukhoff.models.ExchangeRate;
import ru.dmitryobukhoff.repositories.ExchangeRateRepositoryImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
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

    public void addExchangeRate(HttpServletRequest request, HttpServletResponse response) throws IOException{
        String base = request.getParameter("baseCurrencyCode");
        String target = request.getParameter("targetCurrencyCode");
        String rate = request.getParameter("rate");
        if(!isValid(base, target, rate)){
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Отсутствует нужное поле формы.");
            return;
        }
        Optional<ExchangeRate> exchangeRate = exchangeRateRepository.findRateByCodes(base, target);
        if(exchangeRate.isPresent()){
            response.sendError(HttpServletResponse.SC_CONFLICT, "Валютная пара с таким кодом уже существует.");
            return;
        }
        exchangeRateRepository.create(new ExchangeRateCreateDTO(base, target, new BigDecimal(rate)));
    }

    private boolean isValid(String base, String target, String rate){
        return (base != null && target != null && rate != null && (!base.isEmpty()) && (!target.isEmpty()) && (!rate.isEmpty()));
    }

    public void updateExchangeRate(HttpServletRequest request, HttpServletResponse response) throws IOException{
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
        BigDecimal rate = new BigDecimal(request.getParameter("rate"));
        ExchangeRate exchangeRate1 = exchangeRate.get();
        exchangeRate1.setRate(rate);
        exchangeRateRepository.update(exchangeRate1);
    }
}
