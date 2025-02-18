package utile;

import java.sql.*;

public class DataSource {
    private String url="jdbc:mysql://localhost:3306/monprojet";
    private String user="root";
    private String password="";
    private Connection connection;
    private static DataSource instance;

    public DataSource(){
        try{
            connection = DriverManager.getConnection(url,user,password);

        }
        catch(SQLException e){
            throw new RuntimeException(e);
        }
    }
    public static DataSource getInstance(){
        if(instance==null){
            instance=new DataSource();
        }
        return instance;

    }

    public Connection getConnection() {
        return connection;
    }
}
