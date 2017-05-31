package com.example.hodujjajko;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Camera;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Pedometr extends Activity implements SensorEventListener{

    private SensorManager sensorManager;
    private TextView count;
    private Button stop;
    boolean activityRunning;
    private String counting;
    private int sumOfPoints = 0;
    private TextView info ;
    TrainingDao training;
    String startTime;
    String stopTime;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pedometr);
        count = (TextView) findViewById(R.id.conterTextView);
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DATE);
        startTime = String.valueOf(day)+ "-" + String.valueOf(month+1)+"-"+ String.valueOf(year)+" "+String.valueOf(hour)+":"+String.valueOf(minute);
        training = new TrainingDao(getApplicationContext());
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (savedInstanceState != null) {
            counting = savedInstanceState.getString("counting");
            count.setText(counting);
        } else {
            counting = "0";
        }

        PackageManager pm = getPackageManager();
        if (pm.hasSystemFeature(PackageManager.FEATURE_SENSOR_STEP_COUNTER)) {
            Log.i("Pedometr", "czujnik jest widoczny");
        } else {
            Log.i("Pedometr", "Brak czujnika");
        }


        stop = (Button) findViewById(R.id.stopButton);
        info = (TextView) findViewById(R.id.infoTextView);
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DATE);
                stopTime = String.valueOf(day)+ "-" + String.valueOf(month+1)+"-"+ String.valueOf(year)+" "+String.valueOf(hour)+":"+String.valueOf(minute);
                sumOfPoints = (int)Double.parseDouble(count.getText().toString());
                onPause();
                points();
                addToDatabase();
            }
        });

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("counting", count.getText().toString());
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
        Log.i("Pedometr","pauza");
        super.onPause();
        activityRunning = false;
        sensorManager.unregisterListener(this);

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
        info.setText("");
        count.setText("Zdobyto " + sumOfPoints + " punktów");
        stop.setVisibility(View.INVISIBLE);
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

    public void addToDatabase()
    {
        training.open();
        Training t = new Training();
        t.points = sumOfPoints;
        t.duration = String.valueOf(sumOfPoints);
        t.start = startTime;
        t.finish=stopTime;
        t.typeOfTraining="Krokomierz";
        training.addTraining(t);
        List<Training> e = training.fetchAllData();
        Log.i("Pedometr","dlugosc"+e.size());
        for(Training z :e)
        {
            Log.i("Pedometr","wyniki z bazy" + z.points + " " + z.typeOfTraining + " " + z.duration + " " + z.start +" ");
        }
        training.close();

    }
}
