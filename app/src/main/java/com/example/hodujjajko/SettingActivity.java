package com.example.hodujjajko;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class SettingActivity extends Activity implements View.OnClickListener{

    private Button study;
    private Button running;
    private Button pedometr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_activity);
        init();
    }

    private void init(){
        study = (Button)findViewById(R.id.study);
        study.setOnClickListener(this);
        running = (Button)findViewById(R.id.running);
        running.setOnClickListener(this);
        pedometr = (Button)findViewById(R.id.pedometr);
        pedometr.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.study:
                Log.i("Jajko", "startujemy setting timer");
                startSettingTimer();
                break;
            case R.id.pedometr:
                startPedometr();
                break;
            case R.id.running:
                startRunning();
                break;
        }
    }

    private void startSettingTimer(){
        Intent settingTimerIntent = null;
        settingTimerIntent = new Intent(getApplicationContext(), SettingTimer.class);
        startActivity(settingTimerIntent);
    }

    private void startPedometr(){
        Intent pedometrIntent = null;
        pedometrIntent = new Intent(getApplicationContext(), Pedometr.class);
        startActivity(pedometrIntent);
    }
    private void startRunning(){
        Intent runningIntent = null;
        runningIntent = new Intent(getApplicationContext(), GPSActivity.class);
        Log.i("settingactivity", "kliklam startRunning");
        startActivity(runningIntent);
    }
}
