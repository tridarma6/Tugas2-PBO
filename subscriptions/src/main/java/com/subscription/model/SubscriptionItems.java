package com.subscription.model;
public class SubscriptionItems{
    
    private Subscriptions subscription;
    private Items item;
    private int quantity;
    private int price;
    private int amount;
    
    public SubscriptionItems(){
        
    }
    
    public SubscriptionItems(Subscriptions subscription, Items item, int quantity, int price, int amount) {
        this.subscription = subscription;
        this.item = item;
        this.quantity = quantity;
        this.price = price;
        this.amount = amount;
    }
    public void setSubscription(Subscriptions subscription) {
        this.subscription = subscription;
    }
    public Items getItem() {
        return item;
    }
    public int getItemId() {
        return item.getId();
    }
    public int getSubscriptionId() {
        return subscription.getId();
    }
    public void setItem(Items item) {
        this.item = item;
    }
    public Subscriptions getSubscription() {
        return subscription;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }
    public int getAmount() {
        return amount;
    }
    public void setAmount(int amount) {
        this.amount = amount;
    }
}