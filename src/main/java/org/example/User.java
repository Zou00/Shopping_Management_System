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
        System.out.println("\n---------重置用户密码---------");
        System.out.print("请输入要重置密码的用户名：");
        String username=scanner.next();
        try {
            if (!userExists(username)) {
                System.out.println("用户不存在!");
                return;
            }
        
        System.out.print("请重置密码：");
        String password=scanner.next();

        String updateQuery = "UPDATE users SET password = ? WHERE username = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
        preparedStatement.setString(1, password);
        preparedStatement.setString(2,username);
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

        String query= "SELECT * FROM users WHERE username = ?";
            try (Connection connection = DriverManager.getConnection(DB_URL);
                 PreparedStatement statement = connection.prepareStatement(query)) {
    
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


    //用户注册
    public void register(){
        System.out.println("\n---------用户注册---------");
            System.out.print("请设置用户名：");
            String name=scanner.next();
            System.out.print("请设置密码：");
            String password=scanner.next();
            System.out.print("请输入电话号码：");
            String phone=scanner.next();
        
        try{
            if (userExists(name)) {
                System.out.println("用户名已存在!");
                return;
            }

            String query = "INSERT INTO users (username, password, phone) VALUES (?,?,?)";
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,password);
            preparedStatement.setString(3,phone);
            preparedStatement.executeUpdate();

            System.out.println("注册成功！");
            Main.userMenu();

        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    //用户登录
    public void login(){
        System.out.println("\n---------用户登录---------");
        System.out.print("用户名：");
        String username = scanner.next();
        System.out.print("密码：");
        String password = scanner.next();
    
        // 查询数据库验证用户名和密码
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
    
            if (resultSet.next()) {
                System.out.println("登录成功!");
                Main.userMenu();
            } else {
                System.out.println("用户名或密码错误！");
            }
    
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //修改密码
    public void changePassword(){
        System.out.println("\n---------修改密码---------");
        System.out.print("请输入用户名：");
        String username=scanner.next();
        boolean exit=true;
        while(exit){
            System.out.print("请输入新密码：");
            String s1=scanner.next();
            System.out.print("请再次输入新密码：");
            String s2=scanner.next();

            // 更新密码到数据库
            if(s1.equals(s2)){
                String updateQuery = "UPDATE users SET password = ? WHERE username = ?";
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
                    preparedStatement.setString(1, s2);
                    preparedStatement.setString(2,username);
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

    //重置密码
    public void resetPassword(){
        System.out.println("---------重置密码---------");
        System.out.print("用户名：");
        String username=scanner.next();
        System.out.print("电话：");
        int phone=scanner.nextInt();
        try {
            if (!userExists(username)) {
                System.out.println("用户不存在!");
                return;
            }
            if(!phoneMatch(username, phone)){
                System.out.println("输入的电话有误！");
                return;
            }
        
        System.out.print("请重置密码：");
        String password=scanner.next();

        String updateQuery = "UPDATE users SET password = ? WHERE username = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
        preparedStatement.setString(1, password);
        preparedStatement.setString(2,username);
        preparedStatement.executeUpdate();

        System.out.println("密码重置成功!");

        preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //判断电话号码是否匹配
    private boolean phoneMatch(String username,int phone) throws SQLException {
        String query = "SELECT * FROM users WHERE username = ? AND phone = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, username);
        preparedStatement.setInt(2, phone);
        ResultSet resultSet = preparedStatement.executeQuery();
        boolean match = resultSet.next();
        preparedStatement.close();
        return match;
    }
}


