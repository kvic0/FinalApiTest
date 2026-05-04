package PetStore; // თქვენი პაკეტის სახელი

import java.sql.*;

public class DatabaseUtils {
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=PetstoreDB;encrypt=true;trustServerCertificate=true;";
    private static final String USER = "sa";
    private static final String PASS = "lukako08";


    public static OrderDataLombok getOrderFromDb(int id) {
        OrderDataLombok order = new OrderDataLombok();
        String query = "SELECT * FROM OrdersData WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                order.setId(rs.getInt("id"));
                order.setPetId(rs.getInt("petId"));
                order.setQuantity(rs.getInt("quantity"));
                order.setShipDate(rs.getString("shipDate"));
                order.setStatus(rs.getString("status"));
                order.setComplete(rs.getBoolean("complete"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return order;
    }

}