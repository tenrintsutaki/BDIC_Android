package com.example.myapplication.Model;

public class Good {//Represent the good
    private int id;
    private int resource_id;
    private int price;
    private String name;
    private String describe;

    public Good(int id, int resource_id, int price, String name, String describe) {
        this.id = id;
        this.price = price;
        this.resource_id = resource_id;
        this.name = name;
        this.describe = describe;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getResource_id() {
        return resource_id;
    }

    public void setResource_id(int resource_id) {
        this.resource_id = resource_id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }
}
