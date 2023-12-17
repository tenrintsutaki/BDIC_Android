package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RotateDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.example.myapplication.Database.MainSQLHelper;
import com.example.myapplication.Database.RepositoryStorage;
import com.example.myapplication.Model.Good;
import com.example.myapplication.Model.Repository;
import com.example.myapplication.ui.BuyDialog;

import java.util.ArrayList;

public class RepositoryActivity extends AppCompatActivity {
    private CurrentUser currentUserApp;

    private Button blue;
    private Button orange;
    private Button cyan;
    private Button sky;
    private Button leaf;
    private Button flame;
    private Button pink;
    private Button backButton;
    private ArrayList<Integer> goodsID;
    private RepositoryStorage repositoryStorage;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){//Ignore the ugly actionBar
            actionBar.hide();
        }

        currentUserApp = (CurrentUser) getApplication();
        colorInit();//Initialize the color of the style
        setContentView(R.layout.repository);

        initDB();
        findYourRepository();//check what you have in your repository

        TextView balance = findViewById(R.id.currentMoney);
        balance.setText(currentUserApp.getCurrentUser().getMoney()+"");

        blue = findViewById(R.id.blue);
        orange = findViewById(R.id.orange);
        cyan = findViewById(R.id.cyan);
        sky = findViewById(R.id.sky);
        leaf = findViewById(R.id.leaf);
        flame = findViewById(R.id.flame);

        //some references of the buttons.
        //They could set the style of the app.

        blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentUserApp.setColors(new String[]{"#FF6200EE","#FF3700B3","#FFBB86FC"});
                finish();
                flush();
                Toast.makeText(RepositoryActivity.this,"Style: Purple",Toast.LENGTH_SHORT).show();
            }
        });

        orange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentUserApp.setColors(new String[]{"#FF9800","#FF5722","#64FF9800"});
                finish();
                flush();
                Toast.makeText(RepositoryActivity.this,"Style: Orange",Toast.LENGTH_SHORT).show();
            }
        });

        cyan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentUserApp.setColors(new String[]{"#FF03DAC5","FF018786","#A501B5B4"});
                finish();
                flush();
                Toast.makeText(RepositoryActivity.this,"Style: Teal",Toast.LENGTH_SHORT).show();
            }
        });

        sky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentUserApp.setColors(new String[]{"#2196F3","#3F51B5","#A100BCD4"});
                finish();
                flush();
                Toast.makeText(RepositoryActivity.this,"Style: Sky",Toast.LENGTH_SHORT).show();
            }
        });

        leaf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentUserApp.setColors(new String[]{"#4CAF50","#005710","#8C8BC34A"});
                finish();
                flush();
                Toast.makeText(RepositoryActivity.this,"Style: Leaf",Toast.LENGTH_SHORT).show();
            }
        });

        flame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentUserApp.setColors(new String[]{"#FF5722","#6F1E05","#82F93425"});
                finish();
                flush();
                Toast.makeText(RepositoryActivity.this,"Style: Flame",Toast.LENGTH_SHORT).show();
            }
        });

        pink = findViewById(R.id.pink);
        if(goodsID.contains(1)) {
            pink.setBackgroundColor(Color.parseColor("#F868E1"));
            pink.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    currentUserApp.setColors(new String[]{"#F868E1","#C2F595E6","#8D1679"});
                    finish();
                    flush();
                }
            });
        }

        Button dark = findViewById(R.id.dark);
        if(goodsID.contains(2)) {
            dark.setBackgroundColor(Color.parseColor("#1E2968"));
            dark.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    currentUserApp.setColors(new String[]{"#1E2968","#84374DCC","#090E29"});
                    finish();
                    flush();
                }
            });
        }

        Button gray = findViewById(R.id.gray);
        if(goodsID.contains(3)) {
            gray.setBackgroundColor(Color.parseColor("#AAA8A9"));
            gray.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }

        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RepositoryActivity.this,PersonalActivity.class);
                startActivity(intent);
            }
        });

    }

    private void colorInit(){//Init the color, when switches the style needed repaint it.
        GradientDrawable drawable = (GradientDrawable) getResources().getDrawable(R.drawable.background_time);
        drawable.setColor(Color.parseColor(currentUserApp.getMainColor()));

        GradientDrawable drawableFade = (GradientDrawable) getResources().getDrawable(R.drawable.background_time_small);
        drawableFade.setColor(Color.parseColor(currentUserApp.getMainColor()));

        GradientDrawable drawableInput = (GradientDrawable) getResources().getDrawable(R.drawable.background_input);
        drawableInput.setStroke(1,Color.parseColor(currentUserApp.getMainColor()),25,4);

        GradientDrawable drawableBorder = (GradientDrawable) getResources().getDrawable(R.drawable.border_dialog);
        drawableBorder.setStroke(5,Color.parseColor(currentUserApp.getMainColor()),30,5);

        GradientDrawable drawableCircular = (GradientDrawable) getResources().getDrawable(R.drawable.circular);
        drawableCircular.setColor(Color.parseColor(currentUserApp.getMainColor()));

        GradientDrawable drawableAvatar = (GradientDrawable) getResources().getDrawable(R.drawable.cicular_ring_avatar);
        drawableAvatar.setColors(new int[]{Color.parseColor(currentUserApp.getMainColor()),Color.parseColor(currentUserApp.getMainColor())});

        RotateDrawable drawableArrow = (RotateDrawable) getResources().getDrawable(R.drawable.arrow);
        GradientDrawable arrow = (GradientDrawable) drawableArrow.getDrawable();
        arrow.setColor(Color.parseColor(currentUserApp.getMainColor()));

        GradientDrawable mainBackGround = (GradientDrawable) getResources().getDrawable(R.drawable.background_main);
        mainBackGround.setColor(Color.parseColor(currentUserApp.getFadeColor()));

        //I will write every drawable on this part in order to change their colors.
    }

    private void flush(){//refresh current page
        Intent intent = new Intent(this, RepositoryActivity.class);
        startActivity(intent);
    }

    public void changeBackGround(View view){
        String nameBG = view.getTag().toString();
        switch (nameBG){
            case "blue":
                currentUserApp.setBackGroundResource(R.drawable.blue_background);
                Toast.makeText(this.getApplicationContext(),"Changed successfully",Toast.LENGTH_SHORT).show();
                break;
            case "yellow":
                currentUserApp.setBackGroundResource(R.drawable.yello_background);
                Toast.makeText(this.getApplicationContext(),"Changed successfully",Toast.LENGTH_SHORT).show();
                break;
            case "green":
                currentUserApp.setBackGroundResource(R.drawable.green_background);
                Toast.makeText(this.getApplicationContext(),"Changed successfully",Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void initDB(){
        MainSQLHelper mainSQLHelper = new MainSQLHelper(RepositoryActivity.this,"SpinNew.db",null,1);
        SQLiteDatabase db =  mainSQLHelper.getWritableDatabase();
        repositoryStorage = new RepositoryStorage();
        repositoryStorage.setDB(db);
    }

    public void findYourRepository(){
        goodsID = repositoryStorage.getUserRepository(currentUserApp.getCurrentUser().getId());
    }
}
