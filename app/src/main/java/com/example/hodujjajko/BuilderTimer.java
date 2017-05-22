package com.example.hodujjajko;

import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class BuilderTimer implements IBuilderTimer {

    private List<Timer> chainOfTimers;
    private String[] durations;
    private Factory factory;
    private TextView textView;

    public BuilderTimer(String text, TextView textView){
        Log.i("BuilderTimer", "w buildertimer text "+ text);
        durations = text.split("\\+");
        factory = new Factory();
        chainOfTimers = new ArrayList<>();
        this.textView = textView;
    }
    @Override
    public void buildChainOfTimers() {
        for(int i = 0; i < durations.length; i++){
            chainOfTimers.add(factory.create(Float.parseFloat(durations[i]), textView));
        }
    }

    @Override
    public List<Timer> getTimers() {
        return chainOfTimers;
    }
}
