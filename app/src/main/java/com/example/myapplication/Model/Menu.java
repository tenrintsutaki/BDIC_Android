package com.example.myapplication.Model;

import java.io.Serializable;
//The menu class, for the personal list.
public class Menu implements Serializable {
    private String name;
    private int img_id;
    public Menu(String name,int img_id){
        this.img_id = img_id;
        this.name = name;
    }

    public String get_name(){
        return this.name;
    }

    public void set_name(String name){
        this.name = name;
    }

    public int getImg_id(){
        return this.img_id;
    }
}
