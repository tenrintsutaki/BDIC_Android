package com.example.myapplication.Database;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.myapplication.Model.Running;
import com.example.myapplication.SingleRunnerActivity;

import java.util.ArrayList;

public class RunningStorage{
    private SQLiteDatabase db;

    public RunningStorage(SQLiteDatabase db){
        this.db = db;
    }

    public void recordRunning(Running running){//buy a new thing
        ContentValues contentValues = new ContentValues();//Creat a new statement
        contentValues.put("user_id",running.getUser_id());
        contentValues.put("miles",running.getMiles());
        contentValues.put("speed",running.getSpeed());
        contentValues.put("time_consume",running.getTime());
        contentValues.put("energy",running.getEnergy());
        db.insert("Running",null,contentValues);//insert this statement into the db
        contentValues.clear();
    }

    public ArrayList<Running> searchRecords(int user_id){//Search the record of a specific user
        ArrayList<Running> runningList = new ArrayList<>();
        Cursor cursor = db.query("Running",null,"user_id=?",new String[]{user_id+""},null,null,null,null);
        if(cursor.moveToFirst()){
            do{
                @SuppressLint("Range") int id = cursor.getInt(0);
                @SuppressLint("Range") int idOfUser = cursor.getInt(1);
                @SuppressLint("Range") Double miles = cursor.getDouble(2);
                @SuppressLint("Range") int time_consume = cursor.getInt(3);
                @SuppressLint("Range") Double speed = cursor.getDouble(4);
                @SuppressLint("Range") Double energy = cursor.getDouble(4);

                Running running = new Running(id,idOfUser,miles,time_consume,speed,energy);
                runningList.add(running);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return runningList;
    }

    public Double searchRunningMiles(int user_id){//Searching user's sum running miles
        Double miles = 0.0;
        ArrayList<Running> runningList = new ArrayList<>();
        runningList = searchRecords(user_id);
        for (Running running:runningList) {
            miles+=running.getMiles();
        }
        return miles;
    }

    public boolean checkHighestSpeed(int user_id){
        ArrayList<Running> runningList = new ArrayList<>();
        runningList = searchRecords(user_id);
        for (Running running:runningList) {
            if(running.getSpeed()>=10){
                return true;
            }
        }
        return false;
    }

    public boolean checkHighestEnergy(int user_id){
        ArrayList<Running> runningList = new ArrayList<>();
        runningList = searchRecords(user_id);
        for (Running running:runningList) {
            if(running.getEnergy()>=100){
                return true;
            }
        }
        return false;
    }

    public boolean checkHighestTime(int user_id){
        ArrayList<Running> runningList = new ArrayList<>();
        runningList = searchRecords(user_id);
        for (Running running:runningList) {
            if(running.getEnergy()>=600){
                return true;
            }
        }
        return false;
    }

    public boolean checkHighestMiles(int user_id){
        Double miles = searchRunningMiles(user_id);
        if(miles>=3000){
            return true;
        }else {
            return false;
        }
    }

}
