package com.example.hodujjajko;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class RunTimer extends Activity implements View.OnClickListener{

    private TextView textViewTimer;
    private Observer observer;
    private List<TextView> textViews;
    private static final int[] QUEUE_IDS = {
            R.id.queue1,
            R.id.queue2,
            R.id.queue3
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timer);
        init();
    }

    private void init(){
        Log.i("RunTimer", "jestesmy w inice");
        textViewTimer = (TextView)findViewById(R.id.textViewTime);
        textViews = new ArrayList<>(QUEUE_IDS.length);
        for(int id : QUEUE_IDS) {
            TextView textView = (TextView) findViewById(id);
            textViews.add(textView);
        }

        TimersBuildingClass buildingClass = new TimersBuildingClass();
        observer = new TimerObserver(buildingClass);
        Log.i("RunTimer", "w inicie text "+getIntent().getStringExtra("text"));
        buildingClass.build(""+getIntent().getExtras().getString("text"));
        buildingClass.set();
        buildingClass.start();
        Log.i("RunTimer", "zastartowalo wszytsko");
    }


    public class TimersBuildingClass {
        List<Timer> timers;
        Iterator iterator;

        void build(String text){
            BuilderTimer builderTimer = new BuilderTimer(text, textViewTimer);
            builderTimer.buildChainOfTimers();
            timers = builderTimer.getTimers();
            iterator = new Iterator(timers);
            timers.get(0).setObserver(observer);
        }
        void set(){
            int index = 0;
            iterator.reset();
            iterator.next();
            Log.i("RunTimer", "jestem w secie");
            while (iterator.hasNext() && index < 3) {
                Log.i("RunTimer", "index w while " + index);
                textViews.get(index).setText(iterator.next().convertTime());
                index++;
            }
            while(index < 3){
                textViews.get(index).setText("");
                index++;
            }

        }
        void start(){
            timers.get(0).start();
        }
        void update(){
            Log.i("RunTimer", "update w runtime");
            timers.remove(0);
            if(!timers.isEmpty()) {
                playSound();
                set();
                timers.get(0).setObserver(observer);
                start();
            }else{
                playSound();
                textViewTimer.setText("End");
            }

        }

    }
    private void playSound() {
        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
        r.play();

    }
    @Override
    public void onClick(View v) {

    }


}
