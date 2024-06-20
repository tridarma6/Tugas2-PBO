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
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:subscription.db");
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

    public ArrayList<Items> getItemsByIsActive() throws SQLException{
        Connection conn = null;
        PreparedStatement state = null;
        ResultSet result = null;
        ArrayList<Items> listItems = new ArrayList<>();

        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:subscription.db");
            System.out.println("has connected to the database");

            state = conn.prepareStatement("SELECT * FROM items WHERE is_active = 1");
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

    public String addNewItem(Items item) throws SQLException{
        Connection conn = null;
        PreparedStatement state = null;
        String response;

        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:subscription.db");
            System.out.println("has connected to the database");
            state = conn.prepareStatement("INSERT INTO items VALUES (?, ?, ?, ?, ?);");
            System.out.println("Inserting data to table customers");

            state.setInt(1, item.getId());
            state.setString(2, item.getName());
            state.setInt(3, item.getPrice());
            state.setString(4, item.getType());
            state.setInt(5, item.getIs_active());
            
            int rowsAffected = state.executeUpdate();
            if(rowsAffected > 0) {
                response = rowsAffected + " row(s) has been affected";
                System.out.println(response);
            }else{
                response = "No rows have been added";
                System.out.println(response);
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            throw new RuntimeException(e);
        }finally{
            if(state != null) state.close();
            if(conn != null) conn.close();
        }

        return response;
    }
    
    public String deleteItem(int id) throws SQLException{
        Connection conn = null;
        PreparedStatement state = null;
        String response;

        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:subscription.db");
            System.out.println("has connected to the database");
            state = conn.prepareStatement("UPDATE items SET is_active = 0 WHERE id = " + id);
            int rowsAffected = state.executeUpdate();
            if(rowsAffected > 0){
                response = rowsAffected + " row(s) have been affected";
                System.out.println(response);
            }else{
                response = "No rows have been affected";
                System.out.println(response);
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            throw new RuntimeException(e);
        } finally {
            if (state != null) state.close();
            if (conn != null) conn.close();
        }
        return response;
    }
    
    // Spesifikasi API PUT item
    public String updateItem(int id, Items item) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        String response;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:subscription.db");
            System.out.println("Connected to database");

            String updateSQL = "UPDATE items SET name = ?, price = ?, is_active = ?, type = ? WHERE id = ?";
            statement = connection.prepareStatement(updateSQL);
            statement.setString(1, item.getName());
            statement.setInt(2, item.getPrice());
            statement.setInt(3, item.getIs_active());
            statement.setString(4, item.getType());
            statement.setInt(5, id);
            int rowsAffected = statement.executeUpdate();
            if(rowsAffected > 0) {
                response = rowsAffected + " row(s) has been affected";
                System.out.println(response);
            }else{
                response = "No rows have been added";
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
}