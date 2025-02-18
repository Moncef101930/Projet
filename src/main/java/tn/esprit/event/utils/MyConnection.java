package tn.esprit.event.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyConnection {
    private String URL="jdbc:mysql://localhost:3306/event";
    private String USER="root";
    private String PASSWORD="";
    private Connection cnx;
    private static MyConnection instance;
    private MyConnection() {
        try {
            cnx= DriverManager.getConnection(URL,USER,PASSWORD);
            System.out.println("Cnx etablie ...");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("ERREUR");
            throw new RuntimeException(e);
        }
    }
    public static MyConnection getInstance(){
        if(instance ==null){
            instance=new MyConnection();
        }else{
            System.out.println("Deja connecter");
        }
        return instance;
    }

    public Connection getCnx() {
        return cnx;
    }
}
