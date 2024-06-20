package com.subscription.persistence;

import java.sql.*;
import java.util.ArrayList;

import com.subscription.model.ShippingAddress;

public class ShippingAddressAccess {
    
    public String updateShippingAddress(int customerId, int addressId, ShippingAddress address) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        String response;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:subscription.db");
            System.out.println("Connected to database");

            String updateSQL = "UPDATE shipping_address SET title = ?, line1 = ?, line2 = ?, city = ?, province = ?, postcode = ? WHERE id = ? AND customer = ?";
            statement = connection.prepareStatement(updateSQL);
            statement.setString(1, address.getTitle());
            statement.setString(2, address.getLine1());
            statement.setString(3, address.getLine2());
            statement.setString(4, address.getCity());
            statement.setString(5, address.getProvince());
            statement.setString(6, address.getPostcode());
            statement.setInt(7, addressId);
            statement.setInt(8, customerId);
            int rowAffected = statement.executeUpdate();
            if (rowAffected > 0) {
                response = "1 row(s) has been affected";
                System.out.println(response);
            }else{
                response = "Failed";
                System.out.println(response);
            }

        } catch (SQLException | ClassNotFoundException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            throw new RuntimeException(e);
        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }

        return response;
    }

    // get shiping address dengan customer id
    public ArrayList<ShippingAddress> getShippingAddressesByCustomerId(int customerId) throws SQLException {
        Connection conn = null;
        PreparedStatement state = null;
        ResultSet result = null;
        ArrayList<ShippingAddress> shippingAddressesList = new ArrayList<>();

        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:subscription.db");
            System.out.println("Connected to the database");

            String query = "SELECT * FROM shipping_address WHERE customer = ?";
            state = conn.prepareStatement(query);
            state.setInt(1, customerId);
            result = state.executeQuery();

            while (result.next()) {
                int id = result.getInt("id");
                String title = result.getString("title");
                String line1 = result.getString("line1");
                String line2 = result.getString("line2");
                String city = result.getString("city");
                String province = result.getString("province");
                String postcode = result.getString("postcode");

                ShippingAddress address = new ShippingAddress();
                address.setId(id);
                address.setCustomer(customerId);
                address.setTitle(title);
                address.setLine1(line1);
                address.setLine2(line2);
                address.setCity(city);
                address.setProvince(province);
                address.setPostcode(postcode);

                shippingAddressesList.add(address);
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            throw new RuntimeException(e);
        } finally {
            if (result != null) result.close();
            if (state != null) state.close();
            if (conn != null) conn.close();
        }
        return shippingAddressesList;
    }
}