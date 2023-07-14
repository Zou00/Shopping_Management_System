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

            String createTableQuery = "CREATE TABLE IF NOT EXISTS users ( id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, password TEXT, phone TEXT)";
            statement.executeUpdate(createTableQuery);

            System.out.println("用户数据库初始化成功!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}