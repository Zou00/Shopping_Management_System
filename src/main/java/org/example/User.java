package org.example;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.security.SecureRandom;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class User {
    Scanner scanner = new Scanner(System.in);
    //注册
    public void register() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("请输入用户名(不少于5个字符)：");
        String username = scanner.nextLine();
        while (username.length() < 5) {
            System.out.print("用户名长度不能少于5个字符，请重新输入：");
            username = scanner.nextLine();
            }

            System.out.print("请设置密码：");
            String password1 = scanner.nextLine();
            while (password1.length() <= 8 || !isPasswordValid(password1)) {
                System.out.println("密码长度必须大于8个字符，并且必须是大小写字母、数字和标点符号的组合，请重新输入：");
                password1 = scanner.nextLine();
            }
        // 获取当前时间作为注册时间
        LocalDateTime registrationDateTime = LocalDateTime.now();

        String userID=generateRandomId();
        String userLevel="铜牌客户";
        double consumption=0.0;

        System.out.print("请输入手机号：");
        String phonenumber=scanner.nextLine();
        System.out.print("请输入邮箱：");
        String email=scanner.nextLine();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("D:\\Programming\\ShoppingSystem\\listUsers.txt", true))) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss");
            String formattedDateTime = registrationDateTime.format(formatter);

            writer.write(userID+" "+username + " " + password1 + " "+userLevel+" "+ formattedDateTime+" "+consumption+" "+phonenumber+" "+email);

            writer.newLine();
            System.out.println("注册成功！");
        } catch (IOException e) {
            System.out.println("保存用户信息时出错：" + e.getMessage());
        }
    }

    //判断密码是否长度大于8个字符，并且是大小写字母、数字和标点符号的组合
    public static boolean isPasswordValid(String password) {
        if (password.length() < 8) {
            return false;
        }

        boolean hasUppercase = false;
        boolean hasLowercase = false;
        boolean hasNumber = false;

        for (char ch : password.toCharArray()) {
            if (Character.isUpperCase(ch)) {
                hasUppercase = true;
            } else if (Character.isLowerCase(ch)) {
                hasLowercase = true;
            } else if (Character.isDigit(ch)) {
                hasNumber = true;
            }

            if (hasUppercase && hasLowercase && hasNumber) {
                return true;
            }
        }

        return false;
    }

    //生成随机5位编号
    private String generateRandomId() {
        Set<String> usedIds = new HashSet<>();
        Random random = new Random();
        StringBuilder builder = new StringBuilder();

        while (true) {
            for (int i = 0; i < 5; i++) {
                int digit = random.nextInt(10);
                builder.append(digit);
            }

            String randomId = builder.toString();
            if (!usedIds.contains(randomId)) {
                usedIds.add(randomId);
                return randomId;
            }

            builder.setLength(0); // 清空 StringBuilder
            if (usedIds.size() >= 10000) {
                throw new RuntimeException("无法生成唯一的随机编号！");
            }
        }
    }

    //登录
    public  void userLogin(){
        System.out.print("请输入用户名：");
        String userName=scanner.next();
        System.out.print("请输入密码：");
        String userPassword=scanner.next();

        if (iflogin(userName, userPassword)) {
            System.out.println("登录成功！");
            Main.userMenu();
        } else {
            System.out.println("登录失败！账户已锁定！");
        }
    }

    //判断密码输入超过五次
    private static boolean iflogin(String username, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader("D:\\Programming\\ShoppingSystem\\listUsers.txt"))) {
            String line;
            int attempts = 0;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                String storedUsername = parts[1];
                String storedPassword = parts[2];

                if (storedUsername.equals(username)) {
                    if (storedPassword.equals(password)) {
                        return true; // 登录成功
                    } else {
                        attempts++;
                        if (attempts >= 5) {
                            return false; // 账户已锁定
                        }
                        System.out.println("密码错误！请重新输入。");
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("读取用户登录文件时出错：" + e.getMessage());
        }
        return false; // 用户名不存在
    }

    //修改密码
    public void changePassword(String password){
        List<String> lines = new ArrayList<>();
        scanner.nextLine();
        try (BufferedReader reader = new BufferedReader(new FileReader("D:\\Programming\\ShoppingSystem\\listUsers.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userInfo = line.split(" ");
                String passWord = userInfo[2];
                if (password.equals(passWord)) {
                    System.out.print("请输入新密码: ");
                    String newpassword = scanner.next();
                    while (newpassword.length() <= 8 || !isPasswordValid(newpassword)) {
                        System.out.println("密码长度必须大于8个字符，并且必须是大小写字母、数字和标点符号的组合，请重新输入：");
                        newpassword = scanner.nextLine();
                    }
                    userInfo[2] = newpassword;
                    line = String.join(" ", userInfo);
                    System.out.println("成功修改密码！");
                }
                lines.add(line);
            }
        } catch (IOException e) {
            System.out.println("读取用户信息时出错：" + e.getMessage());
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("D:\\Programming\\ShoppingSystem\\listUsers.txt"))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("保存用户信息时出错：" + e.getMessage());
        }
    }

    //重置密码
    public void resetPassword(String username,String email){
        List<String> lines=new ArrayList<>();
        scanner.nextLine();
        try (BufferedReader reader = new BufferedReader(new FileReader("D:\\Programming\\ShoppingSystem\\listUsers.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userInfo = line.split(" ");
                String userName = userInfo[1];
                String Email=userInfo[7];
                if (username.equals(userName) && email.equals(Email)) {
                    String newpassword =generateRandomPassword(8) ;
                    userInfo[2] = newpassword;
                    line = String.join(" ", userInfo);
                    System.out.println("密码修改成功！已发送新密码至邮箱:"+email);
                }
                lines.add(line);
            }
        } catch (IOException e) {
            System.out.println("读取用户信息时出错：" + e.getMessage());
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("D:\\Programming\\ShoppingSystem\\listUsers.txt"))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("保存用户信息时出错：" + e.getMessage());
        }
    }

    //生成随机8位密码
    private static String generateRandomPassword(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()_+{}:\"<>?,./;'[]\\|";
        Random random = new SecureRandom();
        StringBuilder password = new StringBuilder();
        while (password.length() < length) {
            int index = random.nextInt(characters.length());
            char character = characters.charAt(index);
            password.append(character);
        }
        return password.toString();
    }
}


