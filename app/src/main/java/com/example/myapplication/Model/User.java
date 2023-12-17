package com.example.myapplication.Model;

import java.io.Serializable;

public class User implements Serializable {
    private final int id;
    private final String userName;
    private int iconID;
    private int money;

    public User(int id,String userName,int money){
        this.id = id;
        this.userName = userName;
        this.money = money;
    }

    public int getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money){
        this.money = money;
    }

    public int getIconID() {
        return iconID;
    }
}
