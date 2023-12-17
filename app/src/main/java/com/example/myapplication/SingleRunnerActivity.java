package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.*;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.baidu.platform.comapi.basestruct.Point;
import com.example.myapplication.Database.MainSQLHelper;
import com.example.myapplication.Database.RepositoryStorage;
import com.example.myapplication.Database.RunningStorage;
import com.example.myapplication.Model.Running;
import com.example.myapplication.SensorSteps.RunningListener;
import com.example.myapplication.ui.RunDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class SingleRunnerActivity extends AppCompatActivity implements SensorEventListener {
    private BaiduMap mBaiduMap;
    private LocationClient mLocationClient;
    private MyLocationListener myLocationListener;
    private CurrentUser currentApp;
    private TextView fadeBar;
    private TextureMapView mapView;
    private Button back;
    private Button sportBtn;
    private Button data;
    private List<LatLng> points = new ArrayList<LatLng>();
    private Boolean isRunning;
    private RunDialog runDialog;

    private RunningStorage runningStorage;
    private SensorManager sensorManager;
    private Sensor tempSensor;
    private Sensor humiditySensor;
    private Sensor pressureSensor;
    private RunningListener runningListener = new RunningListener();

    private double miles;
    private int second = 0;
    private double speed;
    private double energy;

    private float temperature = 25.0f;
    private float pressure = 66.0f;
    private float humidity = 0.3f;

    private Timer timer;


    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        isRunning = false;
        currentApp = (CurrentUser) getApplication();

        SQLiteDatabase database = dbInit();
        runningStorage = new RunningStorage(database);

        ActionBar actionBar = getSupportActionBar();

        //find the textview about data.

        if(actionBar != null){//Ignore the ugly actionBar
            actionBar.setTitle("Single Runner");
//            actionBar.hide();
        }

        sensorManager=(SensorManager)getSystemService(Context.SENSOR_SERVICE);


        tempSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        humiditySensor = sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
        pressureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);

        sensorManager.registerListener(this,tempSensor,SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this,humiditySensor,SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this,pressureSensor,SensorManager.SENSOR_DELAY_NORMAL);

        setContentView(R.layout.single_runner_layout);

        fadeBar = findViewById(R.id.textView4);
        fadeBar.setBackgroundColor(Color.parseColor(currentApp.getFadeColor()));

        sportBtn = findViewById(R.id.button8);
        sportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SingleRunnerActivity.this,pressure+"",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SingleRunnerActivity.this,MainPageActivity.class);
                startActivity(intent);
            }
        });

        Button homeBtn = findViewById(R.id.button10);
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SingleRunnerActivity.this,PersonalActivity.class);
                startActivity(intent);
            }
        });

        data = findViewById(R.id.button9);
        data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SingleRunnerActivity.this,DataActivity.class);
                startActivity(intent);
            }
        });

        mapView = findViewById(R.id.map);

        mBaiduMap = mapView.getMap();

        mBaiduMap.setMyLocationEnabled(true);

        try {
            mLocationClient = new LocationClient(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        back = findViewById(R.id.backToCenter);
        back.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View view) {
                backToCenter();//Back to the center of the user

                RunDialog runDialog = new RunDialog(SingleRunnerActivity.this);
                runDialog.setListener(new RunDialog.dialogClickListener() {
                    @Override
                    public void onClick(View view) {
                            switch(view.getId()) {
                                case R.id.start_run://Start run click.
                                    isRunning = true;
                                    points = new ArrayList<>();
                                    Toast.makeText(SingleRunnerActivity.this,"Start Running!",Toast.LENGTH_SHORT).show();
                                    runDialog.startTime();
                                    startTime();
                                    runDialog.setCurrentTime(second);//sync the time in these two activity
                                    break;
                                case R.id.stop_run://To stop the run
                                    isRunning = false;
                                    calculateRunning();
                                    points = new ArrayList<>();
                                    Toast.makeText(SingleRunnerActivity.this,"Running Stopped and Saved!",Toast.LENGTH_SHORT).show();
                                    saveRunning(new Running(currentApp.getCurrentUser().getId(),miles,second,speed,energy));//Save in the DB
                                    initRunning();//set the data to null.
                                    stopTime();
                                    runDialog.setCurrentTime(0);
                                    runDialog.stopTime();
                                    break;
                            }
                    }
                });

                Window dialogWindow = runDialog.getWindow();
                dialogWindow.getDecorView().setPadding(0, 0, 0, 0);
                dialogWindow.setGravity(Gravity.TOP);
                WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // get parameters
                lp.alpha = 9f; // Transparency
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.horizontalMargin = 0;
                dialogWindow.setAttributes(lp);
                runDialog.show();

                if(isRunning) {//update the running information
                    runDialog.updateRunningData(points);//update the information first.
                    runDialog.setCurrentTime(second);
                    runDialog.startTime();//Start the time each time close and re-open it.
                }
                runDialog.setSensors(temperature,pressure,humidity);
                runDialog.initDialog();
            }
        });

        //User LocationClientOption set some parameters
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // Open the GPS function
        option.setCoorType("bd09ll"); // set the type...
        option.setScanSpan(1000);

        //Set the locationClientOption
        mLocationClient.setLocOption(option);

        //Register the LocationListener listener
        myLocationListener = new MyLocationListener(mBaiduMap);
        mLocationClient.registerLocationListener(myLocationListener);
        //Start the location.
        mLocationClient.start();

        Timer findLocation = new Timer();
        TimerTask findLocationTask = new TimerTask() {
            @Override
            public void run() {
                points.add(new LatLng(myLocationListener.getCurrentLatitude(), myLocationListener.getCurrentLongitude()));
                backToCenter();
                if(points.size()>=3 && isRunning) {//Enough points for draw lines
                    drawLines();
                }
            }
        };
        findLocation.schedule(findLocationTask,0,500);//Find the location per 0.5s
    }

    public void backToCenter(){//invoke the function to let the window back
        LatLng latLng=new LatLng(myLocationListener.getCurrentLatitude(), myLocationListener.getCurrentLongitude());
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.target(latLng);//put the camera to the center
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
    }


    public void drawLines(){
//Set properties of the line
        OverlayOptions mOverlayOptions = new PolylineOptions()
                .width(6)
                .color(0xAAFF0000)
                .points(points);
//draw the line on the map
//create the object of line
        Overlay polyline = mBaiduMap.addOverlay(mOverlayOptions);
    }

    public void saveRunning(Running running){
        this.runningStorage.recordRunning(running);//save the record into a storage.
    }

    public void calculateRunning(){
        for (int i = 0; i < points.size()-1; i++) {//calculate the distance
            miles += DistanceUtil.getDistance(points.get(i), points.get(i+1));
        }
        miles = miles/1000;//cast to KM
        energy = miles*70;
        speed = miles*3600;
    }

    public SQLiteDatabase dbInit(){
        MainSQLHelper mainSQLHelper = new MainSQLHelper(SingleRunnerActivity.this,"SpinNew.db",null,1);
        return mainSQLHelper.getWritableDatabase();
    }

    public void initRunning(){
        miles = 0.0;//cast to KM
        energy = 0.0;
        speed = 0.0;
        second = 0;
    }

    public void startTime(){//Double timer, to sync the time.
        timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                second++;
            }
        };
        timer.schedule(timerTask,0,1000);
    }

    public void stopTime(){
        if(timer!=null){
            timer.cancel();
            timer = null;
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(sensorEvent.sensor.getType()==Sensor.TYPE_AMBIENT_TEMPERATURE){
            temperature = sensorEvent.values[0];
        }else if(sensorEvent.sensor.getType()==Sensor.TYPE_PRESSURE){
            pressure = sensorEvent.values[0];
        }else if(sensorEvent.sensor.getType()==Sensor.TYPE_RELATIVE_HUMIDITY){
            humidity = sensorEvent.values[0];
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
