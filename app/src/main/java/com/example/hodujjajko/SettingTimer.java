package com.example.hodujjajko;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SettingTimer extends Activity implements View.OnClickListener{

    private EditText timer;
    private List<Button> buttons;
    private static final int[] BUTTON_IDS = {
        R.id.timer1,
        R.id.timer2,
        R.id.timer3,
        R.id.timer4,
        R.id.timer5,
        R.id.timer6,
        R.id.timer7,
        R.id.timer8};
    private Button startTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_timer);
        init();
    }

    private void init(){
        int i = 0;
        timer = (EditText) findViewById(R.id.timer);
        timer.setOnClickListener(this);
        startTimer = (Button)findViewById(R.id.start_timer);
        startTimer.setOnClickListener(this);
        buttons = new ArrayList<>(BUTTON_IDS.length);
        for(int id : BUTTON_IDS) {
            Button button = (Button) findViewById(id);
            button.setOnClickListener(this);
            Log.i("Jajko", "inicjalizujemy tagi " + i);
            button.setTag(i++);
            buttons.add(button);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.start_timer){
            Log.i("Jajko", "alo");
        }else{
            Log.i("Jajko", "znalazlam buttona");
            int index = (Integer)v.getTag();
            timer.setText(buttons.get(index).getText());
        }
    }
}
