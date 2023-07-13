package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
    private static final String DB_URL = "jdbc:sqlite:users.db";

    //注册
    public boolean register(String Name,String passWord){
        return true;
    }

    //登录
    public boolean login(String Name,String passWord){
        return true;
    }

    //修改密码
    public void changePassword(String password){

    }

    //重置密码
    public void resetPassword(){
        
    }


}
