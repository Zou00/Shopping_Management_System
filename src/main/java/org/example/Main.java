package org.example;

import java.util.Scanner;

public class Main{
    private static Scanner scanner=new Scanner(System.in);
    private static Admin admin=new Admin();
    private static User user=new User();
    private static Product product=new Product();
    private static Cart cart=new Cart();
    
    public static void main(String[] args){

        adminDatabaseInitializer databaseInitializer1 = new adminDatabaseInitializer();
        databaseInitializer1.adminInitializeDatabase();

        userDatabaseInitializer databaseInitializer2 = new userDatabaseInitializer();
        databaseInitializer2.userInitializeDatabase();

        DatabaseInitializer databaseInitializer3 = new DatabaseInitializer();
        databaseInitializer3.main();

        boolean exit=true;
        while(exit){
            System.out.println("\n-----欢迎进入购物管理系统-----");
            System.out.println("\n==========身份选择==========");
            System.out.println("|=========1.管理员=========|");
            System.out.println("|=========2.用户 ==========|");
            System.out.println("|=========0.退出 ==========|");
            System.out.print("请输入操作编号：");
            int num=scanner.nextInt();
            scanner.nextLine();

            switch(num){
                case 1:
                    admin.login();
                    break;
                case 2:
                    Consumer();
                    break;
                case 0:
                    System.out.print("是否确认退出购物管理系统（t）：");
                    String s=scanner.next();
                    if(s.equals("t")){
                        System.out.println("您已成功退出购物管理系统!");
                        exit=false;
                    }
                    break;
                default:
                    System.out.println("请输入正确的编号!");
            }
        }
        System.out.println("感谢使用购物管理系统！");   
    }

    public static void adminMenu(){    
        boolean exit=true;
        while(exit){
            System.out.println("\n==========管理员菜单========");
            System.out.println("|========1.密码管理========|");
            System.out.println("|========2.客户管理========|");
            System.out.println("|========3.商品管理========|");
            System.out.println("|========0.退出登录========|");
            System.out.print("请输入操作编号：");
            int num=scanner.nextInt();
            scanner.nextLine();

            switch(num){
                case 1:
                    adminPasswordManagement();
                    break;
                case 2:
                    consumerManagement();
                    break;
                case 3:
                    productsManagement();
                    break;
                case 0:
                    System.out.println("您已成功退出管理员界面!");
                    exit=false;
                    break;
                default:
                    System.out.println("请输入正确的编号!");
            }
        }
    }
    
    private static void adminPasswordManagement(){
        boolean exit=true;
        while(exit){
            System.out.println("\n==========密码管理==========");
            System.out.println("|=======1.修改自身密码======|");
            System.out.println("|=======2.重置用户密码======|");
            System.out.println("|=======0.返回上一级菜单====|");
            System.out.print("请输入操作编号：");
            int num=scanner.nextInt();
            scanner.nextLine();

            switch(num){
                case 1:
                    admin.changePassword();
                    break;
                case 2:
                    user.resetUserPassword();
                    break;
                case 0:
                    exit=false;
                    break;
                default:
                    System.out.println("请输入正确的编号!");
            }
        }
    }

    private static void consumerManagement(){
        boolean exit=true;
        while(exit){
            System.out.println("\n=========客户管理==========");
            System.out.println("|======1.列出客户信息=====|");
            System.out.println("|======2.删除客户信息=====|");
            System.out.println("|======3.查询客户信息=====|");
            System.out.println("|======0.返回上一级菜单===|");
            System.out.print("请输入操作的编号：");
            int num=scanner.nextInt();
            scanner.nextLine();

            switch (num){
                case 1:
                    user.listCustomers();
                    break;
                case 2:
                    user.deleteConsumer();
                    break;
                case 3:
                    user.checkConsumer();
                    break;
                case 0:
                    exit=false;
                    break;
                default:
                    System.out.println("请输入正确的编号!");
            }
        }
    }

