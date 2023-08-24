package ru.dmitryobukhoff.servlets;

import ru.dmitryobukhoff.services.ExchangeService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "exchangeServlet", value = "/exchange")
public class ExchangeServlet extends HttpServlet {

    private ExchangeService exchangeService;

    @Override
    public void init() throws ServletException {
        exchangeService = new ExchangeService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        exchangeService.getExchange(request, response);
    }
}
