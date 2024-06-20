package com.subscription.httpserver;

import java.sql.SQLException;
import java.util.ArrayList;
import java.time.LocalDateTime;
import org.json.JSONArray;
import org.json.JSONObject;

import com.subscription.model.Items;
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
                if ("subscriptions".equals(path[1])) {
                    
                    JSONArray jsonSubsArray = new JSONArray();
                    ArrayList<Subscriptions> listSubs = subscriptionsAccess.getAllSubscriptions();
                    for (Subscriptions subs : listSubs) {
                        jsonSubsArray.put(toJSONObject(subs));
                    }
                    jsonSubs.put(SUBSCRIPTIONS_RECORD, jsonSubsArray);
                    break;
                }else if(SUBSCRIPTIONS_SORTBY.equals(path[1])){
                    JSONArray jsonSubsArray = new JSONArray();
                    ArrayList<Subscriptions> listSubs = subscriptionsAccess.getAllSubscriptionsSortedByTermEnd();
                    for (Subscriptions subs : listSubs) {
                        jsonSubsArray.put(toJSONObject(subs));
                    }
                    jsonSubs.put(SUBSCRIPTIONS_RECORD, jsonSubsArray);
                    break;
                }
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

    public String postSubscriptions(JSONObject jsonObject) throws SQLException{
        Subscriptions subscriptions = parseSubs(jsonObject);
        return subscriptionsAccess.addNewSubscription(subscriptions);
    }

    private Subscriptions parseSubs(JSONObject jsonObject){
        Subscriptions subs = new Subscriptions();
        subs.setId(jsonObject.getInt("id"));
        subs.setCustomer(jsonObject.getInt("customer"));
        subs.setBilling_period(jsonObject.getInt("billing_period"));
        subs.setBilling_period_unit(jsonObject.getString("billing_period_unit"));
        subs.setTotal_due(jsonObject.getInt("total_due"));
        subs.setActived_at(jsonObject.getInt("actived_at"));
        subs.setCurrent_term_start(jsonObject.getString("current_term_start"));
        subs.setCurrent_term_end(jsonObject.getString("current_term_end"));
        subs.setStatus(jsonObject.getString("status"));
        return subs;
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