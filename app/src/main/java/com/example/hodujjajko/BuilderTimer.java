package com.example.hodujjajko;

import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class BuilderTimer implements IBuilderTimer {

    private List<Timer> chainOfTimers;
    private String[] durations;
    private Factory factory;
    private TextView textView;

    public BuilderTimer(String text, TextView textView){
        durations = text.split("\\+");
        factory = new Factory();
        chainOfTimers = new ArrayList<>();
        this.textView = textView;
    }
    @Override
    public void buildChainOfTimers() {
        for(int i = 0; i < durations.length; i++){
            chainOfTimers.add(factory.create(Long.valueOf(durations[i]).longValue(), textView));
        }
    }

    @Override
    public List<Timer> getTimers() {
        return chainOfTimers;
    }
}
