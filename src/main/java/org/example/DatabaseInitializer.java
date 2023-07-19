package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer{
    private static final String DB_URL = "jdbc:sqlite:Data.db";

    public void main() {
        try (Connection connection = DriverManager.getConnection(DB_URL)) {
            productInitializeDatabase(connection);
            cartInitializeDatabase(connection);
            shoppingHistoryInitializeDatabase(connection);
            
            System.out.println("数据库初始化成功！");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void productInitializeDatabase(Connection connection) throws SQLException {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS products (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "productname TEXT NOT NULL," +
                    "price TEXT NOT NULL," +
                    "number TEXT NOT NULL"+
                    ")";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(createTableQuery);
        }
    }

    public void cartInitializeDatabase(Connection connection) throws SQLException {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS cart (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "productname TEXT NOT NULL," +
                    "number TEXT NOT NULL"+
                    ")";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(createTableQuery);
        }
    }

    public void shoppingHistoryInitializeDatabase(Connection connection) throws SQLException {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS shoppinghistory (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "productname TEXT," +
                    "number TEXT"+
                    ")";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(createTableQuery);
        }
    }
}