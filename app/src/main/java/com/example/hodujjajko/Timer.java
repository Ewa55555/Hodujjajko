package com.example.hodujjajko;


import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;
public class Timer extends Activity implements View.OnClickListener{

    private TextView textViewTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timer);
        init();
    }

    private void init(){
        textViewTimer = (TextView)findViewById(R.id.textViewTime);
        textViewTimer.setOnClickListener(this);
        textViewTimer.setText("00:03:00");

        final CounterClass timer = new CounterClass(3*60*1000,1000);
        timer.start();
    }

    @Override
    public void onClick(View v) {

    }

    public class CounterClass extends CountDownTimer{
        public CounterClass(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            long millis = millisUntilFinished;
            String s = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
                    TimeUnit.MILLISECONDS.toMinutes(millis)
                            - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                    TimeUnit.MILLISECONDS.toSeconds(millis) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))
            );
            textViewTimer.setText(s);
        }

        @Override
        public void onFinish() {
            textViewTimer.setText("End");
        }
    }
}
