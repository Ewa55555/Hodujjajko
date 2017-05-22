package com.example.hodujjajko;


import android.util.Log;
import android.widget.TextView;

public class Factory {
    public Timer create(float duration, TextView textView){
        Timer timer = createTimer(duration, textView);
        return timer;
    }
    public Timer createTimer(float duration, TextView textView){
        Log.i("Factory", "duration" +duration);
        return new Timer((long)(duration * 60 * 1000), 1000, textView);
    }
}
