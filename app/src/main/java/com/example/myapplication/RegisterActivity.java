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
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.example.myapplication.Database.MainSQLHelper;

public class RegisterActivity extends AppCompatActivity {
    private EditText account;
    private EditText password;
    private EditText cofirmPw;
    private Button submitButton;
    private Button backButton;
    private MainSQLHelper mainSQLHelper;
    private SQLiteDatabase db;


    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        mainSQLHelper = new MainSQLHelper(RegisterActivity.this,"SpinNew.db",null,1);
        db = mainSQLHelper.getWritableDatabase();
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.hide();
        }//Hide the ugly action bar
        setContentView(R.layout.register_activity);
        account = findViewById(R.id.login_account);
        password = findViewById(R.id.login_password);
        cofirmPw = findViewById(R.id.confirm_password);
        submitButton = findViewById(R.id.login_confirm);
        backButton = findViewById(R.id.back_to_register);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//
                register();
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void register() {
        String userName = account.getText().toString().trim();
        String userPwd = password.getText().toString().trim();
        String confirmPW = cofirmPw.getText().toString().trim();
        System.out.println(userName);
        System.out.println(userPwd);

        if (userName.equals("Name") || userName.equals("")) {
            System.out.println(userName);
            Toast.makeText(RegisterActivity.this, "Please enter your user name!", Toast.LENGTH_SHORT).show();
        } else if (userPwd.equals("")) {
            Toast.makeText(RegisterActivity.this, "Please enter your password!", Toast.LENGTH_SHORT).show();
        } else if(!check_valid(userName)){
            Toast.makeText(RegisterActivity.this, "This user already exists!", Toast.LENGTH_SHORT).show();
        }else if (!confirmPW.equals(userPwd)){
            Toast.makeText(RegisterActivity.this, "Please enter two same password!", Toast.LENGTH_SHORT).show();
        }else{
            ContentValues contentValues = new ContentValues();//Creat a new statement
            contentValues.put("username",userName);//put the user into the database.
            contentValues.put("password",userPwd);
            contentValues.put("money",1000);
            db.insert("User",null,contentValues);//insert this statement into the db
            contentValues.clear();
            Toast.makeText(RegisterActivity.this, "Successfully registered!", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean check_valid(String username){//Is the user already exist?
        Cursor cursor = db.query("User",null,null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do{
                @SuppressLint("Range") String current_username = cursor.getString(cursor.getColumnIndex("username"));
                System.out.println(current_username);
                if(current_username.equals(username)){
                    return false;//When there is an exists user
                }
            }while (cursor.moveToNext());
        }
        cursor.close();
        return true;
    }
}
