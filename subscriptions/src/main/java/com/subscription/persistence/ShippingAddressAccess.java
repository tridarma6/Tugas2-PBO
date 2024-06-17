package com.subscription.persistence;

import java.sql.*;
import java.util.ArrayList;

import com.subscription.model.ShippingAddress;

public class ShippingAddressAccess {
    
    public void updateShippingAddress(int customerId, int addressId, ShippingAddress address) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:subscription.db");
            System.out.println("Connected to database");

            String updateSQL = "UPDATE shipping_addresses SET title = ?, line1 = ?, line2 = ?, city = ?, province = ?, postcode = ? WHERE id = ? AND customer_id = ?";
            statement = connection.prepareStatement(updateSQL);
            statement.setString(1, address.getTitle());
            statement.setString(2, address.getLine1());
            statement.setString(3, address.getLine2());
            statement.setString(4, address.getCity());
            statement.setString(5, address.getProvince());
            statement.setString(6, address.getPostcode());
            statement.setInt(7, addressId);
            statement.setInt(8, customerId);
            statement.executeUpdate();

        } catch (SQLException | ClassNotFoundException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            throw new RuntimeException(e);
        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
    }
}