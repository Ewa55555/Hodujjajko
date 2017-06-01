package com.example.hodujjajko;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button rearEgg;
    private Button schedule;
    private ImageView image;
    private Button activities;


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
        activities = (Button) findViewById(R.id.activities);
        activities.setOnClickListener(this);

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
            case R.id.activities:
                startYourActivities();
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
    private void startYourActivities(){
        Intent activitiesIntenet = null;
        activitiesIntenet = new Intent(getApplicationContext(), Activities.class);
        startActivity(activitiesIntenet);
    }


}
