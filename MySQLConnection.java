package com.company;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnection {
    public static Connection myConnection;
    public static Connection getConnection() throws ClassNotFoundException, SQLException {

        String dataBaseName = "cameroon";
        String userName = "root";
        String password = "telmah1";

        Class.forName("com.mysql.cj.jdbc.Driver");

        myConnection = DriverManager.getConnection("jdbc:mysql://localhost/"+dataBaseName,userName,password);

        return myConnection;
    }
}
