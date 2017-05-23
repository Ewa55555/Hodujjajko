package com.example.hodujjajko;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CreatingSchedule extends AppCompatActivity implements View.OnClickListener{

    private EditText scheduleName;
    private Button startTime;
    private Button endTime;
    private Button chooseDay;
    private Button chooseDate;
    private RadioGroup frequencyRadioGroup;
    private RadioButton frequencyPerWeek;
    private RadioButton frequencyOnce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.creating_schedule);
        init();
    }

    private void init(){
        scheduleName = (EditText) findViewById(R.id.scheduleName);
        scheduleName.setOnClickListener(this);

        chooseDate = (Button) findViewById(R.id.chooseDate);
        chooseDay = (Button) findViewById(R.id.chooseDay);
        chooseDay.setEnabled(false);
        chooseDate.setEnabled(false);

        frequencyRadioGroup = (RadioGroup) findViewById(R.id.frequency);
        frequencyOnce = (RadioButton) findViewById(R.id.once);
        frequencyPerWeek = (RadioButton) findViewById(R.id.perWeek);

        startTime = (Button) findViewById(R.id.startTime);
        startTime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showTimePickerDialog();
            }
        });
        endTime = (Button) findViewById(R.id.endTime);
        endTime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showTimePickerDialog();
            }
        });
        chooseDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
        frequencyRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

                if (checkedId == R.id.once) {
                    chooseDay.setEnabled(false);
                    chooseDate.setEnabled(true);
                } else if (checkedId == R.id.perWeek) {
                    chooseDay.setEnabled(true);
                    chooseDate.setEnabled(false);
                }
            }
        });
        chooseDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDayPickerDialog();
            }
        });

    }

    public static class TimePickerFragment extends DialogFragment implements
            TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return
            return new TimePickerDialog(getActivity(), this, hour, minute, true);
        }


        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//            setTimeString(hourOfDay, minute, 0);
//
//            timeView.setText(timeString);
        }
    }
    private void showTimePickerDialog() {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getFragmentManager(), "timePicker");
    }

    public static class DatePickerFragment extends DialogFragment implements
            DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of TimePickerDialog and return
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }


        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        }
    }

    public static class DayPickerFragment extends DialogFragment{

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            Dialog dialog = new Dialog(getActivity());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.days_dialog);
            List<String> stringList=new ArrayList<>();  // here is list

            stringList.add(getString(R.string.monday));
            stringList.add(getString(R.string.tuesday));
            stringList.add(getString(R.string.wednesday));
            stringList.add(getString(R.string.thursday));
            stringList.add(getString(R.string.friday));
            stringList.add(getString(R.string.saturday));
            stringList.add(getString(R.string.sunday));

            RadioGroup rg = (RadioGroup) dialog.findViewById(R.id.radio_group);

            for(int i=0;i<stringList.size();i++){
                RadioButton rb=new RadioButton(getActivity());
                rb.setText(stringList.get(i));
                rg.addView(rb);
            }

            return dialog;
        }

    }


    private void showDatePickerDialog(){
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }

    private void showDayPickerDialog(){
        DialogFragment newFragment = new DayPickerFragment();
        newFragment.show(getFragmentManager(), "dayPicker");
    }

    @Override
    public void onClick(View v) {

    }
}
