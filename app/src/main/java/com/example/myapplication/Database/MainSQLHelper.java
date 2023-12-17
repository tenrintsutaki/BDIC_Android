package com.example.myapplication.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;
import androidx.annotation.Nullable;

/**
 * This is a helper that could connect to the SQLITE database.
 */
public class MainSQLHelper extends SQLiteOpenHelper {
    private Context context;

    //Just Create a new Table

    private static final String CREATE_USER = "CREATE TABLE User(id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,username TEXT,password TEXT)";
    private static final String CREATE_GOOD = "CREATE TABLE Good(id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,price INTEGER,resourceID INTEGER,name TEXT,describe TEXT)";
    private static final String CREATE_REPOSITORY = "CREATE TABLE Repository(id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,user_id INTEGER,good_id INTEGER)";
    private static final String CREATE_RUNNING = "CREATE TABLE Running(id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,user_id INTEGER,miles REAL,time_consume INTEGER,speed REAL,energy REAL)";

    public MainSQLHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER);
        db.execSQL(CREATE_GOOD);
        db.execSQL(CREATE_REPOSITORY);
        db.execSQL(CREATE_RUNNING);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
