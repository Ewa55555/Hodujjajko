package com.example.hodujjajko;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

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
    private TextView startTimeView;
    private TextView endTimeView;
    private TextView frequencyView;
    private static TextView dayView;
    private Button saveButton;

    private PlanDao plan;
    private int regularity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.creating_schedule);
        init();
    }

    private void init(){
        scheduleName = (EditText) findViewById(R.id.scheduleName);
        scheduleName.setOnClickListener(this);
        startTimeView = (TextView) findViewById(R.id.startTimeView);
        endTimeView = (TextView) findViewById(R.id.endTimeView);
        frequencyView = (TextView) findViewById(R.id.frequencyView);
        dayView = (TextView) findViewById(R.id.dayView);
        saveButton = (Button) findViewById(R.id.save);
        plan = new PlanDao(getApplicationContext());

        chooseDate = (Button) findViewById(R.id.chooseDate);
        chooseDay = (Button) findViewById(R.id.chooseDay);
        chooseDay.setEnabled(false);
        chooseDate.setEnabled(false);

        frequencyRadioGroup = (RadioGroup) findViewById(R.id.frequency);

        startTime = (Button) findViewById(R.id.startTime);
        startTime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showTimePickerDialog(R.id.startTimeView);
            }
        });
        endTime = (Button) findViewById(R.id.endTime);
        endTime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showTimePickerDialog(R.id.endTimeView);
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
                    regularity=0;
                    chooseDay.setEnabled(false);
                    chooseDate.setEnabled(true);
                    frequencyView.setText(getString(R.string.once_string));
                    dayView.setText("");
                } else if (checkedId == R.id.perWeek) {
                    regularity=1;
                    chooseDay.setEnabled(true);
                    chooseDate.setEnabled(false);
                    frequencyView.setText(getString(R.string.per_week_string));
                    dayView.setText("");
                }
            }
        });

        chooseDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDayPickerDialog();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (startTimeView.getText().equals("") || endTimeView.getText().equals("") ||
                        frequencyView.getText().equals("") || dayView.getText().equals("") ||
                        scheduleName.getText().equals("")){
                    Log.i("CreatingSchedule", "puste");
                    Toast.makeText(getApplicationContext(), getString(R.string.blank_string), Toast.LENGTH_LONG).show();
                }else {
                    addToDatabase();
                }


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
            Bundle bundle = getArguments();
            int id = bundle.getInt("textViewId");
            Log.i("CreatingSchedule", "id w onTimeSet "+ id);
            ((TextView)getActivity().findViewById(id)).setText(setTimeString(hourOfDay, minute));
        }
    }
    private void showTimePickerDialog(int textViewId) {
        DialogFragment newFragment = new TimePickerFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("textViewId", textViewId);
        Log.i("CreatingSchedule", "id w showTimePicker "+ textViewId);
        newFragment.setArguments(bundle);
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

            dayView.setText(setDateString(year, month, dayOfMonth));
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

            rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    int numberOfRadioButtons = group.getChildCount();
                    for (int i = 0; i < numberOfRadioButtons; i++) {
                        RadioButton button = (RadioButton) group.getChildAt(i);
                        if (button.getId() == checkedId) {
                            dayView.setText(button.getText().toString());
                            dismiss();

                        }
                    }
                }
            });

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

    private static String setTimeString(int hourOfDay, int minute) {
        String hour = "" + hourOfDay;
        String min = "" + minute;

        if (hourOfDay < 10)
            hour = "0" + hourOfDay;
        if (minute < 10)
            min = "0" + minute;

        String timeString = hour + ":" + min + ":00";
        return timeString;
    }

    private static String setDateString(int year, int monthOfYear, int dayOfMonth) {

        // Increment monthOfYear for Calendar/Date -> Time Format setting
        monthOfYear++;
        String mon = "" + monthOfYear;
        String day = "" + dayOfMonth;

        if (monthOfYear < 10)
            mon = "0" + monthOfYear;
        if (dayOfMonth < 10)
            day = "0" + dayOfMonth;

        String dateString = day + "." + mon + "." + year;
        return dateString;
    }

    @Override
    public void onClick(View v) {


    }

    public void addToDatabase()
    {
        Plan planData = new Plan();
        if (regularity==0)
        {
            Log.i("SCHe","jestem");
            planData.isOnce = true;
            Log.i("SCHe","wartosc isOnce"+planData.isOnce);
            planData.day=dayView.getText().toString();
        }
        else
        {
            planData.isOnce = false;
            planData.dayOfWeek = dayView.getText().toString();

        }

        planData.time = startTimeView.getText().toString();
        planData.name = scheduleName.getText().toString();
        plan.open();
        plan.addPlan(planData);
        List<Plan> p = plan.fetchAllData();
        for(Plan e : p)
        {
            Log.i("Sche","wynik z bazy   "+e.name+" "+  e.isOnce+ " " + e.day + " "+ e.dayOfWeek + " " + e.time);
        }

    }
    }

