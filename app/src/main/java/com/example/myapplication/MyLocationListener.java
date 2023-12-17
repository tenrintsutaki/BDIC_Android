package com.example.myapplication;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.*;


public class MyLocationListener extends BDAbstractLocationListener {
    //Referenced on the Baidu map api
    // Which I learnt from the development document.
    //Referenced on https://lbsyun.baidu.com/index.php?title=androidsdk


    private BaiduMap currentMap;
    private double currentLatitude;
    private double currentLongitude;

    public MyLocationListener(BaiduMap currentMap){
        this.currentMap = currentMap;
    }

    public double getCurrentLatitude() {
        return currentLatitude;
    }
    //get the location information

    public double getCurrentLongitude() {
        return currentLongitude;
    }
    //get the location information

    @Override
    public void onReceiveLocation(BDLocation location) {
        if (location == null){//When the location is not needed, this method will be invoked
            return;
        }
        MyLocationData locData = new MyLocationData.Builder().accuracy(location.getRadius()).direction(location.getDirection()).latitude(location.getLatitude()).longitude(location.getLongitude()).build();
        //Get some useful information
        currentLatitude = location.getLatitude();
        currentLongitude = location.getLongitude();
        //Transfer the current location to the map.
        currentMap.setMyLocationData(locData);
    }

}
