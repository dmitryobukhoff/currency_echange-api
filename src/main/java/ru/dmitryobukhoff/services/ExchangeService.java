package ru.dmitryobukhoff.services;

import ru.dmitryobukhoff.dtos.ExchangeCurrencyDTO;
import ru.dmitryobukhoff.models.Currency;
import ru.dmitryobukhoff.models.ExchangeRate;
import ru.dmitryobukhoff.repositories.ExchangeRateRepositoryImpl;
import ru.dmitryobukhoff.utils.Output;
import ru.dmitryobukhoff.validators.Validator;

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
        if(!Validator.isValid(base, target, amount)){
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Отсутствует нужное поле формы.");
            return;
        }
        Optional<ExchangeRate> exchangeRate = exchangeRateRepository.findRateByCodes(base, target);
        PrintWriter printWriter = response.getWriter();
        if(exchangeRate.isPresent()){
            BigDecimal baseAmount = new BigDecimal(Double.parseDouble(amount));
            BigDecimal convertedAmount = baseAmount.multiply(exchangeRate.get().getRate());
            Output.print(printWriter, make(exchangeRate.get().getBase(), exchangeRate.get().getTarget(), exchangeRate.get().getRate(),
                    baseAmount, convertedAmount));
        }else{
            Optional<ExchangeRate> exchangeRateReversed = exchangeRateRepository.findRateByCodes(target, base);
            if(exchangeRateReversed.isPresent()){
                BigDecimal rate = BigDecimal.ONE.divide(new BigDecimal(exchangeRateReversed.get().getRate().doubleValue()), MathContext.DECIMAL128);
                BigDecimal baseAmount = new BigDecimal(Double.parseDouble(amount));
                BigDecimal convertedAmount = baseAmount.multiply(rate);
                Output.print(printWriter, make(exchangeRateReversed.get().getTarget(), exchangeRateReversed.get().getBase(),
                        rate, baseAmount, convertedAmount));
            }else{
                Optional<ExchangeRate> exchangeRateBaseUSD = exchangeRateRepository.findRateByCodes(base, "USD");
                Optional<ExchangeRate> exchangeRateTargetUSD = exchangeRateRepository.findRateByCodes("USD", target);
                if(exchangeRateBaseUSD.isPresent() && exchangeRateTargetUSD.isPresent()){
                    BigDecimal rate1 = exchangeRateBaseUSD.get().getRate();
                    BigDecimal rate2 = exchangeRateTargetUSD.get().getRate();
                    BigDecimal baseAmount = new BigDecimal(Double.parseDouble(amount));
                    BigDecimal convertedAmount = baseAmount.multiply(rate1).multiply(rate2);
                    Output.print(printWriter, make(exchangeRateBaseUSD.get().getBase(), exchangeRateBaseUSD.get().getTarget(),
                            rate1.multiply(rate2), baseAmount, convertedAmount));
                }else{
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Обмен валют не может быть осуществлён");
                    return;
                }
            }
        }
        printWriter.close();
    }

    private ExchangeCurrencyDTO make(Currency baseCurrency, Currency targetCurrency, BigDecimal rate, BigDecimal baseAmount, BigDecimal convertedAmount){
        return new ExchangeCurrencyDTO(baseCurrency, targetCurrency, rate, baseAmount, convertedAmount);
    }
}
