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
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
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
    private String timeLast;
    TimersBuildingClass buildingClass;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timer);
        if (savedInstanceState != null) {
            timeLast = savedInstanceState.getString("timeLast");
            Log.i("RunTimer", "savedinstance nie jest null i timelast = "+timeLast);
        }else{
            timeLast = getIntent().getExtras().getString("text");
        }
        init();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // Make sure to call the super method so that the states of our views are saved
        super.onSaveInstanceState(outState);
        // Save our own state now
        outState.putString("timeLast", buildingClass.returnStringTimers());
    }

    private void init(){
        Log.i("RunTimer", "jestesmy w inice");
        textViewTimer = (TextView)findViewById(R.id.textViewTime);
        textViews = new ArrayList<>(QUEUE_IDS.length);
        for(int id : QUEUE_IDS) {
            TextView textView = (TextView) findViewById(id);
            textViews.add(textView);
        }

        buildingClass = new TimersBuildingClass();
        observer = new TimerObserver(buildingClass);
        Log.i("RunTimer", "w inicie text "+timeLast);
        buildingClass.build(""+timeLast);
        buildingClass.set();
        buildingClass.start();
        Log.i("RunTimer", "zastartowalo wszytsko");
    }


    public class TimersBuildingClass {
        List<Timer> timers;
        Iterator iterator;
        BuilderTimer builderTimer;

        void build(String text){
            builderTimer = new BuilderTimer(text, textViewTimer);
            builderTimer.buildChainOfTimers();
            Log.i("RunTimer", "pobieram timersy");
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
            Log.i("RunTimer", "startujemy z timerem");
        }
        void update(){
            Log.i("RunTimer", "update w runtime");

            timers.remove(0);
            if (!timers.isEmpty()) {
                playSound();
                set();
                timers.get(0).setObserver(observer);
                start();
            } else {
                playSound();
//                textViewTimer.setText("End");
                points();
            }

        }
        String returnStringTimers(){
            return builderTimer.returnChain();
        }
        void cancel(){
            timers.get(0).cancel();
            timers = null;
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

    public void points()
    {
        LinearLayout lL = (LinearLayout) findViewById(R.id.linearLayout);
        TextView viewPoints = (TextView) findViewById(R.id.textView);
        ImageView image= new ImageView(this);
        image.setImageDrawable(getResources().getDrawable(R.drawable.kurczak));
        lL.addView(image);
        super.onResume() ;
        setContentView(R.layout.points);
//        viewPoints.setText("Zdobyłeś 200 punktów");

    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        buildingClass.cancel();
        Log.i("RunTimer", "usuwam timersy");
    }
}
