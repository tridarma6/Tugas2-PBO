package com.subscription.httpserver;
import java.sql.SQLException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.subscription.model.Cards;
import com.subscription.model.Customers;
import com.subscription.model.ShippingAddress;
import com.subscription.model.Subscriptions;
import com.subscription.persistence.CardsAccess;
import com.subscription.persistence.CustomersAccess;
import com.subscription.persistence.SubscriptionsAccess;
import com.subscription.persistence.ShippingAddressAccess;

public class CustomersReqHandler{
    CardsAccess cardsAccess = new CardsAccess();
    CustomersAccess customersAccess = new CustomersAccess();
    SubscriptionsAccess subscriptionsAccess = new SubscriptionsAccess();
    ShippingAddressAccess shippingAddressAccess = new ShippingAddressAccess();

    private Customers customersParseJSONData(JSONObject jsonReqbody) throws SQLException{
        Customers customers = new Customers();
        customers.setId(jsonReqbody.optInt("id"));
        customers.setEmail(jsonReqbody.optString("email"));
        customers.setFirst_name(jsonReqbody.optString("first_name"));
        customers.setLast_name(jsonReqbody.optString("last_name"));
        customers.setPhone_number(jsonReqbody.optString("phone_number"));

        return customers;
    }

    public String postCustomer(JSONObject jsonObject) throws SQLException{
        Customers customers = customersParseJSONData(jsonObject);
        return customersAccess.addNewCustomer(customers);
    }

    public String putCustomer(JSONObject jsonObject, String[] path) throws SQLException{
        Customers customers = customersParseJSONData(jsonObject);
        int idCustomer = Integer.valueOf(path[2]);
        return customersAccess.updateCustomer(idCustomer, customers);
    }

