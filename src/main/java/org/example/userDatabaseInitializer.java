package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class userDatabaseInitializer{
    private static final String DB_URL = "jdbc:sqlite:User.db";

    public void userInitializeDatabase(){
        try (Connection connection = DriverManager.getConnection(DB_URL);
             Statement statement = connection.createStatement()) {

            String createTableQuery = "CREATE TABLE IF NOT EXISTS users (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "username TEXT NOT NULL," +
                    "password TEXT NOT NULL," +
                    "phone TEXT NOT NULL"+
                    ")";
            statement.executeUpdate(createTableQuery);

            String insertDataQuery = "INSERT INTO users (username, password, phone) VALUES " +
                    "('user1', 'password1','18996891999')," +
                    "('user2', 'password2','15387991123')";
            statement.executeUpdate(insertDataQuery);

            System.out.println("用户数据库初始化成功!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}