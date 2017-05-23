package com.example.hodujjajko;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class Schedule extends AppCompatActivity implements View.OnClickListener{

    private Button createSchedule;
    private Button seeSchedule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule);
        init();
    }

    private void init(){
        createSchedule = (Button)findViewById(R.id.createSchedule);
        createSchedule.setOnClickListener(this);
        seeSchedule = (Button)findViewById(R.id.seeSchedule);
        seeSchedule.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.createSchedule:
                creatingScheduleActivity();
                break;
            case R.id.seeSchedule:
                break;
        }
    }

    private void creatingScheduleActivity(){
        Intent creatingScheduleIntent = null;
        creatingScheduleIntent = new Intent(getApplicationContext(), CreatingSchedule.class);
        startActivity(creatingScheduleIntent);
    }

}
