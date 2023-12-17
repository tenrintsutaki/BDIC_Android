package com.example.myapplication;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.example.myapplication.Model.Menu;
import com.example.myapplication.Model.User;
import com.example.myapplication.ui.PersonalView;

import java.util.ArrayList;
import java.util.Objects;

/**
 * This is a class for presenting personal page with relevant information, like avatar, username and others
 * Also will provide decorations or customized information in Beta version
 * @author Aliosha Louyiming
 */
public class PersonalActivity extends AppCompatActivity {//Present the personal account page
    private ListView listView;
    private ArrayList<Menu> menus;
    private PersonalView personalView;

    private ImageView userBackGround;

    private Button buttonHome;
    private Button buttonProfile;
    private Button buttonMatch;
    private Button buttonData;
    private TextView fadeBar;

    private CurrentUser currentUserApp;
    private User currentUser;
    private TextView userName;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        currentUserApp = (CurrentUser) getApplication();
        currentUser = currentUserApp.getCurrentUser();

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){//Ignore the ugly actionBar
            actionBar.hide();
        }
        initMenu();

        //Set some things...
        setContentView(R.layout.presonal_page);

        listView = findViewById(R.id.menu_list);
        buttonData = findViewById(R.id.button6);
        fadeBar = findViewById(R.id.textView5);
        fadeBar.setBackgroundColor(Color.parseColor(currentUserApp.getFadeColor()));
        buttonProfile = findViewById(R.id.button7);


        userBackGround = findViewById(R.id.user_background);
        userBackGround.setImageResource(currentUserApp.getBackGroundResource());

        userName = findViewById(R.id.user_name);//Set the current user's name
        userName.setText(currentUser.getUserName());
        if(!Objects.equals(currentUser.getUserName(), "Guest")) {//If this is the User not the Guest
            buttonHome = findViewById(R.id.button_person);
            buttonHome.setText("Logout");
            buttonHome.setOnClickListener(new View.OnClickListener() {//Go to the login part
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(PersonalActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            });
        }else{
            buttonHome = findViewById(R.id.button_person);
            buttonHome.setOnClickListener(new View.OnClickListener() {//Go to the login part
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(PersonalActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            });
            buttonHome.setText("Login");
        }

        buttonMatch = findViewById(R.id.button5);
        buttonMatch.setOnClickListener(new View.OnClickListener() {//To the matching page
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PersonalActivity.this,MainPageActivity.class);
                startActivity(intent);
            }
        });

        buttonData = findViewById(R.id.button6);
        buttonData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PersonalActivity.this,DataActivity.class);
                startActivity(intent);
            }
        });

        personalView = new PersonalView(PersonalActivity.this,R.layout.personal_view_list,menus);
        listView.setAdapter(personalView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (Objects.equals(menus.get(i).get_name(), "Repository")) {
                    Intent intent = new Intent(PersonalActivity.this, RepositoryActivity.class);
                    startActivity(intent);
                } else if (Objects.equals(menus.get(i).get_name(), "Store")) {
                    Intent intent = new Intent(PersonalActivity.this, StoreActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void initMenu(){//Create the icons on the list view.
        menus = new ArrayList<Menu>();
        menus.add(new Menu("Profile",R.drawable.ic_profile));
        menus.add(new Menu("Store", R.drawable.ic_home));
        menus.add(new Menu("My Goods", R.drawable.ic_card));
        menus.add(new Menu("Repository", R.drawable.ic_tik_tok));
        menus.add(new Menu("Favours", R.drawable.ic_navigation));
    }

}
