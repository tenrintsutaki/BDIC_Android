package com.example.myapplication;

import android.app.Application;
import android.graphics.Color;
import com.baidu.location.LocationClient;
import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.example.myapplication.Match.Client;
import com.example.myapplication.Model.User;

/**
 * This is the state for saving current instances
 * such as the user, or current color style in the app
 * @author Aliosha Louyiming
 */
public class CurrentUser extends Application {
    private User currentUser;
    private String[] colors;
    private int currentPage;
    private Client currentClient;
    private int backGroundResource;

    @Override
    public void onCreate() {
        super.onCreate();

        SDKInitializer.setAgreePrivacy(getApplicationContext(),true);
        SDKInitializer.initialize(getApplicationContext());
        SDKInitializer.setCoordType(CoordType.BD09LL);
        LocationClient.setAgreePrivacy(true);
        //init the map api or relevant works

        backGroundResource = R.drawable.blue_background;

        currentPage = 1;

        currentUser = new User(0,"Guest",0);
        colors = new String[]{"#FF9800","#FF5722","#64FF9800"};
//        colors = new String[]{"#FF6200EE","#FF3700B3","#FFBB86FC"};
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public String[] getColors() {
        return colors;
    }

    public String getMainColor() {
        return colors[0];
    }

    public String getStrongColor() {
        return colors[1];
    }

    public String getFadeColor() {
        return colors[2];
    }

    public void setColors(String[] colors) {
        this.colors = colors;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public void setBackGroundResource(int resource){
        this.backGroundResource = resource;
    }

    public int getBackGroundResource() {
        return backGroundResource;
    }

}
