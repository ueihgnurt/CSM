package database;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectSqlServer 
{
    String driverName;
    String url;
    String dbname;
    String username;
    String password;
    static Connection connection;
    
    public ConnectSqlServer()
    {
        connection = null;
    }
    
    private void CreateConnection()
    {
        try{
            driverName = "com.mysql.cj.jdbc.Driver";
            url = "jdbc:mysql://localhost:3306/";
            dbname = "csm";
            username = "root";
            password = "trunghieu_367";
            Class.forName(driverName);
            connection = DriverManager.getConnection(url+dbname, username, password);
            
        }catch(Exception ex){
        }
    }
    
    public Connection getConnetion()
    {
        if(connection == null)
        {
            CreateConnection();
        }
        return connection;
    }
            
    public void closeConnection() throws SQLException
    {
        if(connection != null)
        {
            connection.close();
        }
    }
}