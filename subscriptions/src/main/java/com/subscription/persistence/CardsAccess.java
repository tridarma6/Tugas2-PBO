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
            Class.forName("org.sqlite.JBDC");
            conn = DriverManager.getConnection("jbdc:sqlite:subscription.db");
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
}