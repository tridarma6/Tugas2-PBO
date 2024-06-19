package com.subscription.httpserver;

import java.sql.SQLException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.subscription.model.Customers;
import com.subscription.model.Subscriptions;
import com.subscription.persistence.SubscriptionsAccess;

public class SubscriptionsReqHandler{
    private static final String SUBSCRIPTIONS_RECORD = "Subscriptions Record";
    private static final String SUBSCRIPTIONS = "subscriptions";
    private static final String SUBSCRIPTIONS_SORTBY = "subscriptions?sort_by=current_term_end&sort_type=desc";
    
    SubscriptionsAccess subscriptionsAccess = new SubscriptionsAccess();


    public JSONObject getSubscriptions(String path[]) throws SQLException{
        int subsId;
        JSONObject jsonSubs = new JSONObject();

        switch (path.length) {
            case 2:
                JSONArray jsonSubsArray = new JSONArray();
                ArrayList<Subscriptions> listSubs = subscriptionsAccess.getAllSubscriptions();
                for (Subscriptions subs : listSubs) {
                    jsonSubsArray.put(toJSONObject(subs));
                }
                jsonSubs.put(SUBSCRIPTIONS_RECORD, jsonSubsArray);
                break;
            case 3:
                subsId = Integer.parseInt(path[2]);
                ArrayList<Subscriptions> subscriptions = subscriptionsAccess.getSubscriptionById(subsId);
                if(subsId != 0){
                    jsonSubs.put(SUBSCRIPTIONS, subscriptions);
                }
                break;
        
            case 4:
                
                break;
        
            default:
                break;
        }
        return jsonSubs;
    }

    private JSONObject toJSONObject(Subscriptions subscriptions) {
        JSONObject jsonSubsResult = new JSONObject();
        jsonSubsResult.put("id", subscriptions.getId());
        jsonSubsResult.put("customer", subscriptions.getCustomerId());
        jsonSubsResult.put("billing_period", subscriptions.getBilling_period());
        jsonSubsResult.put("billing_period_unit", subscriptions.getBilling_period_unit());
        jsonSubsResult.put("total_due", subscriptions.getTotal_due());
        jsonSubsResult.put("actived_at", subscriptions.getActived_at());
        jsonSubsResult.put("current_term_start", subscriptions.getCurrent_term_start());
        jsonSubsResult.put("current_term_end", subscriptions.getCurrent_term_end());
        jsonSubsResult.put("status", subscriptions.getStatus());
        return jsonSubsResult;
    }

}