package com.example.hodujjajko;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CreatingSchedule extends AppCompatActivity implements View.OnClickListener{

    private EditText scheduleName;
    private Button startTime;
    private Button endTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.creating_schedule);
        init();
    }

    private void init(){
        scheduleName = (EditText) findViewById(R.id.scheduleName);
        scheduleName.setOnClickListener(this);
        startTime = (Button) findViewById(R.id.startTime);
        startTime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //showDatePickerDialog();
            }
        });
        endTime = (Button) findViewById(R.id.endTime);
        endTime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //showDatePickerDialog();
            }
        });
    }

    @Override
    public void onClick(View v) {

    }
}
