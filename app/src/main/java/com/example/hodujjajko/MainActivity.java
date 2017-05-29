package com.example.hodujjajko;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button rearEgg;
    private Button schedule;
    private ImageView image;
    private Button gallery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init(){
        rearEgg = (Button)findViewById(R.id.RearEgg);
        rearEgg.setOnClickListener(this);
        image = (ImageView) findViewById(R.id.imageView);
        schedule = (Button)findViewById(R.id.Schedule);
        schedule.setOnClickListener(this);
        gallery = (Button)findViewById(R.id.Gallery);
        gallery.setOnClickListener(this);

    }

    @Override
    public void onClick(View view){
        switch(view.getId()) {
            case R.id.RearEgg:
                startSettingActivity();
                break;
            case R.id.Schedule:
                startSchedule();
                break;
        }
    }

    private void startSettingActivity(){
        Intent settingActivityIntent = null;
        settingActivityIntent = new Intent(getApplicationContext(), SettingActivity.class);
        startActivity(settingActivityIntent);
    }

    private void startSchedule(){
        Intent scheduleIntent = null;
        scheduleIntent = new Intent(getApplicationContext(), Schedule.class);
        startActivity(scheduleIntent);
    }

}
