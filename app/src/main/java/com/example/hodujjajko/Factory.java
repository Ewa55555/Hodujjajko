package com.example.hodujjajko;


import android.widget.TextView;

public class Factory {
    public Timer create(long duration, TextView textView){
        Timer timer = createTimer(duration, textView);
        return timer;
    }
    public Timer createTimer(long duration, TextView textView){
        return new Timer(duration * 60 * 1000, 1000, textView);
    }
}
