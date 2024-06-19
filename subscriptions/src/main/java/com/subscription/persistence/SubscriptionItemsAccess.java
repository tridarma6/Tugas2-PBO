package com.subscription.persistence;

import java.sql.*;

import com.subscription.model.SubscriptionItems;

public class SubscriptionItemsAccess{

    public String addNewSubscriptionItems(SubscriptionItems subscriptionItems) throws SQLException{
        Connection conn = null;
        PreparedStatement state = null;
        String response;
        try {
            Class.forName("org.sqlite.JDBC");
            System.out.println("has connected to the database");
            conn = DriverManager.getConnection("jdbc:sqlite:subscription.db");
            state = conn.prepareStatement("INSERT INTO subscription_items VALUES(?,?,?,?,?,?);");
            System.out.println("Inserting data to table subscriptions");
            state.setInt(1, subscriptionItems.getSubscriptionId());
            state.setInt(2, subscriptionItems.getItemId());
            state.setInt(3, subscriptionItems.getQuantity());
            state.setInt(4, subscriptionItems.getPrice());
            state.setInt(5, subscriptionItems.getAmount());
            
            int rowsAffected = state.executeUpdate();
            if(rowsAffected > 0) {
                response = rowsAffected + " row(s) has been affected";
                System.out.println(response);
            }else{
                response = "No rows have been added";
                System.out.println(response);
            }
        }catch(SQLException | ClassNotFoundException e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            throw new RuntimeException(e);
        }finally{
            if(state != null) state.close();
            if(conn != null) conn.close();
        }
        return response;
    }
}