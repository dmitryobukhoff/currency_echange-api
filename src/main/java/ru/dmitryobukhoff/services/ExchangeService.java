package ru.dmitryobukhoff.services;

import com.google.gson.Gson;
import ru.dmitryobukhoff.dtos.ExchangeCurrencyDTO;
import ru.dmitryobukhoff.models.Currency;
import ru.dmitryobukhoff.models.ExchangeRate;
import ru.dmitryobukhoff.repositories.ExchangeRateRepositoryImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Optional;

public class ExchangeService {

    private ExchangeRateRepositoryImpl exchangeRateRepository = new ExchangeRateRepositoryImpl();
    public void getExchange(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String base = request.getParameter("from");
        String target = request.getParameter("to");
        String amount = request.getParameter("amount");
        if(!isValid(base, target, amount)){
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Отсутствует нужное поле формы.");
            return;
        }
        Optional<ExchangeRate> exchangeRate = exchangeRateRepository.findRateByCodes(base, target);
        if(exchangeRate.isPresent()){
            Currency baseCurrency = exchangeRate.get().getBase();
            Currency targetCurrency = exchangeRate.get().getTarget();
            BigDecimal rate = exchangeRate.get().getRate();
            BigDecimal baseAmount = new BigDecimal(Double.parseDouble(amount));
            BigDecimal convertedAmount = baseAmount.multiply(rate);
            Gson gson = new Gson();
            PrintWriter printWriter = response.getWriter();
            printWriter.println(gson.toJson(new ExchangeCurrencyDTO(baseCurrency, targetCurrency, rate, baseAmount, convertedAmount)));
            printWriter.close();
        }else{
            Optional<ExchangeRate> exchangeRateReversed = exchangeRateRepository.findRateByCodes(target, base);
            if(exchangeRateReversed.isPresent()){
                Currency baseCurrency = exchangeRateReversed.get().getTarget();
                Currency targetCurrency = exchangeRateReversed.get().getBase();
                BigDecimal rate = BigDecimal.ONE.divide(new BigDecimal(exchangeRateReversed.get().getRate().doubleValue()), MathContext.DECIMAL128);
                BigDecimal baseAmount = new BigDecimal(Double.parseDouble(amount));
                BigDecimal convertedAmount = baseAmount.multiply(rate);
                Gson gson = new Gson();
                PrintWriter printWriter = response.getWriter();
                printWriter.println(gson.toJson(new ExchangeCurrencyDTO(baseCurrency, targetCurrency, rate, baseAmount, convertedAmount)));
                printWriter.close();
            }else{
                Optional<ExchangeRate> exchangeRateBaseUSD = exchangeRateRepository.findRateByCodes(base, "USD");
                Optional<ExchangeRate> exchangeRateTargetUSD = exchangeRateRepository.findRateByCodes("USD", target);
                if(exchangeRateBaseUSD.isPresent() && exchangeRateTargetUSD.isPresent()){
                    Currency baseCurrency = exchangeRateBaseUSD.get().getBase();
                    Currency targetCurrency = exchangeRateTargetUSD.get().getTarget();
                    BigDecimal rate1 = exchangeRateBaseUSD.get().getRate();
                    BigDecimal rate2 = exchangeRateTargetUSD.get().getRate();
                    BigDecimal baseAmount = new BigDecimal(Double.parseDouble(amount));
                    BigDecimal convertedAmount = baseAmount.multiply(rate1).multiply(rate2);
                    Gson gson = new Gson();
                    PrintWriter printWriter = response.getWriter();
                    printWriter.println(gson.toJson(new ExchangeCurrencyDTO(baseCurrency, targetCurrency, rate1.multiply(rate2), baseAmount, convertedAmount)));
                    printWriter.close();
                }else{
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Обмен валют не может быть осуществлён");
                    return;
                }
            }
        }
    }

    private boolean isValid(String base, String target, String amount){
        return (base != null && target != null && amount != null && (!base.isEmpty()) && (!target.isEmpty()) && (!amount.isEmpty()));
    }
}
