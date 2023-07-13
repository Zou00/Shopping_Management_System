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

    private Connection connection;
    private Statement statement;
    private Scanner scanner;

    public User(){
        try {
            connection = DriverManager.getConnection(DB_URL);
            statement = connection.createStatement();
            scanner = new Scanner(System.in);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    //管理员重置用户密码
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

    //查找用户是否存在
    private boolean userExists(String username) throws SQLException {
        String query = "SELECT * FROM users WHERE username = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, username);
        ResultSet resultSet = preparedStatement.executeQuery();
        boolean exists = resultSet.next();
        preparedStatement.close();
        return exists;
    }

    //列出所有客户信息
    public void listCustomers(){
        String query="SELECT username,phone FROM users";
        try{
            ResultSet resultSet=statement.executeQuery(query);
            System.out.println("\n--------客户信息表---------");
            while(resultSet.next()){
                String username=resultSet.getString("username");
                String phone=resultSet.getString("phone");

                System.out.println("用户名："+username);
                System.out.println("电话："+phone);
                System.out.println("-------------------------");
            }
            
            resultSet.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    //删除客户信息
    public void deleteConsumer(){
       System.out.print("请输入要删除信息的用户名：");
       String name=scanner.next();
        try {
            if (!userExists(name)) {
                System.out.println("用户不存在!");
                return;
            }
        String deletequery="DELETE FROM users WHERE username = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(deletequery);
        preparedStatement.setString(1, name);
        preparedStatement.executeUpdate();

        System.out.println("用户信息删除成功！");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //查询客户信息
    public void checkConsumer(){
        System.out.print("请输入要查询的用户名：");
        String username=scanner.next();

        String sql= "SELECT * FROM users WHERE username = ?";
            try (Connection connection = DriverManager.getConnection(DB_URL);
                 PreparedStatement statement = connection.prepareStatement(sql)) {
    
                statement.setString(1, username);
                ResultSet resultSet = statement.executeQuery();
    
                if (resultSet.next()) {
                    String name = resultSet.getString("username");
                    String phone = resultSet.getString("phone");
    
                    System.out.println("\n-------------------------");
                    System.out.println("用户名：" +name);
                    System.out.println("电话：" + phone);
                    System.out.println("------------------------" );
                } else {
                    System.out.println("未找到客户：" + username);
                }
                
            } catch (SQLException e) {
                e.printStackTrace();
            }
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


