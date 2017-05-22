package com.example.hodujjajko;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SettingTimer extends Activity implements View.OnClickListener{

    private EditText timer;
    private Button startTimer;
    private Button reset;
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
        startTimer = (Button)findViewById(R.id.startTimer);
        startTimer.setOnClickListener(this);
        reset = (Button)findViewById(R.id.reset);
        reset.setOnClickListener(this);
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
        int i = 0;
        boolean found = false;
        Log.i("SettingTimer", "Jestemy w on click");
        if (v.getId() == R.id.startTimer){
            if(timer.getText().toString().isEmpty()){
                Toast.makeText(this, getString(R.string.empty_timer_string), Toast.LENGTH_LONG).show();
            }else {
                Intent timerIntent = null;
                Log.i("SettingTimer", "przed intentem");
                timerIntent = new Intent(getApplicationContext(), RunTimer.class);
                Log.i("SettingTimer", "tworzymy intent i extras" + timer.getText());
                timerIntent.putExtra("text", timer.getText().toString());
                Log.i("SettingTimer", "zaraz bedziemy wlaczac runTimer");
                startActivity(timerIntent);
            }

        }else {
            if (v.getId() == R.id.reset) {
                timer.setText("");
            } else {
                while (i < BUTTON_IDS.length && !found) {
                    if (BUTTON_IDS[i] == v.getId()) {
                        found = true;
                    } else {
                        i++;
                    }
                }
                if (i < BUTTON_IDS.length) {
                    String text = timer.getText().toString();
                    if (text.matches("")) {
                        timer.setText(buttons.get(i).getText());
                    } else {
                        if (text.charAt(text.length() - 1) == '+') {
                            timer.setText(text + buttons.get(i).getText());
                        } else {
                            timer.setText(text + '+' + buttons.get(i).getText());
                        }
                    }
                }
            }
        }
    }
}
