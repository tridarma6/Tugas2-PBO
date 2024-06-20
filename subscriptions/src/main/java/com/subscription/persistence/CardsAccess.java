package com.subscription.persistence;

import java.sql.*;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.subscription.model.Cards;


public class CardsAccess{
    public JSONObject getCustomerAndCardsByCustomerId(int id) throws SQLException {
        Connection conn = null;
        PreparedStatement state = null;
        ResultSet result = null;
        JSONObject customerRecord = new JSONObject();
        JSONArray listCards = new JSONArray();
        
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:subscription.db");
            System.out.println("has connected to the database");
            
            String query = "SELECT c.*, cu.first_name, cu.last_name, cu.email, cu.phone_number " +
                        "FROM cards c " +
                        "JOIN customers cu ON cu.id = c.customer " +
                        "WHERE cu.id = ?";
            state = conn.prepareStatement(query);
            state.setInt(1, id);
            result = state.executeQuery();
            
            boolean customerDetailsSet = false;
            while (result.next()) {
                if (!customerDetailsSet) {
                    customerRecord.put("id", result.getInt("customer"));
                    customerRecord.put("first_name", result.getString("first_name"));
                    customerRecord.put("last_name", result.getString("last_name"));
                    customerRecord.put("email", result.getString("email"));
                    customerRecord.put("phone_number", result.getString("phone_number"));
                    customerDetailsSet = true;
                }
                
                JSONObject cardJson = new JSONObject();
                cardJson.put("id", result.getInt("id"));
                cardJson.put("customer", result.getInt("customer"));
                cardJson.put("card_type", result.getString("card_type"));
                cardJson.put("masked_number", result.getString("masked_number"));
                cardJson.put("expiry_month", result.getInt("expiry_month"));
                cardJson.put("expiry_year", result.getInt("expiry_year"));
                cardJson.put("status", result.getString("status"));
                cardJson.put("is_primary", result.getInt("is_primary"));
                
                listCards.put(cardJson);
            }
            
            customerRecord.put("cards", listCards);
            
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            throw new RuntimeException(e);
        } finally {
            if (result != null) result.close();
            if (state != null) state.close();
            if (conn != null) conn.close();
        }
        return customerRecord;
    }


    //  menghapus informasi kartu kredit pelanggan jika is_primary bernilai false
    public String deleteCard(int customerId, int cardId) throws SQLException {
        Connection conn = null;
        PreparedStatement selectState = null;
        PreparedStatement deleteState = null;
        String response;

        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:subscription.db");
            System.out.println("Connected to the database");

            // Cek jika kartu adalah primary
            String selectQuery = "SELECT is_primary FROM cards WHERE id = ? AND customer = ?";
            selectState = conn.prepareStatement(selectQuery);
            selectState.setInt(1, cardId);
            selectState.setInt(2, customerId);
            ResultSet rs = selectState.executeQuery();

            if (rs.next()) {
                int isPrimary = rs.getInt("is_primary");
                if (isPrimary == 0) {
                    // Hapus Card jika tidak primary
                    String deleteQuery = "DELETE FROM cards WHERE id = ? AND customer = ?";
                    deleteState = conn.prepareStatement(deleteQuery);
                    deleteState.setInt(1, cardId);
                    deleteState.setInt(2, customerId);
                    int rowsAffected = deleteState.executeUpdate();

                    if (rowsAffected > 0) {
                        response = rowsAffected + " row(s) have been affected";
                        System.out.println(response);
                    } else {
                        response = "No rows have been affected";
                        System.out.println(response);
                    }
                } else {
                    response = "Cannot delete primary card";
                    System.out.println(response);
                }
            } else {
                response = "Card not found";
                System.out.println(response);
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            throw new RuntimeException(e);
        } finally {
            if (selectState != null) selectState.close();
            if (deleteState != null) deleteState.close();
            if (conn != null) conn.close();
        }
        return response;
    }
}