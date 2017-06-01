package com.example.hodujjajko;


import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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
    private String originalTime;
    TimersBuildingClass buildingClass;
    int sumOfPoints = 0;
    double result=0;
    TrainingDao training;
    String startTime;
    String stopTime;
    int NOTIFICATION_ID;
    NotificationManager notificationManager;
    NotificationCompat.Builder builder;
    String notificationMessage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timer);
        if (savedInstanceState != null) {
            timeLast = savedInstanceState.getString("timeLast");
            originalTime = savedInstanceState.getString("originalTime");
        }else{
            timeLast = getIntent().getExtras().getString("text");
            originalTime = timeLast;
        }
        init();

    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("timeLast", buildingClass.returnStringTimers());
        outState.putString("originalTime", originalTime);
    }

    private void init(){
        notificationMessage = "";
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DATE);
        if(minute<10)
            startTime = String.valueOf(day)+ "-" + String.valueOf(month + 1) + "-" +
                    String.valueOf(year) + " " + String.valueOf(hour) + ":0" + String.valueOf(minute);
        else
            startTime = String.valueOf(day)+ "-" + String.valueOf(month + 1) + "-" +
                    String.valueOf(year) + " " + String.valueOf(hour) + ":" + String.valueOf(minute);
        training = new TrainingDao(getApplicationContext());
        textViewTimer = (TextView)findViewById(R.id.textViewTime);
        textViews = new ArrayList<>(QUEUE_IDS.length);
        for(int id : QUEUE_IDS) {
            TextView textView = (TextView) findViewById(id);
            textViews.add(textView);
        }

        buildingClass = new TimersBuildingClass();
        observer = new TimerObserver(buildingClass);
        buildingClass.build(""+timeLast);
        buildingClass.set();
        buildingClass.start();
    }


    public class TimersBuildingClass {
        List<com.example.hodujjajko.Timer> timers;
        Iterator iterator;
        BuilderTimer builderTimer;

        void build(String text){
            builderTimer = new BuilderTimer(text, textViewTimer);
            builderTimer.buildChainOfTimers();
            timers = builderTimer.getTimers();
            iterator = new Iterator(timers);
            timers.get(0).setObserver(observer);
        }
        void set(){
            int index = 0;
            iterator.reset();
            iterator.next();
            while (iterator.hasNext() && index < 3) {
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
            timers.remove(0);
            if (!timers.isEmpty()) {
                playSound();
                set();
                timers.get(0).setObserver(observer);
                start();
            } else {
                playSound();
                if (notificationManager != null){
                    notificationMessage = getString(R.string.message_end_timer);
                    builder = new NotificationCompat.Builder(getApplicationContext())
                            .setSmallIcon(R.drawable.kurczak1)
                            .setContentTitle(notificationMessage);
                    notificationManager.notify(NOTIFICATION_ID, builder.build());
                }
                String points[] = originalTime.split("\\+");
                for (String v : points) {
                    result += Double.parseDouble(v);
                }
                sumOfPoints = (int)(10*result);
                textViewTimer.setText(getString(R.string.score_string) + sumOfPoints +
                        getString(R.string.score_points_string));
                final Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DATE);
                stopTime = String.valueOf(day) + "-" + String.valueOf(month + 1) + "-" +
                        String.valueOf(year) + " " + String.valueOf(hour) + ":" + String.valueOf(minute);
                points();
                addToDatabase();
            }
        }
        public String getTime(){
            if(timers != null && !timers.isEmpty()) {
                return timers.get(0).getTime();
            }else return "";
        }

        String returnStringTimers(){
            return builderTimer.returnChain();
        }

        void cancel(){
            if(!timers.isEmpty()) {
                timers.get(0).cancel();
                timers = null;
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

    public void points()
    {
        LinearLayout lL = (LinearLayout) findViewById(R.id.layoutDown);
        ImageView image= new ImageView(this);
        if(sumOfPoints > 20) {
            image.setImageDrawable(getResources().getDrawable(R.drawable.kurczak));
            lL.addView(image);
        }else if(sumOfPoints >50){
            image.setImageDrawable(getResources().getDrawable(R.drawable.kurczak1));
            lL.addView(image);

        }else if(sumOfPoints > 100)
        {
            image.setImageDrawable(getResources().getDrawable(R.drawable.kura));
            lL.addView(image);

        }else
        {
            image.setImageDrawable(getResources().getDrawable(R.drawable.kurczak2));
            lL.addView(image);
        }

    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        buildingClass.cancel();
        if(notificationManager != null) {
            notificationManager.cancel(NOTIFICATION_ID);
        }
    }

    public void addToDatabase()
    {
        training.open();
        Training t = new Training();
        t.points = sumOfPoints;
        t.start = startTime;
        t.finish= stopTime;
        t.duration=String.valueOf(result);
        t.typeOfTraining = "Stoper";
        training.addTraining(t);
        training.close();

    }
    @Override
    protected void onStop() {
        super.onStop();
        if(!notificationMessage.equals(getString(R.string.message_end_timer))) {
            builder =
                    new NotificationCompat.Builder(this)
                            .setSmallIcon(R.drawable.kurczak1)
                            .setContentTitle(getString(R.string.message_timer));

            notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(NOTIFICATION_ID, builder.build());
        }
    }


    @Override
    protected void onResume(){
        super.onResume();
        if(notificationManager != null) {
            notificationManager.cancel(NOTIFICATION_ID);

        }
    }

}
