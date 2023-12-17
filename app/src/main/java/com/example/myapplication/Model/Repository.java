package com.example.myapplication.Model;

public class Repository {
    //To store the buy information of a user
    private int id;// ID of the good the user buy
    private int good_id;
    private int user_id;

    Repository(int id,int user_id, int good_id,int resource_id){
        this.id = id;
        this.good_id = good_id;
        this.user_id = user_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getGood_id() {
        return good_id;
    }

    public void setGood_id(int good_id) {
        this.good_id = good_id;
    }
}
