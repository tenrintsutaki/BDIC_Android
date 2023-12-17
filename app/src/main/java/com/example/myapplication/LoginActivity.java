package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.example.myapplication.Database.MainSQLHelper;
import com.example.myapplication.Model.User;
import com.example.myapplication.ui.DecorationActivity;

public class LoginActivity extends AppCompatActivity {
    private EditText account;
    private EditText password;
    private Button submitButton;
    private Button backButton;
    private MainSQLHelper mainSQLHelper;
    private SQLiteDatabase db;
    private CurrentUser currentUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentUser = (CurrentUser) getApplication();
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.hide();
        }
        setContentView(R.layout.login_activity);
        account = findViewById(R.id.login_account);
        password = findViewById(R.id.login_password);
        submitButton = findViewById(R.id.login_confirm);
        backButton = findViewById(R.id.back_to_register);

        mainSQLHelper = new MainSQLHelper(LoginActivity.this,"SpinNew.db",null,1);
        db = mainSQLHelper.getWritableDatabase();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//
                login();//invoke the login part
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//Transfer to register
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void login(){//including the login function
        String userName = account.getText().toString().trim();
        String userPwd = password.getText().toString().trim();
        System.out.println(userName);
        System.out.println(userPwd);
        if (userName.equals("Name") || userName.equals("")) {//No enter
            System.out.println(userName);
            Toast.makeText(LoginActivity.this, "Please enter your user name!", Toast.LENGTH_SHORT).show();
        } else if (userPwd.equals("")) {//no password
            Toast.makeText(LoginActivity.this, "Please enter your password!", Toast.LENGTH_SHORT).show();
        } else if(!check_valid(userPwd,userName)){//some problems...
            Toast.makeText(LoginActivity.this, "This user not exists or password wrong!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(LoginActivity.this, "Successfully login!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LoginActivity.this, Circular_Button_Test.class);
            startActivity(intent);
        }
    }

    private boolean check_valid(String password,String username){
        //The query statement with the String args.
        Cursor cursor = db.query("User",null,"username=?",new String[]{username},null,null,null,null);
        if(cursor.moveToFirst()){
            do{
                @SuppressLint("Range") String current_password = cursor.getString(cursor.getColumnIndex("password"));
                if(current_password.equals(password)){//check if this is valid
                    @SuppressLint("Range") int ID = cursor.getInt(0);
                    @SuppressLint("Range") int money= cursor.getInt(3);
                    currentUser.setCurrentUser(new User(ID,username,money));
                    return true;//When there is an exists user
                }else{
                    return false;//password is wrong
                }
            }while (cursor.moveToNext());
        }
        cursor.close();//there user is not exist.
        return false;
    }
}
