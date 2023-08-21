package ru.dmitryobukhoff.servlets;

import ru.dmitryobukhoff.services.CurrencyService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "currencyServlet", value = "/currency/*")
public class CurrencyServlet extends HttpServlet {

    private CurrencyService currencyService;

    @Override
    public void init() throws ServletException {
        currencyService = new CurrencyService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        currencyService.getCurrency(request, response);
    }
}
