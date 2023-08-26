package org.example;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class Pay {
    public static void calculateTotalPrice(String cartFile, String productFile,String historyFile) {
        Map<String, Integer> cartItems = readCartItems(cartFile);
        Map<String, Double> productPrices = readProductPrices(productFile);

        double totalPrice = 0.0;
        for (Map.Entry<String, Integer> entry : cartItems.entrySet()) {
            String productId = entry.getKey();
            int num = entry.getValue();

            if (productPrices.containsKey(productId)) {
                double price = productPrices.get(productId);
                totalPrice += price * num;
                updateProductQuantity(productFile, productId, -num);

                // 获取当前时间作为购买时间
                LocalDateTime DateTime = LocalDateTime.now();
                saveHistory(historyFile, productId, num, DateTime);
            }
        }

        System.out.println("消费总额：" + totalPrice);

        clearCartItems(cartFile);
    }

    private static Map<String, Integer> readCartItems(String cartFile) {
        Map<String, Integer> cartItems = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(cartFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts.length == 2) {
                    String productId = parts[0];
                    int num = Integer.parseInt(parts[1]);
                    cartItems.put(productId, num);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return cartItems;
    }

    private static Map<String, Double> readProductPrices(String productFile) {
        Map<String, Double> productPrices = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(productFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts.length >= 2) {
                    String productId = parts[0];
                    double price = Double.parseDouble(parts[6]);
                    productPrices.put(productId, price);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return productPrices;
    }

    private static void updateProductQuantity(String productFile, String productId, int quantityDelta) {
        try {
            List<String> lines = new ArrayList<>();

            // 读取商品文件并找到对应行
            try (BufferedReader reader = new BufferedReader(new FileReader(productFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(" ");
                    if (parts.length == 8) {
                        String fileProductId = parts[0].trim();
                        if (fileProductId.equals(productId)) {
                            int currentnum = Integer.parseInt(parts[7].trim());
                            int newnum = Math.max(0, currentnum + quantityDelta);
                            parts[7] = String.valueOf(newnum);  // 更新商品数量
                            line = String.join(" ", parts);  // 将更新后的行拼接回字符串
                        }
                    }
                    lines.add(line);  // 将行添加到列表中
                }
            }

            // 写入更新后的商品文件
            try (PrintWriter writer = new PrintWriter(new FileWriter(productFile))) {
                for (String line : lines) {
                    writer.println(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static void clearCartItems(String cartFile) {
        try {
            PrintWriter writer = new PrintWriter(cartFile);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void simulatepay() {
        String cartFile = "D:\\Programming\\ShoppingSystem\\cart.txt";
        String productFile = "D:\\Programming\\ShoppingSystem\\products.txt";
        String historyFile="D:\\Programming\\ShoppingSystem\\History.txt";

        Scanner scanner = new Scanner(System.in);
        System.out.print("请选择使用的支付方式(1支付宝、2微信、3银行卡):");
        int choice = scanner.nextInt();

        calculateTotalPrice(cartFile, productFile, historyFile);
    }

    //保存购物历史
    public static void saveHistory(String historyFile,String productID, int num, LocalDateTime DateTime) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(historyFile, true))) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss");
            String formattedDateTime = DateTime.format(formatter);

            writer.write(productID + " " + num + " " + formattedDateTime);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("保存商品信息时出错：" + e.getMessage());
        }
    }

    //查看购物历史
    public void History() {
        System.out.println("-----------------------");
        try (BufferedReader reader = new BufferedReader(new FileReader("D:\\Programming\\ShoppingSystem\\History.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] cartInfo = line.split(" ");
                String productId = cartInfo[0];
                int num = Integer.parseInt(cartInfo[1]);
                String DateTime = cartInfo[2];

                // 定义日期时间格式
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss");

                // 解析日期时间字符串
                LocalDateTime dateTime = LocalDateTime.parse(DateTime, formatter);
                String outputDateTime = dateTime.format(formatter);

                System.out.println("商品ID：" + productId);
                System.out.println("数量：" + num);
                System.out.println("购买时间：" + outputDateTime);
                System.out.println("-------------------------");
            }
        } catch(IOException e){
            System.out.println("读取购物信息时出错：" + e.getMessage());
        }
    }
}



