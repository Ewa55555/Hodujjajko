package com.example.hodujjajko;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class Pedometr extends Activity implements SensorEventListener {
    private TextView counter;
    private SensorManager sensorManager;
    private Sensor stepCounter;
    private Sensor stepDetector;
    boolean activityRunning;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pedometr);
        counter = (TextView) findViewById(R.id.conterTextView);
        Log.i("Pedometr","jestem w create");
        PackageManager pm = getPackageManager();
        if (pm.hasSystemFeature(PackageManager.FEATURE_SENSOR_STEP_COUNTER)) {
            Log.i("Pedometr","czujnik jest widoczny");
        }else{
            Log.i("Pedometr","Brak czujnika");
        }

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        stepCounter = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        stepDetector = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
    }
    @Override
    protected void onResume() {

        super.onResume();
        activityRunning = true;
        if (stepCounter != null) {
            sensorManager.registerListener(this,stepCounter, SensorManager.SENSOR_DELAY_FASTEST);
            sensorManager.registerListener(this,stepDetector, SensorManager.SENSOR_DELAY_FASTEST);
        } else {
            Toast.makeText(this, "Count sensor not available!", Toast.LENGTH_LONG).show();
        }

    }
    @Override
    protected void onPause() {
        super.onPause();
        activityRunning = false;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (activityRunning) {
            counter.setText(String.valueOf(event.values[0]));
        }
    }
    protected void onStop() {
        super.onStop();
        sensorManager.unregisterListener(this, stepCounter);
        sensorManager.unregisterListener(this, stepDetector);
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
