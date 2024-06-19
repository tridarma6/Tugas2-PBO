package com.subscription.persistence;

import java.sql.*;
import java.util.ArrayList;

import com.subscription.model.Cards;


public class CardsAccess{
    public ArrayList<Cards> getCardByCustomerId(int id) throws SQLException{
        Connection conn = null;
        PreparedStatement state = null;
        ResultSet result = null;
        ArrayList<Cards> listCards = new ArrayList<>();
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:subscription.db");
            System.out.println("has connected to the database");
            state = conn.prepareStatement("SELECT * FROM cards WHERE customer = ?");
            state.setInt(1, id);
            result = state.executeQuery();

            while (result.next()) {
                Cards cards = new Cards();
                cards.setId(result.getInt("id"));
                cards.setCustomer(result.getInt("customer"));
                cards.setCard_type(result.getString("card_type"));
                cards.setMasked_number(result.getString("masked_number"));
                cards.setExpiry_month(result.getInt("expiry_month"));
                cards.setExpiry_year(result.getInt("expiry_year"));
                cards.setStatus(result.getString("status"));
                cards.setIs_primary(result.getInt("id_primary"));

                listCards.add(cards);
            }
        }catch(SQLException | ClassNotFoundException e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            throw new RuntimeException(e);
        }finally{
            if(result != null) result.close();
            if(state != null) state.close();
            if(conn != null) conn.close();
        }
        return listCards;
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
            String selectQuery = "SELECT is_primary FROM cards WHERE id = ? AND customer_id = ?";
            selectState = conn.prepareStatement(selectQuery);
            selectState.setInt(1, cardId);
            selectState.setInt(2, customerId);
            ResultSet rs = selectState.executeQuery();

            if (rs.next()) {
                int isPrimary = rs.getInt("is_primary");
                if (isPrimary == 0) {
                    // Hapus Card jika tidak primary
                    String deleteQuery = "DELETE FROM cards WHERE id = ? AND customer_id = ?";
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