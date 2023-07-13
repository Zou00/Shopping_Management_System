package org.example;

import java.sql.*;
import java.util.Scanner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User{
    private static final String DB_URL = "jdbc:sqlite:User.db";
    private static final String DB_USER = "";
    private static final String DB_PASSWORD = "";

    private Connection connection;
    private Statement statement;
    private Scanner scanner;

    public User(){
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            statement = connection.createStatement();
            scanner = new Scanner(System.in);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void resetUserPassword(){
        System.out.print("请输入要重置密码的用户名：");
        String username=scanner.next();
        try {
            if (!userExists(username)) {
                System.out.println("用户不存在!");
                return;
            }
        
        System.out.print("请重置密码：");
        String password=scanner.next();

        String updateQuery = "UPDATE users SET password = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
        preparedStatement.setString(1, password);
        preparedStatement.executeUpdate();

        System.out.println("用户密码重置成功!");

        preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean userExists(String username) throws SQLException {
        String query = "SELECT * FROM users WHERE username = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, username);
        ResultSet resultSet = preparedStatement.executeQuery();
        boolean exists = resultSet.next();
        preparedStatement.close();
        return exists;
    }


    //注册
    public boolean register(String Name,String passWord){
        return true;
    }

    //登录
    public boolean login(String Name,String passWord){
        return true;
    }

    //修改密码
    public void changePassword(String password){

    }

    //重置密码
    public void resetPassword(){
        
    }


}
