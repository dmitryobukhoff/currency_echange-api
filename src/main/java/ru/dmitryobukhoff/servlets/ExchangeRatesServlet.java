package ru.dmitryobukhoff.servlets;

import ru.dmitryobukhoff.services.ExchangeRateService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "exchangeRates",value = "/exchangeRates")
public class ExchangeRatesServlet extends HttpServlet {

    private ExchangeRateService exchangeRateService;

    @Override
    public void init() throws ServletException {
        exchangeRateService = new ExchangeRateService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        exchangeRateService.getExchangeRates(response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
