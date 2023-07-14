package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;

public class DatabaseInitializer{
    private static final String DB_URL = "jdbc:sqlite:Data.db";

    public static void main() {
        try (Connection connection = DriverManager.getConnection(DB_URL)) {
            productInitializeDatabase(connection);
            insertInitialData(connection);
            cartInitializeDatabase(connection);
            
            System.out.println("数据库初始化成功！");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void productInitializeDatabase(Connection connection) throws SQLException {
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

    public static void cartInitializeDatabase(Connection connection) throws SQLException {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS cart (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "productname TEXT NOT NULL," +
                    "number TEXT NOT NULL"+
                    ")";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(createTableQuery);
        }
    }

    public static void shoppingHistoryInitializeDatabase(Connection connection) throws SQLException {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS history (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "productname TEXT NOT NULL," +
                    "number TEXT NOT NULL"+
                    ")";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(createTableQuery);
        }
    }

    public static void insertInitialData(Connection connection) throws SQLException {
        String query = "INSERT INTO products (productname, price, number) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, "果冻");
            statement.setDouble(2, 3.99);
            statement.setInt(3, 100);
            statement.executeUpdate();
            
            statement.setString(1, "薯片");
            statement.setDouble(2, 6.50);
            statement.setInt(3, 30);
            statement.executeUpdate();

            statement.setString(1, "巧克力");
            statement.setDouble(2, 7.00);
            statement.setInt(3, 20);
            statement.executeUpdate();
        }
    }
}