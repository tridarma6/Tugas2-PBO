package com.subscription.httpserver;

import java.sql.SQLException;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

import com.subscription.model.Items;
import com.subscription.model.Subscriptions;
import com.subscription.model.Customers;
import com.subscription.model.SubscriptionItems;
import com.subscription.persistence.SubscriptionsAccess;

public class SubscriptionsReqHandler {
    private static final String SUBSCRIPTIONS_RECORD = "Subscriptions Record";
    private static final String SUBSCRIPTIONS = "subscriptions";
    private static final String SUBSCRIPTIONS_SORTBY = "subscriptions?sort_by=current_term_end&sort_type=desc";
    
    SubscriptionsAccess subscriptionsAccess = new SubscriptionsAccess();

    public JSONObject getSubscriptions(String[] path) throws SQLException {
        int subsId;
        JSONObject jsonSubs = new JSONObject();

        switch (path.length) {
            case 2:
                if (SUBSCRIPTIONS.equals(path[1])) {
                    JSONArray jsonSubsArray = new JSONArray();
                    ArrayList<Subscriptions> listSubs = subscriptionsAccess.getAllSubscriptions();
                    for (Subscriptions subs : listSubs) {
                        jsonSubsArray.put(toJSONObject(subs));
                    }
                    jsonSubs.put(SUBSCRIPTIONS_RECORD, jsonSubsArray);
                } else if (SUBSCRIPTIONS_SORTBY.equals(path[1])) {
                    JSONArray jsonSubsArray = new JSONArray();
                    ArrayList<Subscriptions> listSubs = subscriptionsAccess.getAllSubscriptionsSortedByTermEnd();
                    for (Subscriptions subs : listSubs) {
                        jsonSubsArray.put(toJSONObject(subs));
                    }
                    jsonSubs.put(SUBSCRIPTIONS_RECORD, jsonSubsArray);
                }
                break;
            case 3:
                if (SUBSCRIPTIONS.equals(path[1])) {
                    subsId = Integer.parseInt(path[2]);
                    JSONArray jsonArray = new JSONArray();
                    ArrayList<Subscriptions> subscriptions = subscriptionsAccess.getSubscriptionById(subsId);
                    for (Subscriptions subscriptionDetail : subscriptions) {
                        jsonArray.put(toJSONObject(subscriptionDetail));
                    }
                    jsonSubs.put("Subscriptions Detail", jsonArray);
                }
                break;
            default:
                // Handle invalid path case if needed
                break;
        }
        return jsonSubs;
    }

    public String postSubscriptions(JSONObject jsonObject) throws SQLException {
        Subscriptions subscriptions = parseSubs(jsonObject);
        return subscriptionsAccess.addNewSubscription(subscriptions);
    }

    private Subscriptions parseSubs(JSONObject jsonObject) {
        Subscriptions subs = new Subscriptions();
        Customers customer = new Customers();
        subs.setId(jsonObject.getInt("id"));
        subs.setCustomer(jsonObject.getInt("customer"));
        subs.setBilling_period(jsonObject.getInt("billing_period"));
        subs.setBilling_period_unit(jsonObject.getString("billing_period_unit"));
        subs.setTotal_due(jsonObject.getInt("total_due"));
        subs.setActived_at(jsonObject.getInt("actived_at"));
        subs.setCurrent_term_start(jsonObject.getString("current_term_start"));
        subs.setCurrent_term_end(jsonObject.getString("current_term_end"));
        subs.setStatus(jsonObject.getString("status"));

        // Parse customer details if available
        if (jsonObject.has("customer_details")) {
            ArrayList<Customers> customers= new ArrayList<>();

            JSONObject customerJson = jsonObject.getJSONObject("customer_details");
            customer.setId(customerJson.getInt("customer"));
            customer.setFirst_name(customerJson.getString("first_name"));
            customer.setLast_name(customerJson.getString("last_name"));
            subs.setCustomerDetails(customer);
        }

        // Parse subscription items if available
        if (jsonObject.has("subscription_items")) {
            JSONArray itemsArray = jsonObject.getJSONArray("subscription_items");
            ArrayList<SubscriptionItems> subscriptionItemsList = new ArrayList<>();
            for (int i = 0; i < itemsArray.length(); i++) {
                JSONObject itemJson = itemsArray.getJSONObject(i);
                SubscriptionItems subscriptionItem = new SubscriptionItems();
                subscriptionItem.setQuantity(itemJson.getInt("quantity"));
                subscriptionItem.setAmount(itemJson.getInt("amount"));
                
                if (itemJson.has("item_details")) {
                    JSONObject itemDetailsJson = itemJson.getJSONObject("item_details");
                    Items item = new Items();
                    item.setId(itemDetailsJson.getInt("id"));
                    item.setName(itemDetailsJson.getString("name"));
                    item.setPrice(itemDetailsJson.getInt("price"));
                    item.setType(itemDetailsJson.getString("type"));
                    item.setIs_active(itemDetailsJson.getInt("is_active"));
                    subscriptionItem.setItem(item);
                }

                subscriptionItemsList.add(subscriptionItem);
            }
            subs.setSubscriptionItems(subscriptionItemsList);
        }

        return subs;
    }

    private JSONObject toJSONObject(Subscriptions subscriptions) {
        JSONObject jsonSubsResult = new JSONObject();
        jsonSubsResult.put("id", subscriptions.getId());
        jsonSubsResult.put("customer", subscriptions.getCustomer());
        jsonSubsResult.put("billing_period", subscriptions.getBilling_period());
        jsonSubsResult.put("billing_period_unit", subscriptions.getBilling_period_unit());
        jsonSubsResult.put("total_due", subscriptions.getTotal_due());
        jsonSubsResult.put("actived_at", subscriptions.getActived_at());
        jsonSubsResult.put("current_term_start", subscriptions.getCurrent_term_start());
        jsonSubsResult.put("current_term_end", subscriptions.getCurrent_term_end());
        jsonSubsResult.put("status", subscriptions.getStatus());

        // Adding detailed information about customer and subscription items
        
        Customers customer = subscriptions.getCustomerDetails();
        if (customer != null) {
            JSONObject jsonCustomer = new JSONObject();
            jsonCustomer.put("id", customer.getId());
            jsonCustomer.put("first_name", customer.getFirst_name());
            jsonCustomer.put("last_name", customer.getLast_name());
            jsonSubsResult.put("customer_details", jsonCustomer);
        } else {
            jsonSubsResult.put("customer_details", JSONObject.NULL);
        }

        JSONArray jsonItemsArray = new JSONArray();
        if (subscriptions.getSubscriptionItems() != null) {
            for (SubscriptionItems item : subscriptions.getSubscriptionItems()) {
                JSONObject jsonItem = new JSONObject();
                jsonItem.put("quantity", item.getQuantity());
                jsonItem.put("amount", item.getAmount());

                Items itemDetails = item.getItem();
                if (itemDetails != null) {
                    JSONObject jsonItemDetails = new JSONObject();
                    jsonItemDetails.put("id", itemDetails.getId());
                    jsonItemDetails.put("name", itemDetails.getName());
                    jsonItemDetails.put("price", itemDetails.getPrice());
                    jsonItemDetails.put("type", itemDetails.getType());
                    jsonItem.put("item_details", jsonItemDetails);
                } else {
                    jsonItem.put("item_details", JSONObject.NULL);
                }

                jsonItemsArray.put(jsonItem);
            }
        }
        jsonSubsResult.put("subscription_items", jsonItemsArray);

        return jsonSubsResult;
    }
}
