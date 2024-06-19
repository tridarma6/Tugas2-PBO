package com.subscription.httpserver;

import java.sql.SQLException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.subscription.model.Customers;
import com.subscription.model.ShippingAddress;
import com.subscription.persistence.CardsAccess;
import com.subscription.persistence.CustomersAccess;
import com.subscription.persistence.SubscriptionsAccess;
import com.subscription.persistence.ShippingAddressAccess;

public class CustomersReqHandler {
    private static final String CUSTOMER_RECORD = "Customer Record";
    private static final String CARDS = "cards";
    private static final String SUBSCRIPTIONS = "subscriptions";
    private static final String SUBSCRIPTIONS_STATUS_ACTIVE = "subscriptions?subscriptions_status=active";
    private static final String SUBSCRIPTIONS_STATUS_CANCELLED = "subscriptions?subscriptions_status=cancelled";
    private static final String SUBSCRIPTIONS_STATUS_NONRENEWING = "subscriptions?subscriptions_status=nonrenewing";
    private static final String SHIPPING_ADDRESS = "shipping_address";

    CardsAccess cardsAccess = new CardsAccess();
    CustomersAccess customersAccess = new CustomersAccess();
    SubscriptionsAccess subscriptionsAccess = new SubscriptionsAccess();
    ShippingAddressAccess shippingAddressAccess = new ShippingAddressAccess();

    private Customers customersParseJSONData(JSONObject jsonReqbody) throws SQLException {
        Customers customers = new Customers();
        customers.setId(jsonReqbody.optInt("id"));
        customers.setEmail(jsonReqbody.optString("email"));
        customers.setFirst_name(jsonReqbody.optString("first_name"));
        customers.setLast_name(jsonReqbody.optString("last_name"));
        customers.setPhone_number(jsonReqbody.optString("phone_number"));
        return customers;
    }

    public String postCustomer(JSONObject jsonObject) throws SQLException {
        Customers customers = customersParseJSONData(jsonObject);
        return customersAccess.addNewCustomer(customers);
    }

    public String putCustomer(JSONObject jsonObject, String[] path) throws SQLException {
        Customers customers = customersParseJSONData(jsonObject);
        int idCustomer = Integer.parseInt(path[2]);
        return customersAccess.updateCustomer(idCustomer, customers);
    }

    public JSONObject getCustomers(String[] path) throws SQLException {
        int idCustomer;
        JSONObject jsonCustomer = new JSONObject();

        switch (path.length) {
            case 2:
                JSONArray jsonCustomerArray = new JSONArray();
                ArrayList<Customers> listCustomers = customersAccess.getAllCustomers();
                for (Customers customers : listCustomers) {
                    jsonCustomerArray.put(toJSONObject(customers));
                }
                jsonCustomer.put(CUSTOMER_RECORD, jsonCustomerArray);
                break;
            case 3:
                idCustomer = Integer.parseInt(path[2]);
                Customers customer = customersAccess.getCustomerById(idCustomer);
                if (customer.getId() != 0) {
                    jsonCustomer.put(CUSTOMER_RECORD, toJSONObject(customer));
                }
                break;
            case 4:
                idCustomer = Integer.parseInt(path[2]);
                String recordType = path[3];
                switch (recordType) {
                    case CARDS:
                        jsonCustomer.put("Customer's Card Record", getFormattedCustomerCardRecord(idCustomer));
                        break;
                    case SUBSCRIPTIONS:
                        jsonCustomer.put("Customer's Subscription Record", getFormattedCustomerSubscriptionRecord(idCustomer));
                        break;
                    case SUBSCRIPTIONS_STATUS_ACTIVE:
                        jsonCustomer.put("Customer's Subscription Record", getFormattedCustomerSubscriptionStatusRecord(idCustomer, "active"));
                        break;
                    case SUBSCRIPTIONS_STATUS_CANCELLED:
                        jsonCustomer.put("Customer's Subscription Record", getFormattedCustomerSubscriptionStatusRecord(idCustomer, "cancelled"));
                        break;
                    case SUBSCRIPTIONS_STATUS_NONRENEWING:
                        jsonCustomer.put("Customer's Subscription Record", getFormattedCustomerSubscriptionStatusRecord(idCustomer, "nonrenewing"));
                        break;
                    case SHIPPING_ADDRESS:
                        jsonCustomer.put("Customer's Shipping Address Record", getShippingAddressRecord(idCustomer));
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }

        return jsonCustomer;
    }

    public JSONObject getFormattedCustomerCardRecord(int customerId) throws SQLException {
        JSONObject customerAndCards = cardsAccess.getCustomerAndCardsByCustomerId(customerId);
        JSONObject formattedJson = new JSONObject();
        
        JSONArray customerCardArray = new JSONArray();
        customerCardArray.put(customerAndCards);
        
        formattedJson.put("Customer's Card Record", customerCardArray);
        return formattedJson;
    }
    
    public JSONObject getFormattedCustomerSubscriptionRecord(int customerId) throws SQLException{
        JSONObject customerAndSubscriptions = subscriptionsAccess.getCustomerAndSubscriptionsByCustomerId(customerId);
        JSONObject formattedJson = new JSONObject();

        JSONArray customerSubsArray = new JSONArray();
        customerSubsArray.put(customerAndSubscriptions);

        formattedJson.put(SUBSCRIPTIONS, customerSubsArray);
        return formattedJson;
    }

    public JSONObject getFormattedCustomerSubscriptionStatusRecord(int customerId, String status) throws SQLException{
        JSONObject customerAndSubscriptions = subscriptionsAccess.getCustomerAndSubscriptionsStatusByCustomerId(customerId, status);
        JSONObject formattedJson = new JSONObject();

        JSONArray customerSubsArray = new JSONArray();
        customerSubsArray.put(customerAndSubscriptions);

        formattedJson.put(SUBSCRIPTIONS, customerSubsArray);
        return formattedJson;
    }

    
    private JSONObject toJSONObject(Customers customers) {
        JSONObject jsonCusResult = new JSONObject();
        jsonCusResult.put("id", customers.getId());
        jsonCusResult.put("email", customers.getEmail());
        jsonCusResult.put("first_name", customers.getFirst_name());
        jsonCusResult.put("last_name", customers.getLast_name());
        jsonCusResult.put("phone_number", customers.getPhone_number());
        return jsonCusResult;
    }

    private JSONObject getShippingAddressRecord(int idCustomer) throws SQLException {
        JSONObject jsonShipping = new JSONObject();
        JSONArray jsonShippingArray = new JSONArray();
        ArrayList<ShippingAddress> listShippingAddresses = shippingAddressAccess.getShippingAddressesByCustomerId(idCustomer);

        for (ShippingAddress shippingAddress : listShippingAddresses) {
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
        return jsonShipping;
    }
}
