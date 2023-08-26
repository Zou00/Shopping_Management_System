package org.example;

import java.util.Scanner;

public class Main{
    private static Scanner scanner=new Scanner(System.in);
    private static Admin admin=new Admin();
    private static User user=new User();
    private static Product product=new Product();
    private static Cart cart=new Cart();
    private static Pay pay=new Pay();

    public static void main(String[] args){
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
                    adminLogin();
                    break;
                case 2:
                    Consumer();
                    break;
                case 0:
                    System.out.print("是否确认退出购物管理系统（1）：");
                    String s=scanner.next();
                    if(s.equals("1")){
                        System.out.println("您已成功退出购物管理系统!");
                        exit=false;
                    }
                    break;
                default:
                    System.out.print("请重新输入正确的编号：");
            }
        }
        System.out.println("感谢使用购物管理系统！");
    }

    private static void adminLogin(){
        System.out.println("\n=========管理员登录=========");
        System.out.print("请输入用户名：");
        String userName=scanner.next();
        System.out.print("请输入密码：");
        String passWord=scanner.next();

        if(admin.login(userName,passWord))
            adminMenu();
        else
            System.out.println("用户名或密码错误！");
    }

    private static void adminMenu(){
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
                    System.out.print("请重新输入正确的编号：");
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
                    System.out.print("请输入需要重置密码的ID：");
                    String id=scanner.next();
                    admin.resetUserPassword(id);
                    break;
                case 0:
                    exit=false;
                    break;
                default:
                    System.out.print("请重新输入正确的编号：");
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
                    System.out.print("请输入要删除客户的ID:");
                    String userID=scanner.next();
                    System.out.print("请输入要删除客户的用户名：");
                    String deleteUsername=scanner.next();
                    admin.deleteConsumer(userID,deleteUsername);
                    break;
                case 3:
                    System.out.print("请输入要查询客户的用户名：");
                    String checkname=scanner.nextLine();
                    System.out.print("请输入要查询客户的ID：");
                    String userid=scanner.next();
                    admin.checkConsumer(checkname,userid);
                    break;
                case 0:
                    exit=false;
                    break;
                default:
                    System.out.print("请重新输入正确的编号：");
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
                    System.out.print("请输入要修改的商品编号：");
                    String id=scanner.nextLine();
                    product.modifyProduct(id);
                    break;
                case 4:
                    System.out.print("请输入要删除的商品编号：");
                    String productId=scanner.nextLine();
                    product.deleteProduct(productId);
                    break;
                case 5:
                    System.out.print("请输入要查询商品的名称：");
                    String checkname=scanner.nextLine();
                    System.out.print("请输入要查询商品的ID：");
                    String productid=scanner.nextLine();
                    product.checkProduct(checkname,productid);
                    break;
                case 0:
                    exit=false;
                    break;
                default:
                    System.out.print("请重新输入正确的编号：");
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
                    user.userLogin();
                    break;
                case 3:
                    System.out.print("请输入需要重置密码的用户名：");
                    String username=scanner.next();
                    System.out.print("请输入注册时的邮箱：");
                    String email=scanner.next();
                    user.resetPassword(username,email);
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
                    System.out.print("请重新输入正确的编号：");
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
                    System.out.print("请输入原密码：");
                    String password=scanner.next();
                    user.changePassword(password);
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
                    cart.listProducts();
                    break;
                case 2:
                    System.out.println("\n--------添加商品至购物车--------");
                    cart.addToCart();
                    break;
                case 3:
                    System.out.print("请输入要删除的商品编号：");
                    String productId=scanner.nextLine();
                    cart.removeFromCart(productId);
                    break;
                case 4:
                    System.out.print("请输入要修改的商品编号：");
                    String Id=scanner.nextLine();
                    cart.modifyCart(Id);
                    break;
                case 5:
                    cart.list();
                    break;
                case 6:
                    pay.simulatepay();
                    break;
                case 7:
                    pay.History();
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