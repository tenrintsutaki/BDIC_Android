package com.example.myapplication.ui;

import android.annotation.SuppressLint;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.example.myapplication.Database.RunningStorage;
import com.example.myapplication.Model.Running;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class RunDialog extends Dialog {
    private Context context;
    private dialogClickListener listener;
    private TextView distanceDisplay;
    private TextView energyDisplay;
    private TextView speedDisplay;
    private TextView timeDisplay;
    private TextView temp;
    private TextView humid;
    private TextView pressure_var;
    private RunningStorage runningStorage;
    private int second = 0;
    private Double sum = 0.0;
    private Double speed = 0.0;
    private Timer timer;

    private float temperature = 25.0f;
    private float pressure = 1.0f;
    private float humidity = 0.3f;

    public RunDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    public void setListener(dialogClickListener listener){
        this.listener = listener;
    }

    public interface dialogClickListener{//write an interface to listen the click from activity
        public void onClick(View view);
    }

    public void onClick(View v) {//the operation after the click.
        // TODO Auto-generated method stub
        listener.onClick(v);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.running_detail_dialog);
        this.getWindow().setWindowAnimations(R.style.AnimTop);
        ImageButton stopBtn = findViewById(R.id.stop_run);
        ImageButton startBtn = findViewById(R.id.start_run);

        distanceDisplay = findViewById(R.id.distance_num);
        energyDisplay = findViewById(R.id.energy_num);
        timeDisplay = findViewById(R.id.time_num);
        speedDisplay = findViewById(R.id.speed_num);

        temp = findViewById(R.id.temp_var);
        humid = findViewById(R.id.humidity_var);
        pressure_var = findViewById(R.id.pressure_var);



        startBtn.setOnClickListener(this::onClick);//bin this button inorder to invoke in the main activity

        startBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {//different icon for click or press
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    //Pressed Image
                    v.setBackgroundResource(R.drawable.circular_button_confirm_pressed);
                }else if(event.getAction() == MotionEvent.ACTION_UP){
                    //When the mouse up
                    v.setBackgroundResource(R.drawable.circular_button_confirm);
                }
                return false;
            }
        });

        stopBtn.setOnClickListener(this::onClick);//bin this button inorder to invoke in the main activity

        stopBtn.setOnTouchListener(new View.OnTouchListener(){//different icon for click or press
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    //Pressed Image
                    v.setBackgroundResource(R.drawable.circular_button_cancel_pressed);
                }else if(event.getAction() == MotionEvent.ACTION_UP){
                    //When the mouse up
                    v.setBackgroundResource(R.drawable.circular_button_cancel);
                }
                return false;
            }
        });
    }


    public void updateRunningData(List<LatLng> points){
        for (int i = 0; i < points.size()-1; i++) {//calculate the distance
            sum += DistanceUtil.getDistance(points.get(i), points.get(i+1));
        }
        sum = sum/1000;//cast to KM

        distanceDisplay.setText(sum.toString());

        Double calories = sum * 70;
        energyDisplay.setText(calories.toString());
    }

    public void initDialog(){
        distanceDisplay.setText(sum+"");
        energyDisplay.setText((sum*70)+"");
        timeDisplay.setText(second+"");
        speedDisplay.setText(speed+"");

        temp.setText(temperature+"℃");
        humid.setText(humidity+"");
        pressure_var.setText(pressure+"");
    }

    public void startTime(){
        timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Message message = Message.obtain();
                message.arg1 = second;
                handler.sendMessage(message);
                second++;
            }
        };
        timer.schedule(timerTask,0,1000);
    }

    public void setCurrentTime(int second){
        this.second = second;
    }

    public void stopTime(){
        if(timer!=null){
            timer.cancel();
            timer = null;
        }
    }

    public void setSensors(float temperature, float pressure, float humidity){
        this.temperature = temperature;
        this.pressure = pressure;
        this.humidity = humidity;
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        public void handleMessage(android.os.Message msg){
            timeDisplay.setText(second+"");
            speed = sum/msg.arg1;// Km per sec
            speed = speed * 3600;// Km per Hour
            speedDisplay.setText(Math.round(speed)+"");
        };
    };
}
