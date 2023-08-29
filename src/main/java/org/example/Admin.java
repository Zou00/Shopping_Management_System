package org.example;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Admin {
    Scanner scanner = new Scanner(System.in);

    //管理员登录
    public boolean login(String userName, String passWord) {
        try (Workbook workbook = WorkbookFactory.create(new FileInputStream("D:\\Programming\\ShoppingSystem\\adminPassword.xlsx"))) {
            Sheet sheet = workbook.getSheetAt(0); // 假设用户信息在第一个Sheet中

            for (Row row : sheet) {
                String storedUsername = row.getCell(1).getStringCellValue();
                String storedPassword = row.getCell(2).getStringCellValue();

                if (userName.equals(storedUsername) && passWord.equals(storedPassword)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println("读取用户信息时出错：" + e.getMessage());
        } catch (InvalidFormatException e) {
            System.out.println("非法的Excel文件格式：" + e.getMessage());
        }
        return false;
    }


    //管理员修改自身密码
    public void changePassword() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("请输入当前管理员密码：");
        String currentPassword = scanner.nextLine();

        try (Workbook workbook = WorkbookFactory.create(new FileInputStream("D:\\Programming\\ShoppingSystem\\adminPassword.xlsx"))) {
            Sheet sheet = workbook.getSheetAt(0); // 假设管理员密码在第一个Sheet中

            Row row = sheet.getRow(0);
            String storedPassword = row.getCell(0).getStringCellValue();

            if (!currentPassword.equals(storedPassword)) {
                System.out.println("密码错误，无法修改管理员密码！");
                return;
            }

            System.out.print("请输入新的管理员密码：");
            String newPassword = scanner.nextLine();

            row.getCell(0).setCellValue(newPassword);

            try (FileOutputStream fileOut = new FileOutputStream("D:\\Programming\\ShoppingSystem\\adminPassword.xlsx")) {
                workbook.write(fileOut);
                System.out.println("密码修改成功！");
            } catch (IOException e) {
                System.out.println("保存管理员密码时出错：" + e.getMessage());
            }
        } catch (IOException e) {
            System.out.println("读取管理员密码时出错：" + e.getMessage());
        } catch (InvalidFormatException e) {
            System.out.println("非法的Excel文件格式：" + e.getMessage());
        }
    }

    //管理员重置用户密码
    public void resetUserPassword(String id) {
        Scanner scanner = new Scanner(System.in);
        List<String[]> userInfoList = new ArrayList<>();
        try (Workbook workbook = WorkbookFactory.create(new FileInputStream("D:\\Programming\\ShoppingSystem\\listUsers.xlsx"))) {
            Sheet sheet = workbook.getSheetAt(0); // 假设用户信息在第一个Sheet中

            for (Row row : sheet) {
                String storedUserId = row.getCell(0).getStringCellValue();

                if (id.equals(storedUserId)) {
                    // 重置用户密码
                    System.out.print("请输入新密码：");
                    String newPassword = scanner.nextLine();
                    row.getCell(2).setCellValue(newPassword);
                    System.out.println("已重置用户密码！");
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

    //列出客户信息
    public void listCustomers() {
        System.out.println("---------客户信息---------");
        try (Workbook workbook = WorkbookFactory.create(new FileInputStream("D:\\Programming\\ShoppingSystem\\listUsers.xlsx"))) {
            Sheet sheet = workbook.getSheetAt(0); // 假设用户信息在第一个Sheet中

            for (Row row : sheet) {
                String userId = row.getCell(0).getStringCellValue();
                String username = row.getCell(1).getStringCellValue();
                String userLevel = row.getCell(3).getStringCellValue();
                String registrationDateTime = row.getCell(4).getStringCellValue();
                double consumption = row.getCell(5).getNumericCellValue();
                String phoneNumber = row.getCell(6).getStringCellValue();
                String email = row.getCell(7).getStringCellValue();

                // 定义日期时间格式
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss");

                // 解析日期时间字符串
                LocalDateTime dateTime = LocalDateTime.parse(registrationDateTime, formatter);
                String outputDateTime = dateTime.format(formatter);

                System.out.println("用户ID：" + userId);
                System.out.println("用户名：" + username);
                System.out.println("用户等级：" + userLevel);
                System.out.println("注册时间：" + outputDateTime);
                System.out.println("消费总额：" + consumption);
                System.out.println("电话：" + phoneNumber);
                System.out.println("邮箱：" + email);
                System.out.println("-------------------------");
            }
        } catch (IOException e) {
            System.out.println("读取用户信息时出错：" + e.getMessage());
        } catch (InvalidFormatException e) {
            System.out.println("非法的Excel文件格式：" + e.getMessage());
        }
    }


    //删除客户信息
    public void deleteConsumer(String id, String username) {
        File file = new File("D:\\Programming\\ShoppingSystem\\listUsers.xlsx");
        File tempFile = new File("D:\\Programming\\ShoppingSystem\\tempListUsers.xlsx");

        try (Workbook workbook = WorkbookFactory.create(new FileInputStream(file));
             Workbook tempWorkbook = WorkbookFactory.create(new FileInputStream(tempFile));
             FileOutputStream fileOut = new FileOutputStream(tempFile)) {

            Sheet sheet = workbook.getSheetAt(0); // 假设用户信息在第一个Sheet中
            Sheet tempSheet = tempWorkbook.getSheetAt(0);

            int rowNum = 0;
            for (Row row : sheet) {
                String userId = row.getCell(0).getStringCellValue();
                String userName = row.getCell(1).getStringCellValue();

                // 判断是否匹配要删除的客户
                if ((userId.equals(id)) || (username != null && username.equals(userName))) {
                    continue; // 跳过匹配的客户信息，实现删除
                }

                Row newRow = tempSheet.createRow(rowNum);
                for (int i = 0; i < row.getLastCellNum(); i++) {
                    Cell cell = row.getCell(i);
                    if (cell != null) {
                        Cell newCell = newRow.createCell(i, cell.getCellType());
                        newCell.setCellValue(cell.getStringCellValue());
                    }
                }
                rowNum++;
            }

            workbook.close();
            tempWorkbook.write(fileOut);
            tempWorkbook.close();
        } catch (IOException e) {
            System.out.println("执行删除操作时出错：" + e.getMessage());
            return;
        } catch (InvalidFormatException e) {
            System.out.println("非法的Excel文件格式：" + e.getMessage());
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
    public void checkConsumer(String checkname, String userid) {
        System.out.println("-----------------------");
        try (Workbook workbook = WorkbookFactory.create(new FileInputStream("D:\\Programming\\ShoppingSystem\\listUsers.xlsx"))) {
            Sheet sheet = workbook.getSheetAt(0); // 假设用户信息在第一个Sheet中

            for (Row row : sheet) {
                String userId = row.getCell(0).getStringCellValue();
                String username = row.getCell(1).getStringCellValue();
                String userLevel = row.getCell(3).getStringCellValue();
                String registrationDateTime = row.getCell(4).getStringCellValue();
                double consumption = row.getCell(5).getNumericCellValue();
                String phoneNumber = row.getCell(6).getStringCellValue();
                String email = row.getCell(7).getStringCellValue();

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
                } else {
                    System.out.println(" ");
                }
            }
        } catch (IOException e) {
            System.out.println("读取用户信息时出错：" + e.getMessage());
        } catch (InvalidFormatException e) {
            System.out.println("非法的Excel文件格式：" + e.getMessage());
        }
    }
}
