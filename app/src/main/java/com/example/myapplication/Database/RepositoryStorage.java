package com.example.myapplication.Database;


import android.annotation.SuppressLint;
import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.myapplication.LoginActivity;
import com.example.myapplication.Model.Good;
import com.example.myapplication.Model.User;

import java.util.ArrayList;

public class RepositoryStorage{
    private SQLiteDatabase db;

    public void setDB(SQLiteDatabase db){
        this.db = db;
    }

    public void buyNewThing(int user_id,int good_id,int money){//buy a new thing
        ContentValues contentValues = new ContentValues();//Creat a new statement
        contentValues.put("user_id",user_id);//put the new information to the DB
        contentValues.put("good_id",good_id);
        db.insert("Repository",null,contentValues);//insert this statement into the db
        updateUserMoney(user_id,money);//update the money of a user
        contentValues.clear();
    }


    public void updateUserMoney(int user_id,int money){//buy or gain money of a user
        int currentMoney = 0;
        Cursor cursor = db.query("User",new String[]{"money"},"id=?",new String[]{String.valueOf(user_id)},null,null,null,null);
        if(cursor.moveToFirst()){
            do{
                @SuppressLint("Range") Integer current_money = cursor.getInt(cursor.getColumnIndex("money"));
                currentMoney = current_money;//get the current money of a user.
            }while (cursor.moveToNext());
        }
        ContentValues values = new ContentValues();
        values.put("money",currentMoney+money);//update this money
        db.update("User",values,"id=?",new String[]{String.valueOf(user_id)});
        values.clear();
        cursor.close();
    }

    public ArrayList<Integer> getUserRepository(int user_id){//Return the good id belongs to this user
        ArrayList<Integer> id_List = new ArrayList<>();
        Cursor cursor = db.query("Repository",new String[]{"good_id"},"user_id=?",new String[]{String.valueOf(user_id)},null,null,null,null);
        if(cursor.moveToFirst()){
            do{
                @SuppressLint("Range") Integer idOfGood = cursor.getInt(cursor.getColumnIndex("good_id"));
                id_List.add(idOfGood);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return id_List;
    }

    public Good searchGood(int good_id){
        Cursor cursor = db.query("Good",null,"id=?",new String[]{String.valueOf(good_id)},null,null,null,null);
        if(cursor.moveToFirst()){
            do{
                int idOfGood = cursor.getInt(0);
                int price = cursor.getInt(1);
                int resourceID = cursor.getInt(2);
                String name = cursor.getString(3);
                String describe = cursor.getString(4);
                Good good = new Good(idOfGood,resourceID,price,name,describe);
                return good;
            }while (cursor.moveToNext());
        }
        cursor.close();
        return null;
    }

    public ArrayList<Good> searchAllGood(){
        ArrayList<Good> goods = new ArrayList<>();
        Cursor cursor = db.query("Good",null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do{
                int idOfGood = cursor.getInt(0);
                int price = cursor.getInt(1);
                int resourceID = cursor.getInt(2);
                String name = cursor.getString(3);
                String describe = cursor.getString(4);
                Good good = new Good(idOfGood,price,resourceID,name,describe);
                goods.add(good);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return goods;
    }

    public ArrayList<Integer> searchAllID(){
        ArrayList<Integer> IDs = new ArrayList<>();
        Cursor cursor = db.query("Good",null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do{
                int idOfGood = cursor.getInt(0);
                IDs.add(idOfGood);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return IDs;
    }

    public ArrayList<Good> printStoreGoods(int user_id){
        ArrayList<Integer> allID = searchAllID();
        ArrayList<Integer> userAlreadyHaveID = getUserRepository(user_id);
        ArrayList<Good> goods = new ArrayList<>();
        allID.removeAll(userAlreadyHaveID);
        for (Integer i:allID) {
            goods.add(searchGood(i));
        }
        return goods;//Return the list that the user not already buy yet.
    }

    public void insertGood(Good good){
        ContentValues contentValues = new ContentValues();//Creat a new statement
        contentValues.put("name",good.getName());//put the new information to the DB
        contentValues.put("resourceID",good.getResource_id());
        contentValues.put("price",good.getPrice());
        contentValues.put("describe",good.getDescribe());
        db.insert("Good",null,contentValues);//insert this statement into the db
    }
}
