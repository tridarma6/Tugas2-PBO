package com.subscription.model;
public class SubscriptionItems{
    
    private int subscription;
    private int item;
    private int quantity;
    private int price;
    private int amount;
    
    public SubscriptionItems(){

    }

    public SubscriptionItems(int subscription, int item, int quantity, int price, int amount) {
        this.subscription = subscription;
        this.item = item;
        this.quantity = quantity;
        this.price = price;
        this.amount = amount;
    }
    public int getSubscription() {
        return subscription;
    }
    public void setSubscription(int subscription) {
        this.subscription = subscription;
    }
    public int getItem() {
        return item;
    }
    public void setItem(int item) {
        this.item = item;
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