package com.example.hodujjajko;

import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.CountDownTimer;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class Timer extends CountDownTimer {
    private TextView textView;
    private long millis;
    private Observer observer;


    public Timer(long millisInFuture, long countDownInterval, TextView textView) {
        super(millisInFuture, countDownInterval);
        millis = millisInFuture;
        this.textView = textView;
    }

    public void setObserver(Observer observer){
        this.observer = observer;
    }

    public String convertTime(){
        String s = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis)
                        - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                TimeUnit.MILLISECONDS.toSeconds(millis) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))
        );
        return s;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        millis = millisUntilFinished;
        textView.setText(convertTime());
    }

    @Override
    public void onFinish() {
        observer.update();
    }


}

