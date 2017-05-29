package com.example.hodujjajko;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Schedule extends AppCompatActivity implements View.OnClickListener{

    private Button createSchedule;
    private CalendarView calendarView;
    PlanDao planDao;
    static List<Plan> planDay = new ArrayList<Plan>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule);
        createSchedule = (Button)findViewById(R.id.createSchedule);
        createSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                creatingScheduleActivity();
            }
        });

        calendarView = (CalendarView) findViewById(R.id.calendarView);

    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.i("RESUME", "RESUME");
        Log.i("RESUME", "tworze nowe dao");
        planDao = new PlanDao(this);
        planDao.open();
        List<Plan> p = planDao.fetchAllData();
        for(Plan e : p)
        {
            Log.i("Sche","wynik z bazy   "+e.name+" "+  e.isOnce+ " " + e.day + " "+ e.dayOfWeek + " " + e.timeStart + " "+ e.timeEnd);
        }
        planDao.close();
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {
                planDao.open();
                List<Plan> planList = planDao.fetchAllData();
                planDay = new ArrayList<Plan>();
                for (int i = 0; i < planList.size(); i++) {
                    Plan plan = planList.get(i);
                    Log.i("Schedule", "rozpatruje "+plan.name);
                    if ((plan.isOnce && plan.getDay() == dayOfMonth && plan.getMonth() == (month + 1)
                            && plan.getYear() == year) || (!plan.isOnce &&
                            checkDayOfWeek(year, month, dayOfMonth) == plan.dayOfWeek)) {
                        planDay.add(plan);
                        Log.i("Schedule", "dodaje do listy "+plan.name);
                    }
                    else{
                        Log.i("Schedule", "jestem w else");
                        Log.i("Schedule", "dzien w rzeczyw "+checkDayOfWeek(year, month, dayOfMonth));
                        Log.i("Schedule", "w bazie"+plan.dayOfWeek);
                    }
                }
                planDao.close();
                if (planDay.isEmpty()) {
                    Toast.makeText(getApplicationContext(), getString(R.string.no_events_string), Toast.LENGTH_SHORT).show();
                } else {
                    Bundle args = new Bundle();
                    args.putString("date", dayOfMonth+"."+(month+1)+"."+year);
                    FragmentManager fm = getFragmentManager();
                    PlanOfDay dialogFragment = new PlanOfDay();
                    dialogFragment.setArguments(args);
                    dialogFragment.show(fm, "Sample Fragment");
                }

            }
        });
    }

    public int checkDayOfWeek(int year, int month, int day) {
        Calendar c = Calendar.getInstance();
        c.set(year, month, day);
        Log.i("checkday","wedlug funkcji dzien"+year+" "+(month+1)+" "+day+" jest "+c.get(Calendar.DAY_OF_WEEK));
        return c.get(Calendar.DAY_OF_WEEK);
    }

    private void creatingScheduleActivity(){
        Intent creatingScheduleIntent = null;
        creatingScheduleIntent = new Intent(getApplicationContext(), CreatingSchedule.class);
        startActivity(creatingScheduleIntent);
    }

    @Override
    public void onClick(View v) {

    }

    public static class PlanOfDay extends DialogFragment {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            Dialog dialog = new Dialog(getActivity());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.plan_of_day);
            Bundle mArgs = getArguments();
            LinearLayout layout = (LinearLayout)dialog.findViewById(R.id.linearLayout);

            for (int i = 0; i < planDay.size(); i++) {
                LinearLayout newLayout = new LinearLayout(getActivity());
                layout.addView(newLayout);
                TextView textView = new TextView(getActivity());
                final String name = planDay.get(i).name;
                final String time = planDay.get(i).timeStart + " " + mArgs.getString("date");
                textView.setText(name + "\n"+ planDay.get(i).timeStart +"-" + planDay.get(i).timeEnd);
                newLayout.addView(textView);
                Button button = new Button(getActivity());
                button.setText(getString(R.string.add_alert_string));
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addAlert(name, time);
                    }
                });
                newLayout.addView(button);
            }
            Button button = new Button(getActivity());
            button.setText(getString(R.string.ok_string));
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
            layout.addView(button);
            return dialog;
        }


        public void addAlert(String name, String start) {
            Intent intent = new Intent(getActivity(), ReminderBroadcastReceiver.class);
            intent.putExtra("name", name);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(
                    getActivity(), 234324243, intent, 0);
            AlarmManager alarmManager = (AlarmManager)getActivity().getSystemService(ALARM_SERVICE);
            Log.i("schedule", "ustawiam powiadomienie na "+(convertToMillis(start)));
            alarmManager.set(AlarmManager.RTC_WAKEUP, convertToMillis(start) - 5*60000, pendingIntent);
            Log.i("Alert", "ustawiam");
            Toast.makeText(getActivity(), getString(R.string.alert_added_string),Toast.LENGTH_SHORT).show();
        }
        private long convertToMillis(String s){
            Log.i("creating", "string "+s);
            SimpleDateFormat sdf =  new SimpleDateFormat("kk:mm:ss dd.MM.yyyy");
            try {
                Date date = sdf.parse(s);
                Log.i("creating", "milisek "+date.getTime());
                return date.getTime();
            }catch (Exception e){
                Log.i("creating", "weszlam do catcha");
                return 0;
            }
        }
    }
}
