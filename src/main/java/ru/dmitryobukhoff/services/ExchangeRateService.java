package ru.dmitryobukhoff.services;

import ru.dmitryobukhoff.dtos.ExchangeRateCreateDTO;
import ru.dmitryobukhoff.models.ExchangeRate;
import ru.dmitryobukhoff.repositories.ExchangeRateRepositoryImpl;
import ru.dmitryobukhoff.utils.Output;
import ru.dmitryobukhoff.validators.Validator;

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
        if(!exchangeRates.isEmpty())
            Output.print(printWriter, exchangeRates);
        else
            Output.print(printWriter, "Список обменных курсо пуст.");
        printWriter.close();
    }
    public void getExchangeRate(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String url = request.getPathInfo();
        if(Validator.isUrlEmpty(url)){
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Коды валютных пар отсутствуют в запросе.");
            return;
        }else if(Validator.hasUrlValidCodes(url)){
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
        PrintWriter printWriter = response.getWriter();
        Output.print(printWriter, exchangeRate.get());
        printWriter.close();
    }
    public void addExchangeRate(HttpServletRequest request, HttpServletResponse response) throws IOException{
        String base = request.getParameter("baseCurrencyCode");
        String target = request.getParameter("targetCurrencyCode");
        String rate = request.getParameter("rate");
        if(!Validator.isValid(base, target, rate)){
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
    public void updateExchangeRate(HttpServletRequest request, HttpServletResponse response) throws IOException{
        String url = request.getPathInfo();
        if(Validator.isUrlEmpty(url)){
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Коды валютных пар отсутствуют в запросе.");
            return;
        }else if(Validator.hasUrlValidCodes(url)){
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
