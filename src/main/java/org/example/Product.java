package org.example;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Product {
    Scanner scanner = new Scanner(System.in);

    //列出商品信息
    public void listProducts() {
        System.out.println("---------商品信息---------");
        try (Workbook workbook = WorkbookFactory.create(new FileInputStream("D:\\Programming\\ShoppingSystem\\products.xlsx"))) {
            Sheet sheet = workbook.getSheetAt(0); // 假设商品信息在第一个Sheet中

            for (Row row : sheet) {
                String productId = row.getCell(0).getStringCellValue();
                String productname = row.getCell(1).getStringCellValue();
                String origin = row.getCell(2).getStringCellValue();
                String dateTime = row.getCell(3).getStringCellValue();
                String size = row.getCell(4).getStringCellValue();
                double inprice = row.getCell(5).getNumericCellValue();
                double outprice = row.getCell(6).getNumericCellValue();
                int num = (int) row.getCell(7).getNumericCellValue();

                System.out.println("商品编号：" + productId);
                System.out.println("商品名称：" + productname);
                System.out.println("生产厂家：" + origin);
                System.out.println("生产日期：" + dateTime);
                System.out.println("型号：" + size);
                System.out.println("进价：" + inprice);
                System.out.println("零售价格：" + outprice);
                System.out.println("数量：" + num);
                System.out.println("-------------------------");
            }
        } catch (IOException e) {
            System.out.println("读取商品信息时出错：" + e.getMessage());
        } catch (InvalidFormatException e) {
            System.out.println("非法的Excel文件格式：" + e.getMessage());
        }
    }

    //添加商品信息
    public void addProduct() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("请输入商品编号：");
        String productID = scanner.nextLine();
        System.out.print("请输入商品名称：");
        String productname = scanner.nextLine();
        System.out.print("请输入生产厂家：");
        String origin = scanner.nextLine();
        System.out.print("请输入生产日期：");
        String datetime = scanner.nextLine();
        System.out.print("请输入型号：");
        String size = scanner.nextLine();
        System.out.print("请输入进价：");
        double inprice = scanner.nextDouble();
        System.out.print("请输入零售价格：");
        double outprice = scanner.nextDouble();
        System.out.print("请输入数量：");
        int num = scanner.nextInt();

        try (Workbook workbook = WorkbookFactory.create(new FileInputStream("D:\\Programming\\ShoppingSystem\\products.xlsx"));
             FileOutputStream fileOut = new FileOutputStream("D:\\Programming\\ShoppingSystem\\products.xlsx")) {

            Sheet sheet = workbook.getSheetAt(0); // 假设商品信息在第一个Sheet中
            int rowCount = sheet.getLastRowNum();

            Row newRow = sheet.createRow(rowCount + 1);
            newRow.createCell(0).setCellValue(productID);
            newRow.createCell(1).setCellValue(productname);
            newRow.createCell(2).setCellValue(origin);
            newRow.createCell(3).setCellValue(datetime);
            newRow.createCell(4).setCellValue(size);
            newRow.createCell(5).setCellValue(inprice);
            newRow.createCell(6).setCellValue(outprice);
            newRow.createCell(7).setCellValue(num);

            workbook.write(fileOut);
            System.out.println("添加成功！");
        } catch (IOException e) {
            System.out.println("保存商品信息时出错：" + e.getMessage());
        } catch (InvalidFormatException e) {
            System.out.println("非法的Excel文件格式：" + e.getMessage());
        }
    }

    //修改商品信息
    public void modifyProduct(String id) {
        List<String[]> rows = new ArrayList<>();
        scanner.nextLine();
        try (Workbook workbook = WorkbookFactory.create(new FileInputStream("D:\\Programming\\ShoppingSystem\\products.xlsx"))) {
            Sheet sheet = workbook.getSheetAt(0); // 假设商品信息在第一个Sheet中

            for (Row row : sheet) {
                String productId = row.getCell(0).getStringCellValue();

                if (id.equals(productId)) {
                    System.out.print("请输入新的零售价格: ");
                    double newprice = scanner.nextDouble();
                    row.getCell(6).setCellValue(newprice);
                    System.out.println("已重置零售价格！");
                }

                String[] line = new String[8];
                for (int i = 0; i < 8; i++) {
                    Cell cell = row.getCell(i);
                    if (cell != null) {
                        if (cell.getCellType() == CellType.NUMERIC) {
                            line[i] = String.valueOf(cell.getNumericCellValue());
                        } else {
                            line[i] = cell.getStringCellValue();
                        }
                    } else {
                        line[i] = "";
                    }
                }
                rows.add(line);
            }
        } catch (IOException e) {
            System.out.println("读取商品信息时出错：" + e.getMessage());
            return;
        } catch (InvalidFormatException e) {
            System.out.println("非法的Excel文件格式：" + e.getMessage());
            return;
        }

        try (Workbook workbook = WorkbookFactory.create(new FileInputStream("D:\\Programming\\ShoppingSystem\\products.xlsx"));
             FileOutputStream fileOut = new FileOutputStream("D:\\Programming\\ShoppingSystem\\products.xlsx")) {

            Sheet sheet = workbook.getSheetAt(0); // 假设商品信息在第一个Sheet中

            for (int i = 0; i < rows.size(); i++) {
                Row row = sheet.getRow(i);
                String[] line = rows.get(i);
                for (int j = 0; j < line.length; j++) {
                    Cell cell = row.getCell(j);
                    if (cell != null) {
                        if (cell.getCellType() == CellType.NUMERIC) {
                            double value = Double.parseDouble(line[j]);
                            cell.setCellValue(value);
                        } else {
                            cell.setCellValue(line[j]);
                        }
                    }
                }
            }

            workbook.write(fileOut);
        } catch (IOException e) {
            System.out.println("保存商品信息时出错：" + e.getMessage());
        } catch (InvalidFormatException e) {
            System.out.println("非法的Excel文件格式：" + e.getMessage());
        }
    }

    //删除商品信息
    public void deleteProduct(String id) {
        try (Workbook workbook = WorkbookFactory.create(new FileInputStream("D:\\Programming\\ShoppingSystem\\products.xlsx"));
             FileOutputStream fileOut = new FileOutputStream("D:\\Programming\\ShoppingSystem\\products.xlsx")) {

            Sheet sheet = workbook.getSheetAt(0); // 假设商品信息在第一个Sheet中
            Iterator<Row> iterator = sheet.iterator();

            while (iterator.hasNext()) {
                Row row = iterator.next();
                Cell cell = row.getCell(0);
                if (cell != null && cell.getCellType() == CellType.STRING) {
                    String productId = cell.getStringCellValue();
                    if (productId.equals(id)) {
                        iterator.remove();
                        System.out.println("删除商品信息成功！");
                    }
                }
            }

            workbook.write(fileOut);
        } catch (IOException e) {
            System.out.println("执行删除操作时出错：" + e.getMessage());
        } catch (InvalidFormatException e) {
            System.out.println("非法的Excel文件格式：" + e.getMessage());
        }
    }

    //查询商品信息
    public void checkProduct(String checkname, String productid) {
        System.out.println("-----------------------");
        try (Workbook workbook = WorkbookFactory.create(new FileInputStream("D:\\Programming\\ShoppingSystem\\products.xlsx"))) {
            Sheet sheet = workbook.getSheetAt(0); // 假设商品信息在第一个Sheet中

            for (Row row : sheet) {
                String productId = row.getCell(0).getStringCellValue();
                String productname = row.getCell(1).getStringCellValue();

                if ((productid.equals(productId)) || (productname.equals(checkname))) {
                    String origin = row.getCell(2).getStringCellValue();
                    String dateTime = row.getCell(3).getStringCellValue();
                    String size = row.getCell(4).getStringCellValue();
                    double inprice = row.getCell(5).getNumericCellValue();
                    double outprice = row.getCell(6).getNumericCellValue();
                    int num = (int) row.getCell(7).getNumericCellValue();

                    System.out.println("商品编号：" + productId);
                    System.out.println("商品名称：" + productname);
                    System.out.println("生产厂家：" + origin);
                    System.out.println("生产日期：" + dateTime);
                    System.out.println("型号：" + size);
                    System.out.println("进价：" + inprice);
                    System.out.println("零售价格：" + outprice);
                    System.out.println("数量：" + num);
                    System.out.println("-------------------------");
                }
            }
        } catch (IOException e) {
            System.out.println("读取商品信息时出错：" + e.getMessage());
        } catch (InvalidFormatException e) {
            System.out.println("非法的Excel文件格式：" + e.getMessage());
        }
    }

