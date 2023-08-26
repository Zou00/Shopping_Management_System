package org.example;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Cart {
    Scanner scanner = new Scanner(System.in);

    //查看商品
    public void listProducts() {
        System.out.println("---------商品信息---------");
        //商品编号、商品名称、生产厂家、生产日期、型号、零售价格
        try (BufferedReader reader = new BufferedReader(new FileReader("D:\\Programming\\ShoppingSystem\\products.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userInfo = line.split(" ");
                if (userInfo.length >= 4) {
                    String productId = userInfo[0];
                    String productname = userInfo[1];
                    String origin = userInfo[2];
                    String dateTime = userInfo[3];
                    String size = userInfo[4];
                    double outprice = Double.parseDouble(userInfo[6]);

                    System.out.println("商品编号：" + productId);
                    System.out.println("商品名称：" + productname);
                    System.out.println("生产厂家：" + origin);
                    System.out.println("生产日期：" + dateTime);
                    System.out.println("型号：" + size);
                    System.out.println("零售价格：" + outprice);
                    System.out.println("-------------------------");
                }
            }
        } catch (IOException e) {
            System.out.println("读取商品信息时出错：" + e.getMessage());
        }
    }

    //加入购物车
    public void addToCart() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("请输入商品编号：");
        String productID = scanner.nextLine();
        System.out.print("请输入数量：");
        int num = scanner.nextInt();


        try (BufferedWriter writer = new BufferedWriter(new FileWriter("D:\\Programming\\ShoppingSystem\\cart.txt", true))) {
            writer.write(productID + " " + num);
            writer.newLine();
            System.out.println("添加成功！");
        } catch (IOException e) {
            System.out.println("保存商品信息时出错：" + e.getMessage());
        }
    }

    //移除商品
    public void removeFromCart(String id) {
        File file = new File("D:\\Programming\\ShoppingSystem\\cart.txt");
        File tempFile = new File("D:\\Programming\\ShoppingSystem\\tempCart.txt");

        try (BufferedReader reader = new BufferedReader(new FileReader(file));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] userInfo = line.split(" ");
                String productId = userInfo[0];
                if (productId.equals(id)) {
                    continue;
                }
                writer.write(line);
                writer.newLine();
            }

        } catch (IOException e) {
            System.out.println("执行删除操作时出错：" + e.getMessage());
            return;
        }

        // 删除原始文件
        if (file.delete()) {
            // 重命名临时文件为原始文件名
            if (!tempFile.renameTo(file)) {
                System.out.println("重命名文件时出错");
            } else {
                System.out.println("移除购物车成功！");
            }
        } else {
            System.out.println("删除原始文件时出错");
        }
    }

    //修改商品
    public void modifyCart(String id) {
        List<String> lines = new ArrayList<>();
        scanner.nextLine();
        try (BufferedReader reader = new BufferedReader(new FileReader("D:\\Programming\\ShoppingSystem\\cart.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userInfo = line.split(" ");
                String productId = userInfo[0];
                if (id.equals(productId)) {
                    System.out.print("请输入新的数量: ");
                    double num = scanner.nextDouble();
                    userInfo[1] = String.valueOf(num);
                    line = String.join(" ", userInfo);
                    System.out.println("已修改数量！");
                }
                lines.add(line);
            }
        } catch (IOException e) {
            System.out.println("读取商品信息时出错：" + e.getMessage());
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("D:\\Programming\\ShoppingSystem\\cart.txt"))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("保存商品信息时出错：" + e.getMessage());
        }
    }

    //查看购物车
    public void list() {
        System.out.println("---------购物车---------");
        //商品编号、零售价格
        try (BufferedReader reader = new BufferedReader(new FileReader("D:\\Programming\\ShoppingSystem\\cart.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userInfo = line.split(" ");
                String productId = userInfo[0];
                int num = Integer.parseInt(userInfo[1]);

                System.out.println("商品编号：" + productId);
                System.out.println("数量：" + num);
                System.out.println("-------------------------");

            }
        } catch (IOException e) {
            System.out.println("读取商品信息时出错：" + e.getMessage());
        }
    }
}
