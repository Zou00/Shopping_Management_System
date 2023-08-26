package org.example;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Admin {
    Scanner scanner = new Scanner(System.in);

    //管理员登录
    public boolean login(String userName,String passWord) {
        if (userName.equals("admin") && passWord.equals(readAdminPassword())) {
            return true;
        } else {
            return false;
        }
    }

    //管理员修改自身密码
    public void changePassword(){
        System.out.print("请输入当前管理员密码：");
        String currentPassword = scanner.nextLine();
        if (!currentPassword.equals(readAdminPassword())) {
            System.out.println("密码错误，无法修改管理员密码！");
            return;
        }

        System.out.print("请输入新的管理员密码：");
        String newPassword = scanner.nextLine();

        writeAdminPassword(newPassword);
        System.out.println("密码修改成功！");
    }

    private String readAdminPassword() {
        try (BufferedReader reader = new BufferedReader(new FileReader("D:\\Programming\\ShoppingSystem\\adminPassword.txt"))) {
            return reader.readLine();
        } catch (IOException e) {
            System.out.println("读取管理员密码时出错：" + e.getMessage());
        }
        return "";
    }

    private void writeAdminPassword(String password) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("D:\\Programming\\ShoppingSystem\\adminPassword.txt"))) {
            writer.write(password);
        } catch (IOException e) {
            System.out.println("保存管理员密码时出错：" + e.getMessage());
        }
    }

    //管理员重置用户密码
    public void resetUserPassword(String id){
        List<String> lines=new ArrayList<>();
        scanner.nextLine();
        try (BufferedReader reader = new BufferedReader(new FileReader("D:\\Programming\\ShoppingSystem\\listUsers.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userInfo = line.split(" ");
                if (userInfo.length >= 4) {
                    String userId = userInfo[0];
                    if (id == userId) {
                        // 重置用户密码
                        System.out.print("请输入新密码: ");
                        String newpassword = scanner.nextLine();
                        userInfo[2] = newpassword;
                        line = String.join(" ", userInfo);
                        System.out.println("已重置用户密码！");
                    }
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

    //列出客户信息
    public void listCustomers(){
        System.out.println("---------客户信息---------");
        //客户ID、用户名、用户级别（金牌客户、银牌客户、铜牌客户）、用户注册时间、客户累计消费总金额、用户手机号、用户邮箱
        try (BufferedReader reader = new BufferedReader(new FileReader("D:\\Programming\\ShoppingSystem\\listUsers.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userInfo = line.split(" ");
                if (userInfo.length >= 4) {
                    String userId = userInfo[0];
                    String username = userInfo[1];
                    String userLevel=userInfo[3];
                    String registrationDateTime = userInfo[4];
                    double consumption=Double.parseDouble(userInfo[5]);
                    String phoneNumber=userInfo[6];
                    String email=userInfo[7];

                    // 定义日期时间格式
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss");

                    // 解析日期时间字符串
                    LocalDateTime dateTime = LocalDateTime.parse(registrationDateTime, formatter);
                    String outputDateTime =dateTime.format(formatter);

                    System.out.println("用户ID：" + userId);
                    System.out.println("用户名：" + username);
                    System.out.println("用户等级："+userLevel);
                    System.out.println("注册时间：" + outputDateTime);
                    System.out.println("消费总额："+consumption);
                    System.out.println("电话："+phoneNumber);
                    System.out.println("邮箱："+email);
                    System.out.println("-------------------------");
                }
            }
        } catch (IOException e) {
            System.out.println("读取用户信息时出错：" + e.getMessage());
        }
    }

    //删除客户信息
    public void deleteConsumer(String id, String username) {
        File file=new File("D:\\Programming\\ShoppingSystem\\listUsers.txt");
        File tempFile=new File("D:\\Programming\\ShoppingSystem\\tempListUsers.txt");

        try (BufferedReader reader = new BufferedReader(new FileReader(file));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] userInfo = line.split(" ");
                if (userInfo.length >= 4) {
                    String userId = userInfo[0];
                    String userName = userInfo[1];

                    // 判断是否匹配要删除的客户
                    if ((userId.equals(id)) || (username != null && username.equals(userName))) {
                        continue; // 跳过匹配的客户信息，实现删除
                    }
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
                System.out.println("删除客户信息成功！");
            }
        } else {
            System.out.println("删除原始文件时出错");
        }
    }

    //查询客户信息
    public void checkConsumer(String checkname,String userid){
        System.out.println("-----------------------");
        try (BufferedReader reader = new BufferedReader(new FileReader("D:\\Programming\\ShoppingSystem\\listUsers.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userInfo = line.split(" ");
                if (userInfo.length >= 4) {
                    String userId =userInfo[0];
                    String username = userInfo[1];
                    String userLevel = userInfo[3];
                    String registrationDateTime = userInfo[4];
                    double consumption = Double.parseDouble(userInfo[5]);
                    String phoneNumber = userInfo[6];
                    String email = userInfo[7];

                    // 定义日期时间格式
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss");

                    // 解析日期时间字符串
                    LocalDateTime dateTime = LocalDateTime.parse(registrationDateTime, formatter);
                    String outputDateTime = dateTime.format(formatter);

                    if ((userId.equals(userid)) || (username.equals(checkname))) {
                        System.out.println("用户ID：" + userId);
                        System.out.println("用户名：" + username);
                        System.out.println("用户等级：" + userLevel);
                        System.out.println("注册时间：" + outputDateTime);
                        System.out.println("消费总额：" + consumption);
                        System.out.println("电话：" + phoneNumber);
                        System.out.println("邮箱：" + email);
                        System.out.println("-------------------------");
                    }else {
                        System.out.println(" ");
                    }
                }
            }
        }catch (IOException e) {
            System.out.println("读取用户信息时出错：" + e.getMessage());
        }
    }
}


