package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class adminDatabaseInitializer{
    private static final String DB_URL = "jdbc:sqlite:Admin.db";

    public void adminInitializeDatabase(){
        try (Connection connection = DriverManager.getConnection(DB_URL);
             Statement statement = connection.createStatement()) {

            String createTableQuery = "CREATE TABLE IF NOT EXISTS admins (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "username TEXT NOT NULL," +
                    "password TEXT NOT NULL" +
                    ")";
            statement.executeUpdate(createTableQuery);

            String insertDataQuery = "INSERT INTO admins (username, password) VALUES " +
                    "('admin1', 'password1')," +
                    "('admin2', 'password2')";
            statement.executeUpdate(insertDataQuery);

            System.out.println("管理员数据库初始化成功!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}