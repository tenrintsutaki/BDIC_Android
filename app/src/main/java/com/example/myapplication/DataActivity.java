package com.example.myapplication;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.example.myapplication.Database.MainSQLHelper;
import com.example.myapplication.Database.RunningStorage;
import com.example.myapplication.Model.Running;
import com.example.myapplication.SensorSteps.RunningListener;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.*;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is for recording data for the user and present them.
 * Referenced on the MPAndroid Chart API
 * @author Aliosha Louyiming
 */

public class DataActivity extends AppCompatActivity {//Presenting step count and some data
    private SensorManager sensorManager;
    private RunningListener runningListener;
    private Button leftBtnNavi;
    private Button midBtnNavi;
    private Button backBtn;
    private Button nextPage;
    private Button lastPage;
    private LineChart lineChart;
    private BarChart barChart;
    private List<Entry> entryList;
    private List<BarEntry> barEntries;
    private TextView fadeBar;
    private CurrentUser currentUserApp;
    private int currentPage;
    private SQLiteDatabase db;
    private RunningStorage runningStorage;
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentUserApp = (CurrentUser) getApplication();
        currentPage = currentUserApp.getCurrentPage();
        title = findViewById(R.id.graphTitle);

        db = dbInit();
        runningStorage = new RunningStorage(db);


        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {//Ignore the ugly actionBar
            actionBar.hide();
        }

