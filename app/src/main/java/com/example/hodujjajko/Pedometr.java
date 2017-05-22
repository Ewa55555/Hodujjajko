package com.example.hodujjajko;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Kasia on 2017-05-21.
 */

public class Pedometr extends Activity implements SensorEventListener {
    private TextView counter;
    private SensorManager sensorManager;
    private Sensor stepCounter;
    private Sensor stepDetector;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pedometr);
        counter = (TextView) findViewById(R.id.conterTextView);

        sensorManager = (SensorManager)
                getSystemService(Context.SENSOR_SERVICE);
        stepCounter = sensorManager
                .getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        stepDetector = sensorManager
                .getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
    }
    @Override
    protected void onResume() {

        super.onResume();
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
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor sensor = event.sensor;
        float[] values = event.values;
        int value = -1;

        if (values.length > 0) {
            value = (int) values[0];
        }

        if (sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
            counter.setText("Step Counter Detected : " + value);
        } else if (sensor.getType() == Sensor.TYPE_STEP_DETECTOR) {
            // For test only. Only allowed value is 1.0 i.e. for step taken
            counter.setText("Step Detector Detected : " + value);
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
