package org.example;

import java.sql.*;
import java.util.Scanner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Admin{
    private static final String DB_URL = "jdbc:sqlite:Admin.db";

    private Connection connection;
    private Statement statement;
    private Scanner scanner;

    public Admin(){
        try {
            connection = DriverManager.getConnection(DB_URL);
            statement = connection.createStatement();
            scanner = new Scanner(System.in);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

   
    //管理员登录
    public void login(){
        System.out.println("\n---------管理员登录---------");
        System.out.print("用户名：");
        String adminname = scanner.next();
        System.out.print("密码：");
        String password = scanner.next();

        // 查询数据库验证用户名和密码
        String query = "SELECT * FROM admins WHERE username = ? AND password = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, adminname);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                System.out.println("登录成功!");
                Main.adminMenu();
            } else {
                System.out.println("用户名或密码错误！");
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //管理员修改自身密码
    public void changePassword(){
        System.out.println("\n---------修改密码---------");
        boolean exit=true;
        while(exit){
            System.out.print("请输入新密码：");
            String s1=scanner.next();
            System.out.print("请再次输入新密码：");
            String s2=scanner.next();

            // 更新密码到数据库
            if(s1.equals(s2)){
                String updateQuery = "UPDATE admins SET password = ?";
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
                    preparedStatement.setString(1, s2);
                    preparedStatement.executeUpdate();

                    preparedStatement.close();
                    System.out.println("密码修改成功！");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                exit=false;
            }
            else{
                System.out.println("两次密码输入不一致，请重新输入！");
            }
        }
    }
}