    private static void productsManagement(){
        boolean exit=true;
        while(exit){
            System.out.println("\n=========商品管理==========");
            System.out.println("|======1.列出商品信息=====|");
            System.out.println("|======2.添加商品信息=====|");
            System.out.println("|======3.修改商品信息=====|");
            System.out.println("|======4.删除商品信息=====|");
            System.out.println("|======5.查询商品信息=====|");
            System.out.println("|======0.返回上一级菜单===|");
            System.out.print("请输入操作的编号：");
            int num=scanner.nextInt();
            scanner.nextLine();

            switch (num){
                case 1:
                    product.listProducts();
                    break;
                case 2:
                    product.addProduct();
                    break;
                case 3:
                    product.modifyProduct();
                    break;
                case 4:
                    product.deleteProduct();
                    break;
                case 5:
                    product.checkProduct();
                    break;
                case 0:
                    exit=false;
                    break;
                default:
                    System.out.println("请输入正确的编号!");
            }
        }
    }

    private static void Consumer(){
        boolean exit=true;
        while(exit){
            System.out.println("\n============用户==========");
            System.out.println("|------1.注册-------------|");
            System.out.println("|------2.登录-------------|");
            System.out.println("|------3.忘记密码---------|");
            System.out.println("|------0.返回上一级菜单----|");
            System.out.print("请输入操作的编号：");
            int n=scanner.nextInt();
            scanner.nextLine();
        
            switch(n){
                case 1:
                    user.register();
                    break;
                case 2:
                    user.login();
                    break;
                case 3:
                    user.resetPassword();
                    break;
                case 0:
                    exit=false;
                    break;
                default:
                    System.out.println("请输入正确的编号!");
            }
        }
    }

    public static void userMenu(){
        boolean exit=true;
        while(exit){
            System.out.println("\n=========用户菜单=========");
            System.out.println("|========1.密码管理======|");
            System.out.println("|========2.购物==========|");
            System.out.println("|========0.退出登录======|");
            System.out.print("请输入操作的编号：");
            int num=scanner.nextInt();
            scanner.nextLine();

            switch(num){
                case 1:
                    userPasswordManagement();
                    break;
                case 2:
                    shopping();
                    break;
                case 0:
                    System.out.println("您已退出用户登录！");
                    exit=false;
                    break;
                default:
                    System.out.println("请输入正确的编号!");
            }
        }
    }

    private static void userPasswordManagement(){
        boolean exit=true;
        while(exit){
            System.out.println("\n=========密码管理=========");
            System.out.println("|======1.修改密码=========|");
            System.out.println("|======0.返回上一级菜单===|");
            System.out.print("请输入操作的编号：");
            int num=scanner.nextInt();
            scanner.nextLine();

            switch(num){
                case 1:
                    user.changePassword();
                    break;
                case 0:
                    exit=false;
                    break;
                default:
                    System.out.println("请输入正确的编号!");
            }
        }
    }

    private static void shopping(){
        boolean exit=true;
        while(exit){
            System.out.println("\n===============购物================");
            System.out.println("|======1.查看商品=================|");
            System.out.println("|======2.将商品加入购物车==========|");
            System.out.println("|======3.从购物车中移除商品========|");
            System.out.println("|======4.修改购物车中的商品========|");
            System.out.println("|======5.查看购物车===============|");
            System.out.println("|======6.模拟结账=================|");
            System.out.println("|======7.查看购物历史=============|");
            System.out.println("|======0.返回上一级菜单===========|");
            System.out.print("请输入操作的编号：");
            int num=scanner.nextInt();
            scanner.nextLine();

            switch(num){
                case 1:
                    product.listProducts();
                    break;
                case 2:
                    System.out.println("\n--------添加商品至购物车--------");
                    System.out.print("请输入要加入购物车的商品名：");
                    String addName=scanner.next();
                    if(product.addToCart(addName)){
                        cart.addToCart(addName);
                    }
                    break;
                case 3:
                    cart.removeFromCart();
                    break;
                case 4:
                    cart.modifyCart();
                    break;
                case 5:
                    cart.list();
                    break;
                case 6:
                    cart.checkout();
                    break;
                case 7:
                    cart.shoppingHistory();
                    break;
                case 0:
                    exit=false;
                    break;
                default:
                    System.out.println("请输入正确的编号!");
            }
        }
    }
}    