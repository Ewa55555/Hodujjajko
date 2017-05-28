package com.example.hodujjajko;



import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CalendarActivity extends AppCompatActivity {

    private CalendarView calendarView;
    PlanDao planDao;
    static List<Plan> planDay = new ArrayList<Plan>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar);
        planDao = new PlanDao(this);
        calendarView = (CalendarView) findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {

                planDao.open();
                List<Plan> planList = planDao.fetchAllData();
                planDay = new ArrayList<Plan>();
                for (int i = 0; i < planList.size() - 1; i++) {
                    Plan plan = planList.get(i);
                    if ((plan.isOnce && plan.getDay() == dayOfMonth && plan.getMonth() == (month + 1)
                            && plan.getYear() == year) || (plan.isOnce &&
                            checkDayOfWeek(year, month, dayOfMonth) == plan.dayOfWeek)) {
                        planDay.add(plan);
                    }

                }
                planDao.close();
                if (planDay.isEmpty()) {
                    Toast.makeText(getApplicationContext(), getString(R.string.no_events_string), Toast.LENGTH_SHORT).show();
                } else {
                    FragmentManager fm = getFragmentManager();
                    PlanOfDay dialogFragment = new PlanOfDay();
                    dialogFragment.show(fm, "Sample Fragment");
                    //Toast.makeText(getApplicationContext(), "masz wydarzenie", Toast.LENGTH_LONG).show();
//                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(CalendarActivity.this);
//                    alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.cancel();
//                        }
//                    });
//                    alertDialogBuilder.setNegativeButton("Usuń wydarzenia", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            Toast.makeText(getApplicationContext(), "bede usuwac", Toast.LENGTH_LONG).show();
//                        }
//                    });
//                    alertDialogBuilder.show();

                }

            }
        });

    }

    public int checkDayOfWeek(int year, int month, int day) {
        Calendar c = Calendar.getInstance();
        c.set(year, month, day);
        return c.get(Calendar.DAY_OF_WEEK);
    }

    public static class PlanOfDay extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            Dialog dialog = new Dialog(getActivity());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.plan_of_day);

            LinearLayout layout = (LinearLayout)dialog.findViewById(R.id.linearLayout);

            for (int i = 0; i < planDay.size(); i++) {
                TextView textView = new TextView(getActivity());
                textView.setText(planDay.get(i).name+"\n"+ planDay.get(i).timeStart+"-"+planDay.get(i).timeEnd);
                layout.addView(textView);
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
    }
}
