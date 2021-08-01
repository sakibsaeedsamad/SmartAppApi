/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author SCUBE
 */
public class DBConnectionHandler {

    private static String driverName;
    private static String myDatabaseUrl = "jdbc:oracle:thin:@localhost:1521:xe";
    private static String myDatabaseUser="system";
    private static String myDatabasePassword="root";

    
    Connection con = null;
    
    public static Connection getConVentionalConnection() {
        Connection con = null;
    try{
//        Class.forName("com.mysql.jdbc.Driver");
//        Connection c =DriverManager.getConnection("jdbc:mysql://localhost:3306/servletdatabase", "root", "root");

           Class.forName("oracle.jdbc.driver.OracleDriver");
           con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","root");
    } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return con;
    }
    
    public static void releaseConnection(Connection con) {
        try {
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(DBConnectionHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /*public static void readConnectionProperties() {
        Properties prop = new Properties();
        InputStream propFile = null;
        try {
            String filename = "util/DBConnection.properties";
            propFile = DBConnectionHandler.class.getClassLoader().getResourceAsStream(filename);
            if (propFile == null) {
                System.out.println("Sorry, unable to find " + filename);
            }
            //load a properties file from class path, inside static method
            prop.load(propFile);

            driverName = prop.getProperty("driverName");
            myDatabaseUrl = prop.getProperty("myDatabaseUrl");
            myDatabaseUser = prop.getProperty("myDatabaseUser");
            myDatabasePassword = prop.getProperty("myDatabasePassword");

        } catch (FileNotFoundException ex) {
            ex.getMessage();
        } catch (IOException ex) {
            ex.getMessage();
        } finally {
            if (propFile != null) {
                try {
                    propFile.close();
                } catch (IOException e) {
                    e.getMessage();
                }
            }
        }
    }*/
    
    
}