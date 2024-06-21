package com.subscription.persistence;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.management.RuntimeErrorException;

import org.json.JSONArray;
import org.json.JSONObject;

import com.subscription.model.*;

public class SubscriptionsAccess {

    public ArrayList<Subscriptions> getSubscriptionById(int id) throws SQLException {
        Subscriptions subscription = null;
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet result = null;
        ArrayList<Subscriptions> listSubs = new ArrayList<>();
        try {
            Class.forName("org.sqlite.JDBC");
            System.out.println("has connected to the database");
            connection = DriverManager.getConnection("jdbc:sqlite:subscription.db");
            String query = "SELECT s.id as subscription_id, s.customer, s.billing_period, s.billing_period_unit, s.total_due, " +
                    "s.actived_at, s.current_term_start, s.current_term_end, s.status, " +
                    "c.first_name, c.last_name, " +
                    "si.quantity, si.amount, si.item, " +
                    "i.id as item_id, i.name as item_name, i.price as item_price, i.type as item_type, i.is_active " +
                    "FROM subscriptions s " +
                    "JOIN customers c ON s.customer = c.id " +
                    "JOIN subscription_items si ON si.subscription = s.id " +
                    "JOIN items i ON si.item = i.id " +
                    "WHERE s.id = ?";
            stmt = connection.prepareStatement(query);
            stmt.setInt(1, id);
            result = stmt.executeQuery();
    
            if (result.next()) {
                Customers customer = new Customers();
                customer.setId(result.getInt("customer"));
                customer.setFirst_name(result.getString("first_name"));
                customer.setLast_name(result.getString("last_name"));
    
                subscription = new Subscriptions();
                subscription.setId(result.getInt("subscription_id"));
                subscription.setCustomer(customer.getId());
                subscription.setBilling_period(result.getInt("billing_period"));
                subscription.setBilling_period_unit(result.getString("billing_period_unit"));
                subscription.setTotal_due(result.getInt("total_due"));
                subscription.setActived_at(result.getInt("actived_at"));
                subscription.setCurrent_term_start(result.getString("current_term_start"));
                subscription.setCurrent_term_end(result.getString("current_term_end"));
                subscription.setStatus(result.getString("status"));
    
                List<SubscriptionItems> subscriptionItems = new ArrayList<>();
                do {
                    Items item = new Items();
                    item.setId(result.getInt("item_id"));
                    item.setName(result.getString("item_name"));
                    item.setPrice(result.getInt("item_price"));
                    item.setType(result.getString("item_type"));
                    item.setIs_active(result.getInt("is_active"));
    
                    SubscriptionItems subscriptionItem = new SubscriptionItems();
                    subscriptionItem.setQuantity(result.getInt("quantity"));
                    subscriptionItem.setAmount(result.getInt("amount"));
                    subscriptionItem.setItem(item);
    
                    subscriptionItems.add(subscriptionItem);
                } while (result.next());
    
                subscription.setSubscriptionItems(subscriptionItems);
                listSubs.add(subscription);
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            throw new RuntimeException(e);
        } finally {
            if (result != null) result.close();
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
        return listSubs;
    }

    public ArrayList<Subscriptions> getSubscriptionsByCustomerIdAndStatus(int customerId, String status) throws SQLException {
        ArrayList<Subscriptions> listSubs = new ArrayList<>();
        Connection connection = null;
        PreparedStatement stmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            System.out.println("has connected to the database");
            connection = DriverManager.getConnection("jdbc:sqlite:subscription.db");
            String query = "SELECT s.id as subscription_id, s.customer, " +
                           "si.quantity, si.amount, si.item, " +
                           "c.first_name, c.last_name, " +
                           "i.name, i.price, i.type " +
                           "FROM subscriptions s " +
                           "JOIN customers c ON s.customer = c.id " +
                           "JOIN subscription_items si ON si.subscription = s.id " +
                           "JOIN items i ON si.item = i.id " +
                           "WHERE s.customer = ? AND s.status = ?";
            stmt = connection.prepareStatement(query);
            stmt.setInt(1, customerId);
            stmt.setString(2, status);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Customers customer = new Customers();
                customer.setId(rs.getInt("id"));
                customer.setFirst_name(rs.getString("first_name"));
                customer.setLast_name(rs.getString("last_name"));

                Subscriptions subscription = new Subscriptions();
                subscription.setId(rs.getInt("id"));
                subscription.setCustomer(customer.getId());

                List<SubscriptionItems> subscriptionItems = new ArrayList<>();
                do {
                    Items item = new Items();
                    item.setId(rs.getInt("id"));
                    item.setName(rs.getString("name"));
                    item.setPrice(rs.getInt("price"));
                    item.setType(rs.getString("type"));

                    SubscriptionItems subscriptionItem = new SubscriptionItems();
                    subscriptionItem.setQuantity(rs.getInt("quantity"));
                    subscriptionItem.setAmount(rs.getInt("amount"));
                    subscriptionItem.setItem(item);

                    subscriptionItems.add(subscriptionItem);
                } while (rs.next());

                subscription.setSubscriptionItems(subscriptionItems);
                listSubs.add(subscription);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }

        return listSubs;
    }

    public String addNewSubscription(Subscriptions subscriptions) throws SQLException{
        Connection conn = null;
        PreparedStatement state = null;
        String response;

        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:subscription.db");
            System.out.println("has connected to the database");

            state = conn.prepareStatement("INSERT INTO subscriptions VALUES(?,?,?,?,?,?,?,?,?);");
            System.out.println("Inserting data to table subscriptions");
            state.setInt(1, subscriptions.getId());
            state.setInt(2, subscriptions.getCustomer());
            state.setInt(3, subscriptions.getBilling_period());
            state.setString(4, subscriptions.getBilling_period_unit().toString());
            state.setInt(5, subscriptions.getTotal_due());
            state.setInt(6, subscriptions.getActived_at());
            state.setString(7, subscriptions.getCurrent_term_start().toString());
            state.setString(8, subscriptions.getCurrent_term_start().toString());
            state.setString(9, subscriptions.getStatus().toString());

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


    // Spesifikasi untuk mendapatkan semua subscription
    public ArrayList<Subscriptions> getAllSubscriptions() throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;
        ArrayList<Subscriptions> listSubscriptions = new ArrayList<>();

        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:subscription.db");
            System.out.println("Connected to database");
            statement = connection.prepareStatement("SELECT * FROM subscriptions");
            result = statement.executeQuery();

            while (result.next()) {
                Subscriptions subscription = new Subscriptions();
                subscription.setId(result.getInt("id"));
                // Assuming customer ID is being set directly. Modify as needed.
                Customers customer = new Customers();
                customer.setId(result.getInt("customer"));
                subscription.setCustomer(customer.getId());
                subscription.setBilling_period(result.getInt("billing_period"));
                subscription.setBilling_period_unit(result.getString("billing_period_unit"));
                subscription.setTotal_due(result.getInt("total_due"));
                subscription.setActived_at(result.getInt("actived_at"));
                subscription.setCurrent_term_start(result.getString("current_term_start"));
                subscription.setCurrent_term_end(result.getString("current_term_end"));
                listSubscriptions.add(subscription);
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            throw new RuntimeErrorException(new Error(e));
        } finally {
            if (result != null) result.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
        return listSubscriptions;
    }

    // Spesifikasi untuk mendapatkan current term end dan sort type diurut desc
    public ArrayList<Subscriptions> getAllSubscriptionsSortedByTermEnd() throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;
        ArrayList<Subscriptions> listSubscriptions = new ArrayList<>();

        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:subscription.db");
            System.out.println("Connected to database");
            statement = connection.prepareStatement("SELECT * FROM subscriptions ORDER BY current_term_end DESC");
            result = statement.executeQuery();

            while (result.next()) {
                Subscriptions subscription = new Subscriptions();
                subscription.setId(result.getInt("id"));
                
                Customers customer = new Customers();
                customer.setId(result.getInt("customer"));
                subscription.setCustomer(customer.getId());

                subscription.setBilling_period(result.getInt("billing_period"));
                subscription.setBilling_period_unit(result.getString("billing_period_unit"));
                subscription.setTotal_due(result.getInt("total_due"));
                subscription.setActived_at(result.getInt("actived_at"));
                subscription.setCurrent_term_start(result.getString("current_term_start"));
                subscription.setCurrent_term_end(result.getString("current_term_end"));
                listSubscriptions.add(subscription);
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            throw new RuntimeErrorException(new Error(e));
        } finally {
            if (result != null) result.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
        return listSubscriptions;
    }
    // Spesifikasi untuk  mendapatkan detail informasi dengan id
    public SubscriptionDetail getSubscriptionDetailById(int id) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;
        SubscriptionDetail subscriptionDetail = new SubscriptionDetail();
        Subscriptions subscription = new Subscriptions();
        Customers customer = new Customers();
        List<SubscriptionItems> subscriptionItems = new ArrayList<>();

        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:subscription.db");
            System.out.println("Connected to database");

            // Get subscription info
            statement = connection.prepareStatement(
                "SELECT s.id AS subscription_id, s.customer AS customer_id, s.billing_period, " +
                "s.billing_period_unit, s.total_due, s.active_at, s.current_term_start, " +
                "s.current_term_end, c.first_name, c.last_name, c.email, c.phone_number, " +
                "si.item_id, i.name, i.price, i.type, si.quantity, si.amount " +
                "FROM subscriptions s " +
                "JOIN customers c ON s.customer = c.id " +
                "JOIN subscription_items si ON s.id = si.subscription_id " +
                "JOIN items i ON si.item_id = i.id " +
                "WHERE s.id = ?"
            );
            statement.setInt(1, id);
            result = statement.executeQuery();

            if (result.next()) {
                // Mengisi data pelanggan
                customer.setId(result.getInt("customer"));
                customer.setFirst_name(result.getString("first_name"));
                customer.setLast_name(result.getString("last_name"));
                customer.setEmail(result.getString("email"));
                customer.setPhone_number(result.getString("phone_number"));

                // Mengisi data langganan
                subscription.setId(result.getInt("id"));
                subscription.setCustomer(customer.getId());
                subscription.setBilling_period(result.getInt("billing_period"));
                subscription.setBilling_period_unit(result.getString("billing_period_unit"));
                subscription.setTotal_due(result.getInt("total_due"));
                subscription.setActived_at(result.getInt("actived_at"));
                subscription.setCurrent_term_start(result.getString("current_term_start"));
                subscription.setCurrent_term_end(result.getString("current_term_end"));

                // Mengisi data item langganan
                do {
                    Items item = new Items();
                    item.setId(result.getInt("item_id"));
                    item.setName(result.getString("name"));
                    item.setPrice(result.getInt("price"));
                    item.setType(result.getString("type"));

                    SubscriptionItems subscriptionItem = new SubscriptionItems();
                    subscriptionItem.setQuantity(result.getInt("quantity"));
                    subscriptionItem.setAmount(result.getInt("amount"));
                    subscriptionItem.setItem(item);

                    subscriptionItems.add(subscriptionItem);
                } while (result.next());

                subscription.setSubscriptionItems(subscriptionItems);
                subscriptionDetail.setSubscription(subscription);
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            throw new RuntimeErrorException(new Error(e));
        } finally {
            if (result != null) result.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
        return subscriptionDetail;
    }

    public JSONObject getCustomerAndSubscriptionsByCustomerId(int id) throws SQLException {
        Connection conn = null;
        PreparedStatement state = null;
        ResultSet result = null;
        JSONObject customerRecord = new JSONObject();
        JSONArray listSubs = new JSONArray();
        
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:subscription.db");
            System.out.println("has connected to the database");
            
            String query = "SELECT s.*, cu.first_name, cu.last_name, cu.email, cu.phone_number " +
                        "FROM subscriptions s " +
                        "JOIN customers cu ON cu.id = s.customer " +
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
                
                JSONObject subsJson = new JSONObject();
                subsJson.put("id", result.getInt("id"));
                subsJson.put("customer", result.getInt("customer"));
                subsJson.put("billing_period", result.getInt("billing_period"));
                subsJson.put("billing_period_unit", result.getString("billing_period_unit"));
                subsJson.put("total_due", result.getInt("total_due"));
                subsJson.put("actived_at", result.getInt("actived_at"));
                subsJson.put("current_term_start", result.getString("current_term_start"));
                subsJson.put("current_term_end", result.getString("current_term_end"));
                subsJson.put("status", result.getString("status"));
                
                listSubs.put(subsJson);
            }
            
            customerRecord.put("subscriptions", listSubs);
            
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
    public JSONObject getCustomerAndSubscriptionsStatusByCustomerId(int id, String status) throws SQLException {
        Connection conn = null;
        PreparedStatement state = null;
        ResultSet result = null;
        JSONObject customerRecord = new JSONObject();
        JSONArray listSubs = new JSONArray();
        
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:subscription.db");
            System.out.println("has connected to the database");
            
            String query = "SELECT s.*, cu.first_name, cu.last_name, cu.email, cu.phone_number " +
                        "FROM subscriptions s " +
                        "JOIN customers cu ON cu.id = s.customer " +
                        "WHERE cu.id = ? AND s.status = ?";
            state = conn.prepareStatement(query);
            state.setInt(1, id);
            state.setString(2, status);
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
                
                JSONObject subsJson = new JSONObject();
                subsJson.put("id", result.getInt("id"));
                subsJson.put("customer", result.getInt("customer"));
                subsJson.put("billing_period", result.getInt("billing_period"));
                subsJson.put("billing_period_unit", result.getString("billing_period_unit"));
                subsJson.put("total_due", result.getInt("total_due"));
                subsJson.put("actived_at", result.getInt("actived_at"));
                subsJson.put("current_term_start", result.getString("current_term_start"));
                subsJson.put("current_term_end", result.getString("current_term_end"));
                subsJson.put("status", result.getString("status"));
                
                listSubs.put(subsJson);
            }
            
            customerRecord.put("subscriptions", listSubs);
            
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
}


