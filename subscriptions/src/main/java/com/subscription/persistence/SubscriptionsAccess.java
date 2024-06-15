package com.subscription.persistence;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.subscription.model.*;

public class SubscriptionsAccess {

    public Subscriptions getSubscriptionById(int id) throws SQLException {
        Subscriptions subscription = null;
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
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
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Customers customer = new Customers();
                customer.setId(rs.getInt("customer_id"));
                customer.setFirst_name(rs.getString("first_name"));
                customer.setLast_name(rs.getString("last_name"));

                subscription = new Subscriptions();
                subscription.setId(rs.getInt("subscription_id"));
                subscription.setCustomer(customer);

                List<SubscriptionItems> subscriptionItems = new ArrayList<>();
                do {
                    Items item = new Items();
                    item.setId(rs.getInt("item_id"));
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
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return subscription;
    }
}
