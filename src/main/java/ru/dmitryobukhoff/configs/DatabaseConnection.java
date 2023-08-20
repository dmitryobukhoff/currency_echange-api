package ru.dmitryobukhoff.configs;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {
    private static DatabaseConnection databaseConnection;
    private Connection connection;

    private DatabaseConnection(){
        try {
            FileInputStream fileInputStream = new FileInputStream("D:\\Java\\Projects\\currency_exchange\\src\\main\\webapp\\WEB-INF\\properties\\connection.properties");
            Properties properties = new Properties();
            properties.load(fileInputStream);
            String driver = properties.getProperty("connection.driver");
            String url = properties.getProperty("connection.string");
            String user = properties.getProperty("connection.user");
            String password = properties.getProperty("connection.password");
            Class.forName(driver);
            properties.clone();
            fileInputStream.close();
            connection = DriverManager.getConnection(url, user, password);
        } catch (IOException | ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static DatabaseConnection getInstance(){
        if(databaseConnection == null)
            databaseConnection = new DatabaseConnection();
        return databaseConnection;
    }

    public Connection getConnection(){
        return connection;
    }
}
