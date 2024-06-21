package com.subscription.model;

import org.json.JSONObject;

public class SubscriptionDetail {
    private Subscriptions subscription;

    public SubscriptionDetail() {
    }

    public Subscriptions getSubscription() {
        return subscription;
    }

    public void setSubscription(Subscriptions subscription) {
        this.subscription = subscription;
    }

    public JSONObject toJSONObject() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toJSONObject'");
    }
}
