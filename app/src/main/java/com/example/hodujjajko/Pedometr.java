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
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Pedometr extends Activity implements SensorEventListener {

    private SensorManager sensorManager;
    private TextView count;
    private Button stop;
    boolean activityRunning;
    private String counting;
    private int sumOfPoints = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pedometr);
        count = (TextView) findViewById(R.id.conterTextView);
        stop = (Button) findViewById(R.id.stopButton);
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPause();
                points();
            }
        });
        if (savedInstanceState != null) {
            counting = savedInstanceState.getString("counting");
        } else {
            counting = getIntent().getExtras().getString("text");
        }

        PackageManager pm = getPackageManager();
        if (pm.hasSystemFeature(PackageManager.FEATURE_SENSOR_STEP_COUNTER)) {
            Log.i("Pedometr", "czujnik jest widoczny");
        } else {
            Log.i("Pedometr", "Brak czujnika");
        }

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("counting", counting);
    }

    @Override
    protected void onResume() {
        super.onResume();
        activityRunning = true;
        Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if (countSensor != null) {
            sensorManager.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_UI);
        } else {
            Toast.makeText(this, "Sensor jest niedostępny!", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        activityRunning = false;
        sensorManager.unregisterListener(this);
        sumOfPoints = Integer.parseInt(counting);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (activityRunning) {
            count.setText(String.valueOf(event.values[0]));
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void points() {
        LinearLayout lL = (LinearLayout) findViewById(R.id.counter);
        ImageView image = new ImageView(this);
        if (sumOfPoints > 100) {
            image.setImageDrawable(getResources().getDrawable(R.drawable.kurczak));
            lL.addView(image);
        } else if (sumOfPoints > 400) {
            image.setImageDrawable(getResources().getDrawable(R.drawable.kurczak1));
            lL.addView(image);

        } else if (sumOfPoints > 700) {
            image.setImageDrawable(getResources().getDrawable(R.drawable.kura));
            lL.addView(image);

        } else {
            image.setImageDrawable(getResources().getDrawable(R.drawable.kurczak2));
            lL.addView(image);
        }
    }
}
