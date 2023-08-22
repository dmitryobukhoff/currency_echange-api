package ru.dmitryobukhoff.repositories;

import ru.dmitryobukhoff.configs.DatabaseConnection;
import ru.dmitryobukhoff.models.Currency;
import ru.dmitryobukhoff.models.ExchangeRate;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ExchangeRateRepositoryImpl implements ExchangeRateRepository{

    @Override
    public void create(ExchangeRate exchangeRate) {

    }

    @Override
    public ExchangeRate read(Long id) {
        return null;
    }

    @Override
    public void update(ExchangeRate exchangeRate) {

    }

    @Override
    public void delete(ExchangeRate exchangeRate) {

    }

    @Override
    public List<ExchangeRate> findAll() {
        String query = "select exchange_rates.id as er_id,\n" +
                "\tbase.id as base_id,\n" +
                "\tbase.full_name as base_name,\n" +
                "\tbase.code as base_code,\n" +
                "\tbase.sign as base_sign,\n" +
                "\ttarget.id as target_id,\n" +
                "\ttarget.full_name as target_name,\n" +
                "\ttarget.code as target_code,\n" +
                "\ttarget.sign as target_sign,\n" +
                "\texchange_rates.rate\n" +
                "from currency_exchanger.exchange_rates exchange_rates\t\n" +
                "\tjoin currency_exchanger.currencies base on base.id = exchange_rates.base_currency_id\n" +
                "\tjoin currency_exchanger.currencies target on target.id = exchange_rates.target_currency_id";
        List<ExchangeRate> exchangeRates = new ArrayList<>();
        try{
            Connection connection = DatabaseConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                Currency base = new Currency();
                Currency target = new Currency();
                ExchangeRate exchangeRate = new ExchangeRate();
                exchangeRate.setId(resultSet.getLong("er_id"));
                base.setId(resultSet.getLong("base_id"));
                base.setName(resultSet.getString("base_name"));
                base.setCode(resultSet.getString("base_code"));
                base.setSign(resultSet.getString("base_sign"));
                target.setId(resultSet.getLong("target_id"));
                target.setName(resultSet.getString("target_name"));
                target.setCode(resultSet.getString("target_code"));
                target.setSign(resultSet.getString("target_sign"));
                exchangeRate.setRate(resultSet.getBigDecimal("rate"));
                exchangeRate.setBase(base);
                exchangeRate.setTarget(target);
                exchangeRates.add(exchangeRate);
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return exchangeRates;
    }

    @Override
    public Optional<ExchangeRate> findRateByCodes(String currentCode, String targetCode) {
        return Optional.empty();
    }
}
