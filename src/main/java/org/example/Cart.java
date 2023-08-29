package org.example;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.IOException;
import java.util.Scanner;

public class Cart {
    Scanner scanner = new Scanner(System.in);

    //查看商品
    public void listProducts() {
        System.out.println("---------商品信息---------");
        try (Workbook workbook = WorkbookFactory.create(new FileInputStream("D:\\Programming\\ShoppingSystem\\products.xlsx"))) {
            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                String productId = row.getCell(0).getStringCellValue();
                String productname = row.getCell(1).getStringCellValue();
                String origin = row.getCell(2).getStringCellValue();
                String dateTime = row.getCell(3).getStringCellValue();
                String size = row.getCell(4).getStringCellValue();
                double outprice = row.getCell(6).getNumericCellValue();

                System.out.println("商品编号：" + productId);
                System.out.println("商品名称：" + productname);
                System.out.println("生产厂家：" + origin);
                System.out.println("生产日期：" + dateTime);
                System.out.println("型号：" + size);
                System.out.println("零售价格：" + outprice);
                System.out.println("-------------------------");
            }
        } catch (IOException e) {
            System.out.println("读取商品信息时出错：" + e.getMessage());
        } catch (InvalidFormatException e) {
            System.out.println("非法的Excel文件格式：" + e.getMessage());
        }
    }

    // 加入购物车
    public void addToCart() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("请输入商品编号：");
        String productID = scanner.nextLine();
        System.out.print("请输入数量：");
        int num = scanner.nextInt();

        try (Workbook workbook = new XSSFWorkbook(new FileInputStream("D:\\Programming\\ShoppingSystem\\cart.xlsx"))) {
            Sheet sheet = workbook.getSheetAt(0); // 假设购物车信息在第一个Sheet中

            int rowCount = sheet.getLastRowNum(); // 获取行数

            Row row = sheet.createRow(rowCount + 1); // 创建新行
            Cell productIdCell = row.createCell(0); // 商品编号列
            Cell numCell = row.createCell(1); // 数量列

            productIdCell.setCellValue(productID);
            numCell.setCellValue(num);

            FileOutputStream fileOut = new FileOutputStream("D:\\Programming\\ShoppingSystem\\cart.xlsx");
            workbook.write(fileOut);
            fileOut.close();

            System.out.println("添加成功！");
        } catch (IOException e) {
            System.out.println("保存商品信息时出错：" + e.getMessage());
        }
    }

    // 移除商品
    public void removeFromCart(String id) {
        try (Workbook workbook = new XSSFWorkbook(new FileInputStream("D:\\Programming\\ShoppingSystem\\cart.xlsx"))) {
            Sheet sheet = workbook.getSheetAt(0); // 假设购物车信息在第一个Sheet中

            for (Row row : sheet) {
                Cell cell = row.getCell(0); // 商品编号列
                if (cell != null && cell.getStringCellValue().equals(id)) {
                    sheet.removeRow(row);
                    break;
                }
            }

            FileOutputStream fileOut = new FileOutputStream("D:\\Programming\\ShoppingSystem\\cart.xlsx");
            workbook.write(fileOut);
            fileOut.close();

            System.out.println("移除购物车成功！");
        } catch (IOException e) {
            System.out.println("执行删除操作时出错：" + e.getMessage());
        }
    }

    // 修改商品
    public void modifyCart(String id) {
        try (Workbook workbook = new XSSFWorkbook(new FileInputStream("D:\\Programming\\ShoppingSystem\\cart.xlsx"))) {
            Sheet sheet = workbook.getSheetAt(0); // 假设购物车信息在第一个Sheet中

            for (Row row : sheet) {
                Cell cell = row.getCell(0); // 商品编号列
                if (cell != null && cell.getStringCellValue().equals(id)) {
                    System.out.print("请输入新的数量: ");
                    double num = scanner.nextDouble();
                    Cell numCell = row.getCell(1); // 数量列
                    numCell.setCellValue(num);

                    FileOutputStream fileOut = new FileOutputStream("D:\\Programming\\ShoppingSystem\\cart.xlsx");
                    workbook.write(fileOut);
                    fileOut.close();

                    System.out.println("已修改数量！");
                    return;
                }
            }

            System.out.println("未找到要修改的商品编号！");
        } catch (IOException e) {
            System.out.println("读取商品信息时出错：" + e.getMessage());
        }
    }

    // 查看购物车
    public void list() {
        System.out.println("---------购物车---------");

        try (Workbook workbook = new XSSFWorkbook(new FileInputStream("D:\\Programming\\ShoppingSystem\\cart.xlsx"))) {
            Sheet sheet = workbook.getSheetAt(0); // 假设购物车信息在第一个Sheet中

            for (Row row : sheet) {
                Cell productIdCell = row.getCell(0); // 商品编号列
                Cell numCell = row.getCell(1); // 数量列

                String productId = productIdCell.getStringCellValue();
                double num = numCell.getNumericCellValue();

                System.out.println("商品编号：" + productId);
                System.out.println("数量：" + num);
                System.out.println("-------------------------");
            }
        } catch (IOException e) {
            System.out.println("读取商品信息时出错：" + e.getMessage());
        }
    }
}
