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

        boolean exit=true;
        while(exit){
            System.out.println("\n欢迎进入购物管理系统");
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
                    System.out.print("是否确认退出购物管理系统（T）：");
                    String s=scanner.next();
                    if(s.equals("T")){
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
                    admin.resetUserPassword();
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
                    admin.listCustomers();
                    break;
                case 2:
                    System.out.print("请输入要删除客户的用户名：");
                    String deleteUsername=scanner.next();
                    admin.deleteConsumer(deleteUsername);
                    break;
                case 3:
                    System.out.print("请输入要查询客户的用户名：");
                    String checkUsername=scanner.next();
                    admin.checkConsumer(checkUsername);
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
                    System.out.print("请输入要添加商品的名称：");
                    String productName=scanner.next();
                    System.out.print("请输入要添加商品的价格：");
                    double productPrice=scanner.nextDouble();
                    product.addProduct(productName,productPrice);
                    break;
                case 3:
                    System.out.print("请输入要修改的商品名：");
                    String modifyName=scanner.next();
                    System.out.print("请输入新的价格：");
                    double modifyPrice=scanner.nextDouble();
                    product.modifyProduct(modifyName,modifyPrice);
                    break;
                case 4:
                    System.out.print("请输入要删除的商品名：");
                    String deletName=scanner.next();
                    product.deleteProduct(deletName);
                    break;
                case 5:
                    System.out.print("请输入要查询的商品名：");
                    String checkName=scanner.next();
                    product.checkProduct(checkName);
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
        System.out.print("用户注册1，用户登录2：");
        int n=scanner.nextInt();
        if(n==1){
            System.out.println("\n=========用户注册=========");
            System.out.print("请输入用户名：");
            String Name=scanner.next();
            System.out.print("请设置密码：");
            String Password=scanner.next();

            if(user.register(Name,Password)){
                System.out.println("用户注册成功！");
                userMenu();
            }
            else{
                System.out.print("用户名已存在，请重新输入！");
            }
        }
        else if(n==2){
            System.out.println("\n=========用户登录=========");
            System.out.print("请输入用户名：");
            String userName=scanner.next();
            System.out.print("请输入密码：");
            String userPassword=scanner.next();

            if(user.login(userName,userPassword)){
                userMenu();
            }
            else{
                System.out.println("用户名或密码错误！");
            }
        }
        else{
            System.out.print("请重新输入正确的编号：");
        }
    }

    private static void userMenu(){
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
            System.out.println("|======1.修改密码========|");
            System.out.println("|======2.重置密码========|");
            System.out.println("|======0.返回上一级菜单===|");
            System.out.print("请输入操作的编号：");
            int num=scanner.nextInt();
            scanner.nextLine();

            switch(num){
                case 1:
                    System.out.print("请输入新密码：");
                    String s1=scanner.next();
                    System.out.print("请再次输入新密码：");
                    String s2=scanner.next();
                    if(s1.equals(s2)){
                        user.changePassword(s2);
                        System.out.println("密码修改成功！");
                    }
                    break;
                case 2:
                    user.resetPassword();
                    System.out.println("密码重置成功！");
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
            System.out.println("|======1.将商品加入购物车==========|");
            System.out.println("|======2.从购物车中移除商品========|");
            System.out.println("|======3.修改购物车中的商品========|");
            System.out.println("|======4.模拟结账=================|");
            System.out.println("|======5.查看购物历史=============|");
            System.out.println("|======0.返回上一级菜单===========|");
            System.out.print("请输入操作的编号：");
            int num=scanner.nextInt();
            scanner.nextLine();

            switch(num){
                case 1:
                    System.out.print("请输入要加入购物车的商品名：");
                    String addName=scanner.next();
                    System.out.print("请输入要加入商品的数量：");
                    int n=scanner.nextInt();
                    cart.addToCart(addName,n);
                    break;
                case 2:
                    System.out.print("请输入要移除的商品名：");
                    String removeName=scanner.next();
                    cart.removeFromCart(removeName);
                    break;
                case 3:
                    System.out.print("请输入要修改的商品名");
                    String modifyName=scanner.next();
                    System.out.print("请输入新的数量：");
                    int q=scanner.nextInt();
                    cart.modifyCart(modifyName,q);
                    break;
                case 4:
                    cart.checkout();
                    break;
                case 5:
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