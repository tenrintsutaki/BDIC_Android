package com.example.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.example.myapplication.Model.User;

public class MainPageActivity extends AppCompatActivity {
    private Button turnToMatch;
    private Button leftBtnNavi;
    private Button rightBtnNavi;
    private Button recordBtn;
    private Button singleBtn;
    private Button progressBtn;
    private TextView fadeBar;
    private CurrentUser currentUserApp;
    private User currentUser;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentUserApp = (CurrentUser) getApplication();
        currentUser = currentUserApp.getCurrentUser();
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){//Ignore the ugly actionBar
            actionBar.hide();
        }

        setContentView(R.layout.main_page);

        leftBtnNavi = findViewById(R.id.left);
        rightBtnNavi = findViewById(R.id.right);
        turnToMatch = findViewById(R.id.match);

        leftBtnNavi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainPageActivity.this,PersonalActivity.class);
                startActivity(intent);
            }
        });

        rightBtnNavi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainPageActivity.this,DataActivity.class);
                startActivity(intent);
            }
        });

        turnToMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainPageActivity.this,Circular_Button_Test.class);
                startActivity(intent);
            }
        });

        fadeBar = findViewById(R.id.fadeBar);
        fadeBar.setBackgroundColor(Color.parseColor(currentUserApp.getFadeColor()));

        recordBtn = findViewById(R.id.recordBtn);
        recordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainPageActivity.this,RecordActivity.class);
                startActivity(intent);
            }
        });

        singleBtn = findViewById(R.id.singleBtn);
        singleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainPageActivity.this,SingleRunnerActivity.class);
                startActivity(intent);
            }
        });

        progressBtn = findViewById(R.id.btnProgress);
        progressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainPageActivity.this,ProgressActivity.class);
                startActivity(intent);
            }
        });
    }
}
