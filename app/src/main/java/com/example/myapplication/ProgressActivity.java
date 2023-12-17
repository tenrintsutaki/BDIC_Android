package com.example.myapplication;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.example.myapplication.Database.MainSQLHelper;
import com.example.myapplication.Database.RunningStorage;
import com.example.myapplication.Model.User;

public class ProgressActivity extends AppCompatActivity {
    Double currentMiles = 0.0;
    private CurrentUser currentUserApp;
    private User currentUser;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progress_page);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){//Ignore the ugly actionBar
            actionBar.hide();
        }
        currentUserApp = (CurrentUser) getApplication();
        currentUser = currentUserApp.getCurrentUser();

        RunningStorage runningStorage = new RunningStorage(dbInit());
        currentMiles = runningStorage.searchRunningMiles(currentUser.getId());

        progressBar = findViewById(R.id.progressBar);//Max number of it is 100
        progressBar.setProgress(currentMiles.intValue());
        checkProgress();

        Button toTask = findViewById(R.id.btn_task_page);
        toTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProgressActivity.this,TaskActivity.class);
                startActivity(intent);
            }
        });
    }

    public SQLiteDatabase dbInit(){
        MainSQLHelper mainSQLHelper = new MainSQLHelper(ProgressActivity.this,"SpinNew.db",null,1);
        return mainSQLHelper.getWritableDatabase();
    }

    public void checkProgress(){//set the visibility of the correct icons.
        TextView miles = findViewById(R.id.currentMiles);
        miles.setText(currentMiles.intValue()+"/100");

        if(currentMiles.intValue()>=25){
            ImageView correct25 = findViewById(R.id.correct25);
            correct25.setVisibility(View.VISIBLE);
        }

        if(currentMiles.intValue()>=50){
            ImageView correct50 = findViewById(R.id.correct50);
            correct50.setVisibility(View.VISIBLE);
        }

        if(currentMiles.intValue()>=75){
            ImageView correct75 = findViewById(R.id.correct75);
            correct75.setVisibility(View.VISIBLE);
        }

        if(currentMiles.intValue()>=100){
            ImageView correct100 = findViewById(R.id.correct100);
            correct100.setVisibility(View.VISIBLE);
        }
    }
}
