package com.example.myapplication;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.example.myapplication.Database.MainSQLHelper;
import com.example.myapplication.Database.RunningStorage;
import com.example.myapplication.Model.Running;
import com.example.myapplication.Model.User;
import com.example.myapplication.ui.PersonalView;
import com.example.myapplication.ui.RecordView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Objects;

public class RecordActivity extends AppCompatActivity {

    private CurrentUser currentUserApp;
    private User currentUser;
    private ListView listView;
    private ArrayList<Running> menus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record_page);
        currentUserApp = (CurrentUser) getApplication();
        currentUser = currentUserApp.getCurrentUser();
        initMenu(currentUser.getId());

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){//Ignore the ugly actionBar
            actionBar.setTitle("Running Records");
        }

        listView = findViewById(R.id.records_list);

        RecordView recordView = new RecordView(RecordActivity.this,R.layout.record_view_list,menus);
        listView.setAdapter(recordView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });


    }

    public SQLiteDatabase dbInit(){
        MainSQLHelper mainSQLHelper = new MainSQLHelper(RecordActivity.this,"SpinNew.db",null,1);
        return mainSQLHelper.getWritableDatabase();
    }

    public void initMenu(int user_id){
        RunningStorage runningStorage = new RunningStorage(dbInit());//bind the db to the search class
        menus = runningStorage.searchRecords(user_id);
    }
}
