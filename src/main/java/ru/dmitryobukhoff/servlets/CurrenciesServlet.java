package ru.dmitryobukhoff.servlets;

import com.google.gson.Gson;
import ru.dmitryobukhoff.models.Currency;
import ru.dmitryobukhoff.repositories.CurrencyRepositoryImpl;
import ru.dmitryobukhoff.services.CurrencyService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "currencyServlet", value = "/currencies")
public class CurrencyServlet extends HttpServlet{

    private CurrencyService currencyService;

    @Override
    public void init() throws ServletException {
        currencyService = new CurrencyService();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        currencyService.getCurrencies(response);
        super.doGet(request, response);
    }
}
