package org.example;

import java.sql.*;
import java.util.Scanner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Cart{
    private static final String DB_URL = "jdbc:sqlite:Data.db";

    private Connection connection;
    private Statement statement;
    private Scanner scanner;

    public Cart(){
        try {
            connection = DriverManager.getConnection(DB_URL);
            statement = connection.createStatement();
            scanner = new Scanner(System.in);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //加入商品
    public void addToCart(String addName){
        System.out.print("请输入要加入商品的数量：");
        int n=scanner.nextInt();
        try{
            String query = "INSERT INTO cart (productname, number) VALUES (?,?)";
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            preparedStatement.setString(1,addName);
            preparedStatement.setInt(2,n);
            preparedStatement.executeUpdate();
    
            System.out.println("成功加入购物车！");
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    //移除商品
    public void removeFromCart(){
        System.out.println("\n--------移除商品--------");
        System.out.print("请输入要移除的商品：");
        String name=scanner.next();
        try {
            if (!cartExists(name)) {
                System.out.println("商品不存在!");
                return;
            }
        String deletequery="DELETE FROM cart WHERE productname = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(deletequery);
        preparedStatement.setString(1, name);
        preparedStatement.executeUpdate();

        System.out.println("移除商品成功！");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //判断购物车中是否有该商品
    private boolean cartExists(String productname) throws SQLException {
        String query = "SELECT * FROM cart WHERE productname = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, productname);
        ResultSet resultSet = preparedStatement.executeQuery();
        boolean exists = resultSet.next();
        preparedStatement.close();
        return exists;
    }

    //修改商品
    public void modifyCart(){
        System.out.println("\n---------修改商品信息---------");
        System.out.print("请输入要修改信息的商品名：");
        String productname=scanner.next();
    
        try {
            if (!cartExists(productname)) {
                System.out.println("商品不存在!");
                return;
            }
            
        System.out.print("修改商品数量：");
        int num=scanner.nextInt();
    
        String updateQuery = "UPDATE cart SET number = ? WHERE productname = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
        preparedStatement.setInt(1, num);
        preparedStatement.setString(2,productname);
        preparedStatement.executeUpdate();
    
        System.out.println("商品信息修改成功!");
    
        preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //查看购物车
    public void list(){
        String query="SELECT productname, number FROM cart";
        try{
            ResultSet resultSet=statement.executeQuery(query);
            System.out.println("\n--------购物车---------");
            while(resultSet.next()){
                String productname=resultSet.getString("productname");
                String number=resultSet.getString("number");

                System.out.println("商品名："+productname);
                System.out.println("数量："+number);
                System.out.println("-------------------------");
            }
            
            resultSet.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    //模拟结账
    public void checkout(){
        try (Connection connection = DriverManager.getConnection(DB_URL)) {
        double total = calculateTotalPrice(connection);

        System.out.println("--------------------");
        System.out.println("购物清单：");
        String query = "SELECT * FROM cart";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                String productname = resultSet.getString("productname");
                int number = resultSet.getInt("number");
                System.out.println(productname + " —— 数量：" + number + " —— 小计：" + (getPrice(connection, productname) * number));
                decreaseproduct(connection, productname, number);
                savePurchaseHistory(connection,productname,number);
            }
        }
        System.out.println("--------------------");
        System.out.println("总金额：￥" + total);

        clearCart(connection);

    } catch (SQLException e) {
        e.printStackTrace();
    }
}

    public static double calculateTotalPrice(Connection connection) throws SQLException {
        double total = 0.0;
        String query = "SELECT * FROM cart";
        try (Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                String productname = resultSet.getString("productname");
                int number = resultSet.getInt("number");
                double price = getPrice(connection, productname);
                total += (price * number);
            }
        }
        return total;
    }

    public static double getPrice(Connection connection, String productname) throws SQLException {
        String query = "SELECT price FROM products WHERE productname = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, productname);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getDouble("price");
            }
        }
        return 0.0;
    }

    public static void clearCart(Connection connection) throws SQLException {
        String query = "DELETE FROM cart";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
        }
    }

    public static void decreaseproduct(Connection connection, String productname, int number) throws SQLException {
        String updateQuery = "UPDATE products SET number = number - ? WHERE productname = ?";
        try (PreparedStatement statement = connection.prepareStatement(updateQuery)) {
            statement.setInt(1, number);
            statement.setString(2, productname);
            statement.executeUpdate();
        }
    }

    public void shoppingHistory() {
        try(Connection connection = DriverManager.getConnection(DB_URL)) {
            displayPurchaseHistory(connection);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void savePurchaseHistory(Connection connection, String productname, int number) throws SQLException {
        String insertQuery = "INSERT INTO shoppinghistory (productname, number) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(insertQuery)) {
            statement.setString(1, productname);
            statement.setInt(2, number);
            statement.executeUpdate();
        }
    }

    public static void displayPurchaseHistory(Connection connection) throws SQLException {
        String query = "SELECT * FROM shoppinghistory";
        try (Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query)) {
            System.out.println("--------购物历史--------");
            System.out.println("商品     数量");
            while (resultSet.next()) {
                String productname = resultSet.getString("productname");
                int number = resultSet.getInt("number");
                System.out.println(productname + "      " + number);
            }
            System.out.println("------------------------");
        }
    }
}