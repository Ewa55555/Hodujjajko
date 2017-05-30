package com.example.hodujjajko;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
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

public class Pedometr extends Activity implements SensorEventListener{

    private SensorManager sensorManager;
    private TextView count;
    private Button stop;
    boolean activityRunning;
    private String counting;
    private int sumOfPoints = 0;
    private TextView info ;
    TrainingDao training;
    Time startTime= new Time();
    Time stopTime = new Time();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pedometr);
        count = (TextView) findViewById(R.id.conterTextView);
        startTime.setToNow();
        training = new TrainingDao(getApplicationContext());
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
                stopTime.setToNow();
                sumOfPoints = (int)Double.parseDouble(count.getText().toString());
                onPause();
                points();
                addToDatabase();
            }
        });
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
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
        t.duration = Integer.parseInt(count.getText().toString());
        t.start = startTime.toString();
        t.finish= stopTime.toString();
        t.typeOfTraining="Krokomierz";
        training.addTraining(t);
        training.close();

    }
}
