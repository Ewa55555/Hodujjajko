package com.example.hodujjajko;


import android.app.Activity;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class RunTimer extends Activity implements View.OnClickListener{

    private TextView textViewTimer;
    private TextView queue1;
    private TextView queue2;
    private TextView queue3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timer);
        init();
    }

    private void init(){
        Log.i("RunTimer", "jestesmy w inice");
        textViewTimer = (TextView)findViewById(R.id.textViewTime);
        queue1 = (TextView)findViewById(R.id.queue1);
        queue2 = (TextView)findViewById(R.id.queue2);
        queue3 = (TextView)findViewById(R.id.queue3);

        TimersBuildingClass buildingClass = new TimersBuildingClass();
        buildingClass.build("1+1+1+1");
        buildingClass.set();
        buildingClass.start();
        Log.i("RunTimer", "zastartowalo wszytsko");
    }

    public class TimersBuildingClass {
        List<Timer> timers;

        void build(String text){
            BuilderTimer builderTimer = new BuilderTimer(text, textViewTimer);
            builderTimer.buildChainOfTimers();
            timers = builderTimer.getTimers();
        }
        void set(){
            queue1.setText(timers.get(1).convertTime());
            queue2.setText(timers.get(2).convertTime());
            queue3.setText(timers.get(3).convertTime());
            timers.get(0).start();
        }
        void start(){
            timers.get(0).start();
        }
    }

    @Override
    public void onClick(View v) {

    }


}
