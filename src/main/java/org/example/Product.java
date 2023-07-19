package org.example;

import java.sql.*;
import java.util.Scanner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Product{
    private static final String DB_URL = "jdbc:sqlite:Data.db";

    private Connection connection;
    private Statement statement;
    private Scanner scanner;

    public Product(){
        try {
            connection = DriverManager.getConnection(DB_URL);
            statement = connection.createStatement();
            scanner = new Scanner(System.in);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //列出所有商品信息
    public void listProducts(){
        String query="SELECT productname,price,number FROM products";
        try{
            ResultSet resultSet=statement.executeQuery(query);
            System.out.println("\n--------商品信息表---------");
            while(resultSet.next()){
                String productname=resultSet.getString("productname");
                String price=resultSet.getString("price");
                String number=resultSet.getString("number");

                System.out.println("商品名："+productname);
                System.out.println("售价："+price);
                System.out.println("剩余："+number);
                System.out.println("-------------------------");
            }
            
            resultSet.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    //添加商品信息
    public void addProduct(){
        System.out.println("\n---------添加商品---------");
        System.out.print("请输入商品名：");
        String name=scanner.next();
        System.out.print("请输入价格：");
        String price=scanner.next();
        System.out.print("请输入库存：");
        String number=scanner.next();
            
        try{
            if (productExists(name)) {
                System.out.println("商品已存在!");
                return;
            }
    
            String query = "INSERT INTO products (productname, price, number) VALUES (?,?,?)";
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,price);
            preparedStatement.setString(3,number);
            preparedStatement.executeUpdate();
    
            System.out.println("添加成功！");
    
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    //判断商品是否存在
    private boolean productExists(String productname) throws SQLException {
        String query = "SELECT * FROM products WHERE productname = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, productname);
        ResultSet resultSet = preparedStatement.executeQuery();
        boolean exists = resultSet.next();
        preparedStatement.close();
        return exists;
    }

    //修改商品信息
    public void modifyProduct(){
        System.out.println("\n---------修改商品信息---------");
        System.out.print("请输入要修改信息的商品名：");
        String productname=scanner.next();

        try {
            if (!productExists(productname)) {
                System.out.println("商品不存在!");
                return;
            }
        
        System.out.print("修改商品价格：");
        String price=scanner.next();

        String updateQuery = "UPDATE products SET price = ? WHERE productname = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
        preparedStatement.setString(1, price);
        preparedStatement.setString(2,productname);
        preparedStatement.executeUpdate();

        System.out.println("商品信息修改成功!");

        preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //删除商品信息
    public void deleteProduct(){
        System.out.print("请输入要删除信息的商品名：");
        String name=scanner.next();
        try {
            if (!productExists(name)) {
                System.out.println("商品不存在!");
                return;
            }
        String deletequery="DELETE FROM products WHERE productname = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(deletequery);
        preparedStatement.setString(1, name);
        preparedStatement.executeUpdate();

        System.out.println("商品信息删除成功！");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //查询商品信息
    public void checkProduct(){
        System.out.print("请输入要查询的商品名：");
        String name=scanner.next();

        String query= "SELECT * FROM products WHERE productname = ?";
            try (Connection connection = DriverManager.getConnection(DB_URL);
                 PreparedStatement statement = connection.prepareStatement(query)) {
    
                statement.setString(1, name);
                ResultSet resultSet = statement.executeQuery();
    
                if (resultSet.next()) {
                    String productname = resultSet.getString("productname");
                    String price = resultSet.getString("price");
                    String number =resultSet.getString("number");
    
                    System.out.println("\n-------------------------");
                    System.out.println("商品名：" +productname);
                    System.out.println("售价：" + price);
                    System.out.println("剩余："+number);
                    System.out.println("------------------------" );
                } else {
                    System.out.println("商品不存在！");
                }
                
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }

    //商品加入购物车(判断是否存在该商品)
    public boolean addToCart(String addName){
        try{
            if (!productExists(addName)) {
                System.out.println("商品不存在，无法加入购物车!");
                return false;
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
}
