package com.example.hodujjajko;

import android.util.Log;

public class TimerObserver implements Observer{

    RunTimer.TimersBuildingClass timersBuildingClass;

    public TimerObserver(RunTimer.TimersBuildingClass timersBuildingClass){
        this.timersBuildingClass = timersBuildingClass;
    }
    @Override
    public void update() {
        timersBuildingClass.update();
    }
}
