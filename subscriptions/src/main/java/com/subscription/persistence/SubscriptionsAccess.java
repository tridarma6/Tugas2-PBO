package com.subscription.persistence;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.subscription.model.*;

public class SubscriptionsAccess {

    public ArrayList<Subscriptions> getSubscriptionById(int id) throws SQLException {
        Subscriptions subscription = null;
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet result = null;
        ArrayList<Subscriptions> listSubs = new ArrayList<>();
        try {
<<<<<<< HEAD
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
=======
            connection = DriverManager.getConnection("jbdc:sqlite:subscription.db");
            String query = "SELECT s.id as subscription_id, s.customer_id, " +
                           "si.quantity, si.amount, si.item_id, " +
                           "c.first_name, c.last_name, " +
                           "i.name, i.price, i.type " +
                           "FROM subscriptions s " +
                           "JOIN customers c ON s.customer_id = c.id " +
                           "JOIN subscription_items si ON si.subscription_id = s.id " +
                           "JOIN items i ON si.item_id = i.id " +
                           "WHERE s.id = ?";
            stmt = connection.prepareStatement(query);
            stmt.setInt(1, id);
            result = stmt.executeQuery();
>>>>>>> 3dae76aef2b49f8016e7cb531786ab1c090aa50c

            if (result.next()) {
                Customers customer = new Customers();
                customer.setId(result.getInt("customer_id"));
                customer.setFirst_name(result.getString("first_name"));
                customer.setLast_name(result.getString("last_name"));

                subscription = new Subscriptions();
                subscription.setId(result.getInt("subscription_id"));
                subscription.setCustomer(customer);

                List<SubscriptionItems> subscriptionItems = new ArrayList<>();
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
                listSubs.add(subscription);
            }
        }catch(SQLException e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            throw new RuntimeException(e);
        }finally{
            if(result != null) result.close();
            if(stmt != null) stmt.close();
            if(connection != null) connection.close();
        }
        return listSubs;
    }

    public ArrayList<Subscriptions> getSubscriptionsByCustomerIdAndStatus(int customerId, String status) throws SQLException {
        ArrayList<Subscriptions> listSubs = new ArrayList<>();
        Connection connection = null;
        PreparedStatement stmt = null;

        try {
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
                subscription.setCustomer(customer);

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
}
