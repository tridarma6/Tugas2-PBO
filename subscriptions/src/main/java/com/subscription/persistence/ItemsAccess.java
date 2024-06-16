package com.subscription.persistence;

import java.sql.*;
import java.util.ArrayList;

import com.subscription.model.Items;
public class ItemsAccess{

    public ArrayList<Items> getAllItems() throws SQLException{
        Connection conn = null;
        PreparedStatement state = null;
        ResultSet result = null;
        ArrayList<Items> itemsList = new ArrayList<>();

        try {
            Class.forName("org.sqlite.JBDC");
            conn = DriverManager.getConnection("jbdc:sqlite:subscription.db");
            System.out.println("has connected to database");
            state = conn.prepareStatement("SELECT * FROM items");
            result = state.executeQuery();

            while(result.next()){
                int id = result.getInt("id");
                String name = result.getString("name");
                int price = result.getInt("price");
                String type = result.getString("type");
                int is_active = result.getInt("is_active");

                Items item = new Items();
                item.setId(id);
                item.setName(name);
                item.setPrice(price);
                item.setType(type);
                item.setIs_active(is_active);

                itemsList.add(item);
            }
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            throw new RuntimeException(e);
        }finally{
            if(result != null) result.close();
            if(state != null) state.close();
            if(conn != null) conn.close();
        }

        return itemsList;
    } 

    public Items getItemById(int id) throws SQLException{
        Connection conn = null;
        PreparedStatement state = null;
        ResultSet result = null;
        Items item = new Items();

        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:subscription.db");
            System.out.println("has connected to database");

            state = conn.prepareStatement("SELECT * FROM items WHERE id = ?");
            state.setInt(1, id);
            result = state.executeQuery();

            while (result.next()) {
                int idt = result.getInt("id");
                String name = result.getString("name");
                int price = result.getInt("price");
                String type = result.getString("type");
                int is_active = result.getInt("is_active");

                item.setId(idt);
                item.setName(name);
                item.setPrice(price);
                item.setType(type);
                item.setIs_active(is_active);
            }
        }catch (SQLException | ClassNotFoundException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            throw new RuntimeException(e);
        }finally{
            if(result != null) result.close();
            if(state != null) state.close();
            if(conn != null) conn.close();
        }

        return item;
    }

    public ArrayList<Items> getItemsByIsActive(int is_active) throws SQLException{
        Connection conn = null;
        PreparedStatement state = null;
        ResultSet result = null;
        ArrayList<Items> listItems = new ArrayList<>();

        try {
            Class.forName("org.sqlite.JBDC");
            conn = DriverManager.getConnection("jbdc:sqlite:subscription.db");
            System.out.println("has connected to the database");

            state = conn.prepareStatement("SELECT * FROM items WHERE is_active = ?");
            state.setInt(1, is_active);
            result = state.executeQuery();

            while (result.next()) {
                Items item = new Items();
                int idt = result.getInt("id");
                String name = result.getString("name");
                int price = result.getInt("price");
                String type = result.getString("type");
                int active = result.getInt("is_active");

                item.setId(idt);
                item.setName(name);
                item.setPrice(price);
                item.setType(type);
                item.setIs_active(active);

                listItems.add(item);
            }
        }catch (SQLException | ClassNotFoundException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            throw new RuntimeException(e);
        }finally{
            if(result != null) result.close();
            if(state != null) state.close();
            if(conn != null) conn.close();
        }

        return listItems;
    }
}