        if(currentPage == 1){//Set current page
            initPage_1();
        }else if(currentPage == 2){
            initPage_2();
        }else if(currentPage == 3){
            initPage_3();
        }
    }


    public void initPage_1(){//Page 1 init
            setContentView(R.layout.data_page);

            TextView title = findViewById(R.id.graphTitle);
            title.setText("Running Miles");
            lineChart = findViewById(R.id.line_chart1);
            barChart = findViewById(R.id.line_chart2);
            ArrayList<Running> runningRecords = runningStorage.searchRecords(currentUserApp.getCurrentUser().getId());

            entryList = new ArrayList<>();
        for (int i = 0; i < runningRecords.size(); i++) {
            entryList.add(new Entry(i, (float) runningRecords.get(i).getMiles()));
        }
        //put some data in it

            barEntries = new ArrayList<>();
        for (int i = 0; i < runningRecords.size(); i++) {
            barEntries.add(new BarEntry(i, (float) runningRecords.get(i).getMiles()));
        }
            //put some data in it

            LineDataSet lineDataSet = new LineDataSet(entryList,"Running Miles");
            lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            lineDataSet.setDrawFilled(true);
            lineDataSet.setDrawCircles(false);
            lineDataSet.setFillColor(Color.BLUE);
            lineDataSet.setLineWidth(2.0f);
            //Set the style of the line data

            BarDataSet barDataSet = new BarDataSet(barEntries,"");

            LineData lineData = new LineData(lineDataSet);
            BarData barData = new BarData(barDataSet);

            lineChart.setData(lineData);
            lineChart.getXAxis().setDrawGridLines(false);
            lineChart.getAxisRight().setDrawGridLines(false);
            lineChart.getAxisLeft().setDrawGridLines(false);
            lineChart.invalidate();
            //set the style of the line chart

            barChart.setData(barData);
            barChart.getXAxis().setDrawGridLines(false);
            barChart.getXAxis().setDrawAxisLine(false);
            barChart.getAxisRight().setDrawGridLines(false);
            barChart.getAxisLeft().setDrawGridLines(false);
            barChart.invalidate();
            //for the bar chart....


            leftBtnNavi = findViewById(R.id.buttonHome);
            midBtnNavi = findViewById(R.id.buttonMatch);
            backBtn = findViewById(R.id.backButton2);
            fadeBar = findViewById(R.id.textView8);
            fadeBar.setBackgroundColor(Color.parseColor(currentUserApp.getFadeColor()));


            leftBtnNavi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(DataActivity.this,PersonalActivity.class);
                    startActivity(intent);
                }
            });

            midBtnNavi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(DataActivity.this,MainPageActivity.class);
                    startActivity(intent);
                }
            });

            backBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(DataActivity.this,Circular_Button_Test.class);
                    startActivity(intent);
                }
            });

            lastPage = findViewById(R.id.left_arrow);
            lastPage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    currentUserApp.setCurrentPage(checkCurrentPageMinus(currentUserApp.getCurrentPage()));
                    Toast.makeText(DataActivity.this,currentPage+"",Toast.LENGTH_SHORT).show();
                    finish();
                    flush();
                }
            });

            nextPage = findViewById(R.id.right_arrow);
            nextPage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    currentUserApp.setCurrentPage(checkCurrentPagePlus(currentUserApp.getCurrentPage()));
                    Toast.makeText(DataActivity.this,currentPage+"",Toast.LENGTH_SHORT).show();
                    finish();
                    flush();
                }
            });

        }

    public void initPage_2(){//For the page 2 init
        setContentView(R.layout.data_page_2);
        title = findViewById(R.id.graphTitle);
        title.setText("Running Speed");
        lineChart = findViewById(R.id.line_chart1);
        barChart = findViewById(R.id.line_chart2);
        ArrayList<Running> runningRecords = runningStorage.searchRecords(currentUserApp.getCurrentUser().getId());

        entryList = new ArrayList<>();
        for (int i = 0; i < runningRecords.size(); i++) {
            entryList.add(new Entry(i, (float) runningRecords.get(i).getSpeed()));
        }
        //put some data in it

        barEntries = new ArrayList<>();
        for (int i = 0; i < runningRecords.size(); i++) {
            barEntries.add(new BarEntry(i, (float) runningRecords.get(i).getSpeed()));
        }
        //put some data in it

        LineDataSet lineDataSet = new LineDataSet(entryList,"Running Speed");
        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        lineDataSet.setDrawFilled(true);
        lineDataSet.setDrawCircles(false);
        lineDataSet.setFillColor(Color.RED);
        lineDataSet.setLineWidth(2.0f);
        //Set the style of the line data

        BarDataSet barDataSet = new BarDataSet(barEntries,"");
        barDataSet.setColor(Color.RED);

        LineData lineData = new LineData(lineDataSet);
        BarData barData = new BarData(barDataSet);

        lineChart.setData(lineData);
        lineChart.getXAxis().setDrawGridLines(false);
        lineChart.getAxisRight().setDrawGridLines(false);
        lineChart.getAxisLeft().setDrawGridLines(false);
        lineChart.invalidate();
        //set the style of the line chart

        barChart.setData(barData);
        barChart.getXAxis().setDrawGridLines(false);
        barChart.getXAxis().setDrawAxisLine(false);
        barChart.getAxisRight().setDrawGridLines(false);
        barChart.getAxisLeft().setDrawGridLines(false);
        barChart.invalidate();
        //for the bar chart....


        leftBtnNavi = findViewById(R.id.buttonHome);
        midBtnNavi = findViewById(R.id.buttonMatch);
        backBtn = findViewById(R.id.backButton2);
        fadeBar = findViewById(R.id.textView8);
        fadeBar.setBackgroundColor(Color.parseColor(currentUserApp.getFadeColor()));

        lastPage = findViewById(R.id.left_arrow);
        lastPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentUserApp.setCurrentPage(checkCurrentPageMinus(currentUserApp.getCurrentPage()));
                Toast.makeText(DataActivity.this,currentPage+"",Toast.LENGTH_SHORT).show();
                finish();
                flush();
            }
        });

        nextPage = findViewById(R.id.right_arrow);
        nextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentUserApp.setCurrentPage(checkCurrentPagePlus(currentUserApp.getCurrentPage()));
                Toast.makeText(DataActivity.this,currentPage+"",Toast.LENGTH_SHORT).show();
                finish();
                flush();
            }
        });
    }

    public void initPage_3(){
        setContentView(R.layout.data_page_3);
        title = findViewById(R.id.graphTitle);
        title.setText("Running Time");
        lineChart = findViewById(R.id.line_chart1);
        barChart = findViewById(R.id.line_chart2);
        ArrayList<Running> runningRecords = runningStorage.searchRecords(currentUserApp.getCurrentUser().getId());

        entryList = new ArrayList<>();
        for (int i = 0; i < runningRecords.size(); i++) {
            entryList.add(new Entry(i, (float) runningRecords.get(i).getTime()));
        }
        //put some data in it

        barEntries = new ArrayList<>();
        for (int i = 0; i < runningRecords.size(); i++) {
            barEntries.add(new BarEntry(i, (float) runningRecords.get(i).getTime()));
        }
        //put some data in it

        LineDataSet lineDataSet = new LineDataSet(entryList,"Running Time");
        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        lineDataSet.setDrawFilled(true);
        lineDataSet.setDrawCircles(false);
        lineDataSet.setFillColor(Color.GREEN);
        lineDataSet.setLineWidth(2.0f);
        //Set the style of the line data

        BarDataSet barDataSet = new BarDataSet(barEntries,"");
        barDataSet.setColor(Color.GREEN);

        LineData lineData = new LineData(lineDataSet);
        BarData barData = new BarData(barDataSet);

        lineChart.setData(lineData);
        lineChart.getXAxis().setDrawGridLines(false);
        lineChart.getAxisRight().setDrawGridLines(false);
        lineChart.getAxisLeft().setDrawGridLines(false);
        lineChart.invalidate();
        //set the style of the line chart

        barChart.setData(barData);
        barChart.getXAxis().setDrawGridLines(false);
        barChart.getXAxis().setDrawAxisLine(false);
        barChart.getAxisRight().setDrawGridLines(false);
        barChart.getAxisLeft().setDrawGridLines(false);
        barChart.invalidate();
        //for the bar chart....


        leftBtnNavi = findViewById(R.id.buttonHome);
        midBtnNavi = findViewById(R.id.buttonMatch);
        backBtn = findViewById(R.id.backButton2);
        fadeBar = findViewById(R.id.textView8);
        fadeBar.setBackgroundColor(Color.parseColor(currentUserApp.getFadeColor()));

        lastPage = findViewById(R.id.left_arrow);
        lastPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentUserApp.setCurrentPage(checkCurrentPageMinus(currentUserApp.getCurrentPage()));
                Toast.makeText(DataActivity.this,currentPage+"",Toast.LENGTH_SHORT).show();
                finish();
                flush();
            }
        });

        nextPage = findViewById(R.id.right_arrow);
        nextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentUserApp.setCurrentPage(checkCurrentPagePlus(currentUserApp.getCurrentPage()));
                Toast.makeText(DataActivity.this,currentPage+"",Toast.LENGTH_SHORT).show();
                finish();
                flush();
            }
        });
    }

    private int checkCurrentPagePlus(int currentPage){//Next Page
        if(currentPage==3){
            return 1;
        }else{
            return currentPage+1;
        }
    }

    private int checkCurrentPageMinus(int currentPage){//Last Page
        if(currentPage==1){
            return 3;
        }else{
            return currentPage-1;
        }
    }

    public void getStep() {
        runningListener = new RunningListener();

        sensorManager.registerListener(runningListener, sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR),
                SensorManager.SENSOR_DELAY_NORMAL);

        sensorManager.registerListener(runningListener, sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER),
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    private void flush(){//refresh current page
        Intent intent = new Intent(this, DataActivity.class);
        startActivity(intent);
    }

    public SQLiteDatabase dbInit(){
        MainSQLHelper mainSQLHelper = new MainSQLHelper(DataActivity.this,"SpinNew.db",null,1);
        return mainSQLHelper.getWritableDatabase();
    }
}
