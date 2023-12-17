package com.example.myapplication.SensorSteps;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.Log;

/**
 * This is a class for counting steps of the user
 * @author Aliosha Lou
 */
public class RunningListener implements SensorEventListener {
    private int stepDetector = 0;
    private int stepCounter = 0;
    private float temperature = 0.0f;
    private float pressure = 0.0f;
    private float humidity = 0.0f;

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE) {
            temperature = event.values[0];
        } else if (event.sensor.getType() == Sensor.TYPE_PRESSURE) {
            pressure = event.values[0];
        } else if (event.sensor.getType() == Sensor.TYPE_RELATIVE_HUMIDITY){
            humidity = event.values[0];
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        Log.d("onAccuracyChanged", String.valueOf(i));
    }

    public int getStepDetector() {
        return stepDetector;
    }

    public int getStepCounter() {
        return stepCounter;
    }

    public float getTemperature() {
        return temperature;
    }

    public float getPressure() {
        return pressure;
    }

    public float getHumidity() {
        return humidity;
    }
}