    public JSONObject getCustomers(String[] path) throws SQLException{
        int idCustomer = 0;
        JSONObject jsonCustomer = null;

        if(path.length == 2){
            jsonCustomer = new JSONObject();
            JSONArray jsonCustomerArray = new JSONArray();
            ArrayList<Customers> listCustomers = customersAccess.getAllCustomers();
            for(Customers customers : listCustomers){
                JSONObject jsonCusResult = new JSONObject();
                jsonCusResult.put("id", customers.getId());
                jsonCusResult.put("email", customers.getEmail());
                jsonCusResult.put("first_name", customers.getFirst_name());
                jsonCusResult.put("last_name", customers.getLast_name());
                jsonCusResult.put("phone_number", customers.getPhone_number());
                jsonCustomerArray.put(jsonCusResult);
            }
            jsonCustomer.put("Customer Record", jsonCustomerArray);

        }else if(path.length == 3){
            jsonCustomer = new JSONObject();
            idCustomer = Integer.valueOf(path[2]);
            Customers customers = customersAccess.getCustomerById(idCustomer);
            if(customers.getId() != 0){
                JSONObject jsonCusResult = new JSONObject();
                jsonCusResult.put("id", customers.getId());
                jsonCusResult.put("email", customers.getEmail());
                jsonCusResult.put("first_name", customers.getFirst_name());
                jsonCusResult.put("last_name", customers.getLast_name());
                jsonCusResult.put("phone_number", customers.getPhone_number());
                
                jsonCustomer.put("Customer Record", jsonCusResult);
            }
        }else if(path.length == 4){
            if ("cards".equals(path[3])) {
                jsonCustomer = new JSONObject();
                idCustomer = Integer.valueOf(path[2]);
                JSONObject jsonCards = new JSONObject();
                JSONArray jsonCardArray = new JSONArray();

                ArrayList<Cards> listCards = cardsAccess.getCardByCustomerId(idCustomer);

                for(Cards card : listCards){
                    JSONObject jsonCardObject = new JSONObject();
                    jsonCardObject.put("id", card.getId());
                    jsonCardObject.put("customer", card.getCustomer());
                    jsonCardObject.put("card_type", card.getCard_type());
                    jsonCardObject.put("masked_number", card.getMasked_number());
                    jsonCardObject.put("expiry_month", card.getExpiry_month());
                    jsonCardObject.put("expiry_year", card.getExpiry_year());
                    jsonCardObject.put("status", card.getStatus());
                    jsonCardObject.put("is_primary", card.getIs_primary());

                    jsonCardArray.put(jsonCardObject);
                }

                jsonCards.put("Cards Record", jsonCardArray);
                jsonCustomer.put("Customer's Card Record", jsonCards);

            } else if("subscriptions".equals(path[3])){
                jsonCustomer = new JSONObject();
                idCustomer = Integer.valueOf(path[3]);
                JSONObject jsonSubscription = new JSONObject();
                JSONArray jsonSubscriptionArray = new JSONArray();

                ArrayList<Subscriptions> listSubscriptions = subscriptionsAccess.getSubscriptionById(idCustomer);
                for (Subscriptions subscription : listSubscriptions) {
                    JSONObject jsonSubscriptionRecord = new JSONObject();
                    jsonSubscriptionRecord.put("id", subscription.getId());
                    jsonSubscriptionRecord.put("customer", subscription.getCustomer());
                    jsonSubscriptionRecord.put("billing_period", subscription.getBilling_period());
                    jsonSubscriptionRecord.put("billing_period_unit", subscription.getBilling_period_unit());
                    jsonSubscriptionRecord.put("total_due", subscription.getTotal_due());
                    jsonSubscriptionRecord.put("active_at", subscription.getActived_at());
                    jsonSubscriptionRecord.put("current_term_start", subscription.getCurrent_term_start());
                    jsonSubscriptionRecord.put("current_term_end", subscription.getCurrent_term_end());
                    jsonSubscriptionRecord.put("status", subscription.getStatus());

                    jsonSubscriptionArray.put(jsonSubscriptionRecord);
                }

                jsonSubscription.put("Subscription Record", jsonSubscriptionArray);
                jsonCustomer.put("Customer's Subscription Record", jsonSubscription);

                
            }else if("subscriptions?subscriptions_status=active".equals(path[3])){
                jsonCustomer = new JSONObject();
                idCustomer = Integer.valueOf(path[3]);
                JSONObject jsonSubscription = new JSONObject();
                JSONArray jsonSubscriptionArray = new JSONArray();

                ArrayList<Subscriptions> listSubscriptions = subscriptionsAccess.getSubscriptionsByCustomerIdAndStatus(idCustomer, "active");
                for (Subscriptions subscription : listSubscriptions) {
                    JSONObject jsonSubscriptionRecord = new JSONObject();
                    jsonSubscriptionRecord.put("id", subscription.getId());
                    jsonSubscriptionRecord.put("customer", subscription.getCustomer());
                    jsonSubscriptionRecord.put("billing_period", subscription.getBilling_period());
                    jsonSubscriptionRecord.put("billing_period_unit", subscription.getBilling_period_unit());
                    jsonSubscriptionRecord.put("total_due", subscription.getTotal_due());
                    jsonSubscriptionRecord.put("active_at", subscription.getActived_at());
                    jsonSubscriptionRecord.put("current_term_start", subscription.getCurrent_term_start());
                    jsonSubscriptionRecord.put("current_term_end", subscription.getCurrent_term_end());
                    jsonSubscriptionRecord.put("status", subscription.getStatus());

                    jsonSubscriptionArray.put(jsonSubscriptionRecord);
                }

                jsonSubscription.put("Subscription Record", jsonSubscriptionArray);
                jsonCustomer.put("Customer's Subscription Record", jsonSubscription);

            }else if("subscriptions?subscriptions_status=cancelled".equals(path[3])){
                jsonCustomer = new JSONObject();
                idCustomer = Integer.valueOf(path[3]);
                JSONObject jsonSubscription = new JSONObject();
                JSONArray jsonSubscriptionArray = new JSONArray();

                ArrayList<Subscriptions> listSubscriptions = subscriptionsAccess.getSubscriptionsByCustomerIdAndStatus(idCustomer, "cancelled");
                for (Subscriptions subscription : listSubscriptions) {
                    JSONObject jsonSubscriptionRecord = new JSONObject();
                    jsonSubscriptionRecord.put("id", subscription.getId());
                    jsonSubscriptionRecord.put("customer", subscription.getCustomer());
                    jsonSubscriptionRecord.put("billing_period", subscription.getBilling_period());
                    jsonSubscriptionRecord.put("billing_period_unit", subscription.getBilling_period_unit());
                    jsonSubscriptionRecord.put("total_due", subscription.getTotal_due());
                    jsonSubscriptionRecord.put("active_at", subscription.getActived_at());
                    jsonSubscriptionRecord.put("current_term_start", subscription.getCurrent_term_start());
                    jsonSubscriptionRecord.put("current_term_end", subscription.getCurrent_term_end());
                    jsonSubscriptionRecord.put("status", subscription.getStatus());

                    jsonSubscriptionArray.put(jsonSubscriptionRecord);
                }

                jsonSubscription.put("Subscription Record", jsonSubscriptionArray);
                jsonCustomer.put("Customer's Subscription Record", jsonSubscription);
            
            }else if("subscriptions?subscriptions_status=nonrenewing".equals(path[3])){
                jsonCustomer = new JSONObject();
                idCustomer = Integer.valueOf(path[3]);
                JSONObject jsonSubscription = new JSONObject();
                JSONArray jsonSubscriptionArray = new JSONArray();

                ArrayList<Subscriptions> listSubscriptions = subscriptionsAccess.getSubscriptionsByCustomerIdAndStatus(idCustomer, "nonrenewing");
                for (Subscriptions subscription : listSubscriptions) {
                    JSONObject jsonSubscriptionRecord = new JSONObject();
                    jsonSubscriptionRecord.put("id", subscription.getId());
                    jsonSubscriptionRecord.put("customer", subscription.getCustomer());
                    jsonSubscriptionRecord.put("billing_period", subscription.getBilling_period());
                    jsonSubscriptionRecord.put("billing_period_unit", subscription.getBilling_period_unit());
                    jsonSubscriptionRecord.put("total_due", subscription.getTotal_due());
                    jsonSubscriptionRecord.put("active_at", subscription.getActived_at());
                    jsonSubscriptionRecord.put("current_term_start", subscription.getCurrent_term_start());
                    jsonSubscriptionRecord.put("current_term_end", subscription.getCurrent_term_end());
                    jsonSubscriptionRecord.put("status", subscription.getStatus());

                    jsonSubscriptionArray.put(jsonSubscriptionRecord);
                }

                jsonSubscription.put("Subscription Record", jsonSubscriptionArray);
                jsonCustomer.put("Customer's Subscription Record", jsonSubscription);
            }
            else if("shipping_address".equals(path[3])){
                jsonCustomer = new JSONObject();
                idCustomer = Integer.valueOf(path[3]);
                JSONObject jsonShipping = new JSONObject();
                JSONArray jsonShippingArray = new JSONArray();

                ArrayList<ShippingAddress> listShippingAddresses = shippingAddressAccess.getShippingAddressesByCustomerId(idCustomer);
                for(ShippingAddress shippingAddress : listShippingAddresses){
                    JSONObject jsonShippingAddressRecord = new JSONObject();
                    jsonShippingAddressRecord.put("id", shippingAddress.getId());
                    jsonShippingAddressRecord.put("customer", shippingAddress.getCustomer());
                    jsonShippingAddressRecord.put("title", shippingAddress.getTitle());
                    jsonShippingAddressRecord.put("line1", shippingAddress.getLine1());
                    jsonShippingAddressRecord.put("line2", shippingAddress.getLine2());
                    jsonShippingAddressRecord.put("city", shippingAddress.getCity());
                    jsonShippingAddressRecord.put("province", shippingAddress.getProvince());
                    jsonShippingAddressRecord.put("postcode", shippingAddress.getPostcode());

                    jsonShippingArray.put(jsonShippingAddressRecord);
                }

                jsonShipping.put("Shipping Address Record", jsonShippingArray);
                jsonCustomer.put("Customer's Shipping Address Record", jsonShipping);
            }
        }

        return jsonCustomer;
    }
}