package com.sqldemo;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class MySqlConnection1 {
	String url = "jdbc:mysql://localhost:3306/employeePayrollService";
    String username ="root";
    String password ="rootpassword";

    public Connection getConnection(){
        Connection connection = null;
        try {
            connection =  DriverManager.getConnection(url,username,password);
            return connection;

        }catch (SQLException e ){
            e.printStackTrace();
        }
        return null;
    }

}

