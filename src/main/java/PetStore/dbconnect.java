package Lect19;

import org.testng.annotations.Test;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class dbconnect {

    @Test
    public static void main(String[] args) {
        String url = "jdbc:sqlserver://localhost:1433;databaseName=master;user=sa;password=lukako08;encrypt=true;trustServerCertificate=true";
        try (Connection connection = DriverManager.getConnection(url)) {
            System.out.println("Connected to SQL Server successfully!");
        } catch (SQLException e) {
            System.err.println("Connection failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}