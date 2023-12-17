package com.example.myapplication;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.location.Location;
import android.media.Image;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.*;
import android.view.*;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.AnimatorRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.baidu.mapapi.map.*;
import com.baidu.mapapi.model.LatLng;
import com.example.myapplication.Match.*;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.example.myapplication.Model.User;
import com.example.myapplication.ui.MatchDialog;
import com.example.myapplication.ui.RunDialog;
import com.tbruyelle.rxpermissions2.RxPermissions;


import java.util.Timer;
import java.util.TimerTask;

public class Circular_Button_Test extends AppCompatActivity {
    Animation animation;
    Button button;
    TextView ring;
    Boolean isAnimating = false;
    private int sec;
    private Timer timer;
    private TimerTask timerTask;
    private TextureMapView mapView;
    private  BaiduMap mBaiduMap;
    private LocationClient mLocationClient;
    private MyLocationListener myLocationListener;

    private Button leftNaviBtn;
    private Button midNaviBtn;
    private Button rightNaviBtn;

    private TextView fadeBar;

    private User currentUser;
    private CurrentUser currentApp;

    private Client client;

    private String[] permissions = new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS};
    private static final int OPEN_SET_REQUEST_CODE = 100;

    private MediaPlayer mediaPlayer;


    /**
     * Init the map instance
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        client = new Client(new double[]{39.7590,116.3280},"Tenrin");
//        client = new Client(new double[]{39.7684,116.3265},"TenrinTsutaki");
        //Set a client for test

        initPermissions();

        checkVersion();

        currentApp = (CurrentUser) getApplication();

        ActionBar actionBar = getSupportActionBar();

        Intent intentLogin = getIntent();
        Bundle bundle = intentLogin.getExtras();

        if(actionBar != null){//Ignore the ugly actionBar
            actionBar.setTitle("Running Partner");
//            actionBar.hide();
        }

        setContentView(R.layout.cicular_button_test);

        matchMessageBtnInit();

        fadeBar = findViewById(R.id.textView4);
        fadeBar.setBackgroundColor(Color.parseColor(currentApp.getFadeColor()));

        button = findViewById(R.id.button4);

        ring = findViewById(R.id.ring);

        mapView = findViewById(R.id.map);

        mBaiduMap = mapView.getMap();
        mBaiduMap.setMyLocationEnabled(true);

        try {
            mLocationClient = new LocationClient(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

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
        //Creat a new client with current location.

        leftNaviBtn = findViewById(R.id.button10);
        leftNaviBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Circular_Button_Test.this,PersonalActivity.class);
                startActivity(intent);
            }
        });


        rightNaviBtn = findViewById(R.id.button9);
        rightNaviBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(Circular_Button_Test.this,DataActivity.class);
            startActivity(intent);
        }
        });

        midNaviBtn = findViewById(R.id.button8);
        midNaviBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Circular_Button_Test.this,MainPageActivity.class);
                startActivity(intent);
            }
        });
    }


    @SuppressLint("SetTextI18n")
    public void startScale(View view){
        client = new Client(new double[]{myLocationListener.getCurrentLatitude(),myLocationListener.getCurrentLongitude()},"TestUser");
        client.setMain_activity(this);
        Toast.makeText(this.getApplicationContext(),""+myLocationListener.getCurrentLatitude()+", "+myLocationListener.getCurrentLongitude(),Toast.LENGTH_SHORT).show();
        if(isAnimating){
           ring.clearAnimation();//Stop the animation
            button.setText("MATCH");
           isAnimating = false;
           sec = 0;
           stopTime();
        }else{
            animation  = AnimationUtils.loadAnimation(Circular_Button_Test.this,R.anim.button_scale);
            backToCenter();
            animation.setRepeatCount(10000);//set replay times
            ring.startAnimation(animation);//Start!
            button.setText("MATCHING......");
            button.setVisibility(View.VISIBLE);
            startTime();//Start the time recording

            mediaPlayer = MediaPlayer.create(this,R.raw.di);
            mediaPlayer.start();//play the audio of the match

            Thread netWork = new Thread(new Runnable() {
                @Override
                public void run() {
                    client.startMatch();//Start the matching thread
                }
            });

            netWork.start();
            isAnimating = true;
        }

    }

    public void backToCenter(){//invoke the function to let the window back
        LatLng latLng=new LatLng(myLocationListener.getCurrentLatitude(), myLocationListener.getCurrentLongitude());
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.target(latLng).zoom(16.2f);
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        public void handleMessage(android.os.Message msg){
            button.setText("Time : "+msg.arg1+" S");
            Toast.makeText(getApplicationContext(),client.getMessage(),Toast.LENGTH_SHORT).show();
            //Set the toast.

            String[] location = client.getLocation();
            //Get your match partner's location
            LatLng point = new LatLng(Double.parseDouble(location[0]), Double.parseDouble(location[1]));
            //Marker ICON
            BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.icon_gcoding);
            //Add the marker on the map
            OverlayOptions optionPartner = new MarkerOptions().position(point).icon(bitmap);
            //Put the marker on the map
            mBaiduMap.addOverlay(optionPartner);
            startTime();
        };
    };

    private void startTime(){//Start!
        if(timer == null){
            timer = new Timer();
        }
        timerTask = new TimerTask() {
            @Override
            public void run() {

                Message message = Message.obtain();
                message.arg1 = sec;
                handler.sendMessage(message);
                sec++;
                //Create a new thread inorder to not avoid the main thread.
            }
        };
        timer.schedule(timerTask,1000);
    }

    private void stopTime(){//Stop the recording time
        if(timer!=null){
            timer.cancel();
            timer = null;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {//Create the menu
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_item:
                Intent intent  = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://space.bilibili.com/40178341"));
                startActivity(intent);
                break;
            case R.id.remove_item:
                intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:110"));
                startActivity(intent);
                break;
            case R.id.select_1:
                intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:110"));
                startActivity(intent);
                break;
            default:
        }
        return true;
    }

    public boolean lacksPermission(String[] permissions) {//use for check the permission, referenced on the CSDN
        for (String permission : permissions) {
            //Check if there are lack of permission?
            if(ContextCompat.checkSelfPermission(getApplicationContext(), permission) != PackageManager.PERMISSION_GRANTED){
                return true;
            }
        }
        return false;
    }

    private void initPermissions() {//use for check the permission, referenced on the CSDN
        if (lacksPermission(permissions)) {//use the function to check
            //Request the permissions which will be needed
            ActivityCompat.requestPermissions(this, permissions, OPEN_SET_REQUEST_CODE);
        } else {

        }
    }

    @Override
    //use for check the permission, referenced on the CSDN
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){//Check the request result
            case OPEN_SET_REQUEST_CODE:
                if (grantResults.length > 0) {
                    for(int i = 0; i < grantResults.length; i++){
                        if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
                            Toast.makeText(getApplicationContext(),"No Permission",Toast.LENGTH_LONG).show();
                            return;
                        }
                    }
                } else {
                    Toast.makeText(getApplicationContext(),"No Permission",Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    private void checkVersion() {//use for check the permission, referenced on the CSDN
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
            RxPermissions rxPermissions = new RxPermissions(this);
            rxPermissions.request(Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.READ_PHONE_STATE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(granted -> {
                        if (granted) {//Got the permission successfully
                        } else {//Cannot get the permission
                            Toast.makeText(Circular_Button_Test.this,"No Permission!",Toast.LENGTH_SHORT).show();
                        }
                    });
        }else {

        }
    }

    private void matchMessageBtnInit(){
        ImageButton messageDetail = findViewById(R.id.messageDetail);
        messageDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MatchDialog matchDialog = new MatchDialog(Circular_Button_Test.this);
                if(client != null) {
                    client.setUI(matchDialog);
                }
                matchDialog.setListener(new MatchDialog.dialogClickListener() {
                    @Override
                    public void onClick(View view) {
                        switch (view.getId()){
                            case R.id.sendBtn://send to your partner
                                if(client.getState()!=null) {
                                    Thread thread = new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            client.getSendThread().sendPrivateMessage(matchDialog.getMessage());
                                        }
                                    });
                                    thread.start();
                                }
                                matchDialog.sendMessage();
                                break;
                        }
                    }
                });
                Window dialogWindow = matchDialog.getWindow();
                dialogWindow.getDecorView().setPadding(0, 0, 0, 0);
                dialogWindow.setGravity(Gravity.TOP);
                WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // get parameters
                lp.alpha = 9f; // Transparency
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.horizontalMargin = 0;
                dialogWindow.setAttributes(lp);
                matchDialog.show();
            }
        });
    }

    public void appendMessage(MatchDialog matchDialog,String str){
        matchDialog.appendMessage(str);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(client!=null){
            client.stopClient();
        }
    }
}
