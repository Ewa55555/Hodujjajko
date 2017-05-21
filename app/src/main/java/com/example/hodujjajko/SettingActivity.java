package com.example.hodujjajko;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class SettingActivity extends Activity implements View.OnClickListener{

    private Button study;
    private Button training;
    private Button running;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_activity);
        init();
    }

    private void init(){
        study = (Button)findViewById(R.id.study);
        study.setOnClickListener(this);
        training = (Button)findViewById(R.id.training);
        training.setOnClickListener(this);
        running = (Button)findViewById(R.id.running);
        running.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.study:
                Log.i("Jajko", "startujemy setting timer");
                startSettingTimer();
        }
    }

    private void startSettingTimer(){
        Intent settingTimerIntent = null;
        settingTimerIntent = new Intent(getApplicationContext(), SettingTimer.class);
        startActivity(settingTimerIntent);
    }
}