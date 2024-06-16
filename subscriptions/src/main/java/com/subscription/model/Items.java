package com.subscription.model;
public class Items {
    private enum Type {
        plan, addon;
    }
    private int id;
    private String name;
    private int price;
    private int is_active;
    private Type type;

    public Items () {

    }
    
    public Items (int id, String name, int price, int is_active, Type type ){
        this.id = id;
        this.name = name;
        this.price = price;
        this.is_active = is_active;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getIs_active() {
        return is_active;
    }

    public void setIs_active(int is_active) {
        this.is_active = is_active;
    }
    
    public String getType(){
        return type.toString();
    }

    public void setType(String type) {
        this.type = Type.valueOf(type);
    }
}