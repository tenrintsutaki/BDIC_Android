package com.example.myapplication.Database;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.myapplication.Model.Running;
import com.example.myapplication.Model.Task;

import java.util.ArrayList;

public class TaskStorage {
    private SQLiteDatabase db;

    public TaskStorage(SQLiteDatabase db){
        this.db = db;
    }

    public ArrayList<Task> findAllTasks(){
        ArrayList<Task> taskList = new ArrayList<>();
        Cursor cursor = db.query("Task",null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do{
                @SuppressLint("Range") int id = cursor.getInt(0);
                @SuppressLint("Range") String name = cursor.getString(1);
                @SuppressLint("Range") String content = cursor.getString(2);
                @SuppressLint("Range") int condition = cursor.getInt(3);
                @SuppressLint("Range") int reward = cursor.getInt(4);
                Task task = new Task(id,name,content,condition,reward);
                taskList.add(task);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return taskList;
    }

    public void deleteTask(int task_id){

    }
}
