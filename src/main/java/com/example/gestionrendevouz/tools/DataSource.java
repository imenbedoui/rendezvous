package com.example.gestionrendevouz.tools;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSource {
    private String url="jdbc:mysql://127.0.0.1:3306/imen";
    private String login="root";
    private String pwd="";
    public static DataSource instance;
    Connection cnx;
    private DataSource(){
        try{
            cnx= DriverManager.getConnection(url,login,pwd);
            System.out.println("la connexion avec la base de données est réussite");
        }catch (SQLException e){
            System.out.println(e.getMessage());
            System.out.println("noooon");
        }
    }
    public Connection getCnx() {
        return cnx;
    }
    public static DataSource getInstance() {
        if (instance==null){
            return new DataSource();
        }
        return instance;
    }
}