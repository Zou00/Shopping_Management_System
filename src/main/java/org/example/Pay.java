package org.example;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
    public static void calculateTotalPrice(String cartFile, String productFile, String historyFile) {
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

        try (Workbook workbook = WorkbookFactory.create(new FileInputStream(cartFile))) {
            Sheet sheet = workbook.getSheetAt(0); // 假设购物车商品信息在第一个Sheet中

            Iterator<Row> iterator = sheet.iterator();
            while (iterator.hasNext()) {
                Row row = iterator.next();
                String productId = row.getCell(0).getStringCellValue();
                int num = (int) row.getCell(1).getNumericCellValue();
                cartItems.put(productId, num);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return cartItems;
    }

    private static Map<String, Double> readProductPrices(String productFile) {
        Map<String, Double> productPrices = new HashMap<>();

        try (Workbook workbook = WorkbookFactory.create(new FileInputStream(productFile))) {
            Sheet sheet = workbook.getSheetAt(0); // 假设商品价格信息在第一个Sheet中

            Iterator<Row> iterator = sheet.iterator();
            while (iterator.hasNext()) {
                Row row = iterator.next();
                String productId = row.getCell(0).getStringCellValue();
                double price = row.getCell(6).getNumericCellValue();
                productPrices.put(productId, price);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return productPrices;
    }

    private static void updateProductQuantity(String productFile, String productId, int quantityDelta) {
        try (Workbook workbook = WorkbookFactory.create(new FileInputStream(productFile))) {
            Sheet sheet = workbook.getSheetAt(0); // 假设商品信息在第一个Sheet中

            for (Row row : sheet) {
                String fileProductId = row.getCell(0).getStringCellValue();
                if (fileProductId.equals(productId)) {
                    int currentnum = (int) row.getCell(7).getNumericCellValue();
                    int newnum = Math.max(0, currentnum + quantityDelta);
                    row.getCell(7).setCellValue(newnum); // 更新商品数量
                    break;
                }
            }

            try (FileOutputStream fileOut = new FileOutputStream(productFile)) {
                workbook.write(fileOut);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void clearCartItems(String cartFile) {
        try (Workbook workbook = WorkbookFactory.create(new FileInputStream(cartFile));
             FileOutputStream fileOut = new FileOutputStream(cartFile)) {

            Sheet sheet = workbook.getSheetAt(0); // 假设购物车商品信息在第一个Sheet中

            for (Row row : sheet) {
                sheet.removeRow(row);
            }

            workbook.write(fileOut);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void simulatepay() {
        String cartFile = "D:\\Programming\\ShoppingSystem\\cart.xlsx";
        String productFile = "D:\\Programming\\ShoppingSystem\\products.xlsx";
        String historyFile = "D:\\Programming\\ShoppingSystem\\History.xlsx";

        Scanner scanner = new Scanner(System.in);
        System.out.print("请选择使用的支付方式(1支付宝、2微信、3银行卡):");
        int choice = scanner.nextInt();

        calculateTotalPrice(cartFile, productFile, historyFile);
    }

    // 保存购物历史
    public static void saveHistory(String historyFile, String productId, int num, LocalDateTime dateTime) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("购物历史");

            // 获取当前行数作为新行的索引
            int rowIndex = sheet.getLastRowNum() + 1;

            // 创建新行，并写入数据
            Row row = sheet.createRow(rowIndex);
            Cell cellProductId = row.createCell(0);
            cellProductId.setCellValue(productId);
            Cell cellNum = row.createCell(1);
            cellNum.setCellValue(num);
            Cell cellDateTime = row.createCell(2);
            cellDateTime.setCellValue(dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

            // 保存Excel文件
            try (FileOutputStream fileOut = new FileOutputStream(historyFile)) {
                workbook.write(fileOut);
            }
        } catch (IOException e) {
            System.out.println("保存购物历史时出错：" + e.getMessage());
        }
    }

    // 查看购物历史
    public void history(String historyFile) {
        System.out.println("-----------------------");
        try (Workbook workbook = WorkbookFactory.create(new FileInputStream(historyFile))) {
            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                String productId = row.getCell(0).getStringCellValue();
                int num = (int) row.getCell(1).getNumericCellValue();
                String dateTimeStr = row.getCell(2).getStringCellValue();

                LocalDateTime dateTime = LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

                System.out.println("商品ID：" + productId);
                System.out.println("数量：" + num);
                System.out.println("购买时间：" + dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                System.out.println("-------------------------");
            }
        } catch (IOException e) {
            System.out.println("读取购物历史时出错：" + e.getMessage());
        }
    }
}

