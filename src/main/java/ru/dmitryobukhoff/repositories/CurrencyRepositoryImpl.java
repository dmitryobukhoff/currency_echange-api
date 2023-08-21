package ru.dmitryobukhoff.repositories;

import ru.dmitryobukhoff.configs.DatabaseConnection;
import ru.dmitryobukhoff.models.Currency;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CurrencyRepositoryImpl implements CurrencyRepository{

    @Override
    public void create(Currency currency) {
        String query = "INSERT INTO currency_exchange.currencies(code, full_name, sign) VALUES (?, ?, ?)";
        try{
            Connection connection = DatabaseConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, currency.getCode());
            preparedStatement.setString(2, currency.getName());
            preparedStatement.setString(3, currency.getSign());
            preparedStatement.execute();
            preparedStatement.close();
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Currency read(Long id) {
        String query = "SELECT * FROM currency_exchanger.currencies WHERE id = " + id;
        Currency currency = new Currency();
        try{
            Connection connection = DatabaseConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            currency.setId(id);
            currency.setCode(resultSet.getString("code"));
            currency.setName(resultSet.getString("full_name"));
            currency.setSign(resultSet.getString("sign"));
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return currency;
    }

    @Override
    public void update(Currency currency) {

    }

    @Override
    public void delete(Currency currency) {

    }

    @Override
    public List<Currency> findAll() {
        List<Currency> currencies = new ArrayList<>();
        String query = "SELECT * FROM currency_exchanger.currencies";
        try{
            Connection connection = DatabaseConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                Currency currency = new Currency();
                currency.setId(resultSet.getLong("id"));
                currency.setName(resultSet.getString("full_name"));
                currency.setCode(resultSet.getString("code"));
                currency.setSign(resultSet.getString("sign"));
                currencies.add(currency);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return currencies;
    }

    @Override
    public Optional<Currency> findCurrencyByCode(String code) {
        String query = "SELECT * FROM currency_exchanger.currencies  WHERE code = '" + code + "'";
        Currency currency = null;
        try{
            Connection connection = DatabaseConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            if(resultSet.next()){
                currency = new Currency();
                currency.setId(resultSet.getLong("id"));
                currency.setName(resultSet.getString("full_name"));
                currency.setCode(resultSet.getString("code"));
                currency.setSign(resultSet.getString("sign"));
            }
            statement.close();
            resultSet.close();
        }catch (SQLException  e){
            throw new RuntimeException(e);
        }
        return Optional.ofNullable(currency);
    }
}
