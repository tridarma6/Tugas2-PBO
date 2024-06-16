package com.subscription.persistence;

import com.subscription.model.Subscriptions;
import java.sql.*;
import java.util.ArrayList;
import javax.management.RuntimeErrorException;

public class SubscriptionsAccess {
    
    // Fungsi untuk mendapatkan subscription berdasarkan id
    public Subscriptions getSubscriptionsById(int id) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;
        Subscriptions subscription = new Subscriptions();

        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:subscription.db");
            System.out.println("Connected to database");
            statement = connection.prepareStatement("SELECT * FROM subscriptions WHERE id = ?");
            statement.setInt(1, id);
            result = statement.executeQuery();
s
            while (result.next()) {
                subscription.setId(result.getInt("id"));
                subscription.setCustomer(result.getInt("customer"));
                subscription.setBillingPeriod(result.getInt("billing_period"));
                subscription.setPeriodUnit(result.getString("billing_period_unit"));
                subscription.setTotalDue(result.getInt("total_due"));
                subscription.setActiveAt(result.getTimestamp("active_at").toLocalDateTime());
                subscription.setCurrentTermStart(result.getTimestamp("current_term_start").toLocalDateTime());
                subscription.setCurrentTermEnd(result.getTimestamp("current_term_end").toLocalDateTime());
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            throw new RuntimeErrorException(new Error(e));
        } finally {
            if (result != null) result.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
        return subscription;
    }

    // Fungsi untuk mendapatkan semua subscriptions
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
                subscription.setCustomer(result.getInt("customer"));
                subscription.setBillingPeriod(result.getInt("billing_period"));
                subscription.setPeriodUnit(result.getString("billing_period_unit"));
                subscription.setTotalDue(result.getInt("total_due"));
                subscription.setActiveAt(result.getTimestamp("active_at").toLocalDateTime());
                subscription.setCurrentTermStart(result.getTimestamp("current_term_start").toLocalDateTime());
                subscription.setCurrentTermEnd(result.getTimestamp("current_term_end").toLocalDateTime());
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

    // Fungsi untuk mendapatkan semua subscriptions yang diurutkan berdasarkan current_term_end secara descending
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
                subscription.setCustomer(result.getInt("customer"));
                subscription.setBillingPeriod(result.getInt("billing_period"));
                subscription.setPeriodUnit(result.getString("billing_period_unit"));
                subscription.setTotalDue(result.getInt("total_due"));
                subscription.setActiveAt(result.getTimestamp("active_at").toLocalDateTime());
                subscription.setCurrentTermStart(result.getTimestamp("current_term_start").toLocalDateTime());
                subscription.setCurrentTermEnd(result.getTimestamp("current_term_end").toLocalDateTime());
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

    public SubscriptionDetail getSubscriptionDetailById(int id) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;
        SubscriptionDetail subscriptionDetail = new SubscriptionDetail();
        Subscriptions subscription = new Subscriptions();
        Customer customer = new Customer();
        List<SubscriptionItem> subscriptionItems = new ArrayList<>();

        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:subscription.db");
            System.out.println("Connected to database");

            // Get subscription info
            statement = connection.prepareStatement("SELECT * FROM subscriptions WHERE id = ?");
            statement.setInt(1, id);
            result = statement.executeQuery();

            if (result.next()) {
                subscription.setId(result.getInt("id"));
                subscription.setCustomer(result.getInt("customer"));
                subscription.setBillingPeriod(result.getInt("billing_period"));
                subscription.setPeriodUnit(result.getString("billing_period_unit"));
                subscription.setTotalDue(result.getInt("total_due"));
                subscription.setActiveAt(result.getTimestamp("active_at").toLocalDateTime());
                subscription.setCurrentTermStart(result.getTimestamp("current_term_start").toLocalDateTime());
                subscription.setCurrentTermEnd(result.getTimestamp("current_term_end").toLocalDateTime());
            }

            // Get customer info
            statement = connection.prepareStatement("SELECT * FROM customers WHERE id = ?");
            statement.setInt(1, subscription.getCustomer());
            result = statement.executeQuery();

            if (result.next()) {
                customer.setId(result.getInt("id"));
                customer.setFirstName(result.getString("first_name"));
                customer.setLastName(result.getString("last_name"));
            }

            // Get subscription items info
            statement = connection.prepareStatement("SELECT si.quantity, si.amount, i.id as item_id, i.name, i.price, i.type " +
                    "FROM subscription_items si " +
                    "JOIN items i ON si.item_id = i.id " +
                    "WHERE si.subscription_id = ?");
            statement.setInt(1, id);
            result = statement.executeQuery();

            while (result.next()) {
                SubscriptionItem subscriptionItem = new SubscriptionItem();
                subscriptionItem.setQuantity(result.getInt("quantity"));
                subscriptionItem.setAmount(result.getDouble("amount"));

                Item item = new Item();
                item.setId(result.getInt("item_id"));
                item.setName(result.getString("name"));
                item.setPrice(result.getDouble("price"));
                item.setType(result.getString("type"));

                subscriptionItem.setItem(item);
                subscriptionItems.add(subscriptionItem);
            }

            // Set all data to subscriptionDetail
            subscriptionDetail.setSubscription(subscription);
            subscriptionDetail.setCustomer(customer);
            subscriptionDetail.setSubscriptionItems(subscriptionItems);

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
}
