package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class userDatabaseInitializer {
    private static final String DB_URL = "jdbc:sqlite:User.db";

    public void userInitializeDatabase(){
        try (Connection connection = DriverManager.getConnection(DB_URL);
             Statement statement = connection.createStatement()) {

            // 创建 admins 表
            String createTableQuery = "CREATE TABLE IF NOT EXISTS users (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "username TEXT NOT NULL," +
                    "password TEXT NOT NULL" +
                    ")";
            statement.executeUpdate(createTableQuery);

            // 向 admins 表中插入数据
            String insertDataQuery = "INSERT INTO users (username, password) VALUES " +
                    "('user1', 'password1')," +
                    "('user2', 'password2')";
            statement.executeUpdate(insertDataQuery);

            System.out.println("用户数据库初始化成功");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
