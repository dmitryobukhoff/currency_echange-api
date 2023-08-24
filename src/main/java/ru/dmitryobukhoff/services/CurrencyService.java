package ru.dmitryobukhoff.services;

import com.google.gson.Gson;
import ru.dmitryobukhoff.models.Currency;
import ru.dmitryobukhoff.repositories.CurrencyRepositoryImpl;
import ru.dmitryobukhoff.utils.Output;
import ru.dmitryobukhoff.validators.Validator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;

public class CurrencyService {
    private static CurrencyRepositoryImpl currencyRepository = new CurrencyRepositoryImpl();

    public void getCurrencies(HttpServletResponse response) throws IOException {
        List<Currency> currencies = currencyRepository.findAll();
        Gson gson = new Gson();
        PrintWriter printWriter = response.getWriter();
        printWriter.println(gson.toJson(currencies));
        printWriter.close();
    }

    public void getCurrency(HttpServletRequest request, HttpServletResponse response) throws IOException{
        String url = request.getPathInfo();
        if(url == null){
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Отсутствует код валюты");
            return;
        }
        String code = url.substring(1);
        if(code.isEmpty()){
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Валюта с таким кодом не найдена");
            return;
        }
        Optional<Currency> currency = currencyRepository.findCurrencyByCode(code);
        if(currency.isEmpty()){
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Валюта с таким кодом не найдена");
            return;
        }
        PrintWriter printWriter = response.getWriter();
        Output.print(printWriter, currency.get());
        printWriter.close();
    }

    public void addCurrency(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("name");
        String code = request.getParameter("code");
        String sign = request.getParameter("sign");
        if(!Validator.isValid(name, code, sign)){
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Отсутствие нужного поля формы.");
            return;
        }
        Optional<Currency> currency = currencyRepository.findCurrencyByCode(code);
        if(currency.isPresent()){
            response.sendError(HttpServletResponse.SC_CONFLICT, "Такая валюта уже существует.");
            return;
        }
        currencyRepository.create(new Currency(name, code, sign));
    }

}
