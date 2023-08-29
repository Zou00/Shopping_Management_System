package org.example;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class User {
    Scanner scanner = new Scanner(System.in);
    //注册
    public void register() {
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

        String userID = generateRandomId();
        String userLevel = "铜牌客户";
        double consumption = 0.0;

        System.out.print("请输入手机号：");
        String phonenumber = scanner.nextLine();
        System.out.print("请输入邮箱：");
        String email = scanner.nextLine();

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("用户信息");

            // 创建表头
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("用户ID");
            headerRow.createCell(1).setCellValue("用户名");
            headerRow.createCell(2).setCellValue("密码");
            headerRow.createCell(3).setCellValue("用户等级");
            headerRow.createCell(4).setCellValue("注册时间");
            headerRow.createCell(5).setCellValue("消费金额");
            headerRow.createCell(6).setCellValue("手机号");
            headerRow.createCell(7).setCellValue("邮箱");

            // 创建数据行
            Row dataRow = sheet.createRow(1);
            dataRow.createCell(0).setCellValue(userID);
            dataRow.createCell(1).setCellValue(username);
            dataRow.createCell(2).setCellValue(password1);
            dataRow.createCell(3).setCellValue(userLevel);
            dataRow.createCell(4).setCellValue(registrationDateTime.toString());
            dataRow.createCell(5).setCellValue(consumption);
            dataRow.createCell(6).setCellValue(phonenumber);
            dataRow.createCell(7).setCellValue(email);

            // 保存为XLSX文件
            try (FileOutputStream fileOut = new FileOutputStream("D:\\Programming\\ShoppingSystem\\listUsers.xlsx")) {
                workbook.write(fileOut);
                System.out.println("注册信息保存成功！");
            } catch (IOException e) {
                System.out.println("保存用户信息时出错：" + e.getMessage());
            }
        } catch (IOException e) {
            System.out.println("创建工作簿时出错：" + e.getMessage());
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
        try (Workbook workbook = WorkbookFactory.create(new FileInputStream("D:\\Programming\\ShoppingSystem\\listUsers.xlsx"))) {
            Sheet sheet = workbook.getSheetAt(0);

            int attempts = 0;
            for (Row row : sheet) {
                String storedUsername = row.getCell(1).getStringCellValue();
                String storedPassword = row.getCell(2).getStringCellValue();

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
        } catch (InvalidFormatException e) {
            System.out.println("非法的Excel文件格式：" + e.getMessage());
        }

        return false; // 用户名不存在
    }


    //修改密码
    public void changePassword(String password) {
        Scanner scanner = new Scanner(System.in);
        List<String[]> userInfoList = new ArrayList<>();
        try (Workbook workbook = WorkbookFactory.create(new FileInputStream("D:\\Programming\\ShoppingSystem\\listUsers.xlsx"))) {
            Sheet sheet = workbook.getSheetAt(0); // 假设用户信息在第一个Sheet中

            for (Row row : sheet) {
                String storedPassword = row.getCell(2).getStringCellValue();

                if (password.equals(storedPassword)) {
                    System.out.print("请输入新密码: ");
                    String newPassword = scanner.next();
                    while (newPassword.length() <= 8 || !isPasswordValid(newPassword)) {
                        System.out.println("密码长度必须大于8个字符，并且必须是大小写字母、数字和标点符号的组合，请重新输入：");
                        newPassword = scanner.nextLine();
                    }
                    row.getCell(2).setCellValue(newPassword);
                    System.out.println("成功修改密码！");
                }

                int lastCellNum = row.getLastCellNum();
                String[] userInfo = new String[lastCellNum];
                for (int i = 0; i < lastCellNum; i++) {
                    Cell cell = row.getCell(i);
                    if (cell != null) {
                        userInfo[i] = cell.getStringCellValue();
                    } else {
                        userInfo[i] = ""; // 空单元格处理
                    }
                }
                userInfoList.add(userInfo);
            }
        } catch (IOException e) {
            System.out.println("读取用户信息时出错：" + e.getMessage());
            return;
        } catch (InvalidFormatException e) {
            System.out.println("非法的Excel文件格式：" + e.getMessage());
            return;
        }

        try (Workbook workbook = WorkbookFactory.create(new FileInputStream("D:\\Programming\\ShoppingSystem\\listUsers.xlsx"));
             FileOutputStream fileOut = new FileOutputStream("D:\\Programming\\ShoppingSystem\\listUsers.xlsx")) {

            Sheet sheet = workbook.getSheetAt(0);
            int rowNum = 0;
            for (String[] userInfo : userInfoList) {
                Row row = sheet.getRow(rowNum);
                if (row == null) {
                    row = sheet.createRow(rowNum);
                }
                for (int i = 0; i < userInfo.length; i++) {
                    String cellValue = userInfo[i];
                    row.createCell(i).setCellValue(cellValue);
                }
                rowNum++;
            }

            workbook.write(fileOut);
        } catch (IOException e) {
            System.out.println("保存用户信息时出错：" + e.getMessage());
        } catch (InvalidFormatException e) {
            System.out.println("非法的Excel文件格式：" + e.getMessage());
        }
    }

    //重置密码
    public void resetPassword(String username, String email) {
        Scanner scanner = new Scanner(System.in);
        List<String[]> userInfoList = new ArrayList<>();
        try (Workbook workbook = WorkbookFactory.create(new FileInputStream("D:\\Programming\\ShoppingSystem\\listUsers.xlsx"))) {
            Sheet sheet = workbook.getSheetAt(0); // 假设用户信息在第一个Sheet中

            for (Row row : sheet) {
                String storedUsername = row.getCell(1).getStringCellValue();
                String storedEmail = row.getCell(7).getStringCellValue();

                if (username.equals(storedUsername) && email.equals(storedEmail)) {
                    String newPassword = generateRandomPassword(8);
                    row.getCell(2).setCellValue(newPassword);
                    System.out.println("密码修改成功！已发送新密码至邮箱：" + email);
                }

                int lastCellNum = row.getLastCellNum();
                String[] userInfo = new String[lastCellNum];
                for (int i = 0; i < lastCellNum; i++) {
                    Cell cell = row.getCell(i);
                    if (cell != null) {
                        userInfo[i] = cell.getStringCellValue();
                    } else {
                        userInfo[i] = "";
                    }
                }
                userInfoList.add(userInfo);
            }
        } catch (IOException e) {
            System.out.println("读取用户信息时出错：" + e.getMessage());
            return;
        } catch (InvalidFormatException e) {
            System.out.println("非法的Excel文件格式：" + e.getMessage());
            return;
        }

        try (Workbook workbook = WorkbookFactory.create(new FileInputStream("D:\\Programming\\ShoppingSystem\\listUsers.xlsx"));
             FileOutputStream fileOut = new FileOutputStream("D:\\Programming\\ShoppingSystem\\listUsers.xlsx")) {

            Sheet sheet = workbook.getSheetAt(0); // 假设用户信息在第一个Sheet中
            int rowNum = 0;
            for (String[] userInfo : userInfoList) {
                Row row = sheet.getRow(rowNum);
                if (row == null) {
                    row = sheet.createRow(rowNum);
                }
                for (int i = 0; i < userInfo.length; i++) {
                    String cellValue = userInfo[i];
                    row.createCell(i).setCellValue(cellValue);
                }
                rowNum++;
            }

            workbook.write(fileOut);
        } catch (IOException e) {
            System.out.println("保存用户信息时出错：" + e.getMessage());
        } catch (InvalidFormatException e) {
            System.out.println("非法的Excel文件格式：" + e.getMessage());
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
