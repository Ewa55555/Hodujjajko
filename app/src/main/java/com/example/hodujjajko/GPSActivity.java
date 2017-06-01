package com.example.hodujjajko;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;


public class GPSActivity extends Activity {

    GPSTrackerRunning gps;
    double longitude;
    double latitude;
    private Button stopRunning;
    private Button startRunning;
    private TextView distance;
    LocationDAO locationDAO;
    long startTime;
    TrainingDao training;
    String startTimeTraining;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.running);
        locationDAO = new LocationDAO(this);
        training = new TrainingDao(this);
        distance = (TextView) findViewById(R.id.distance);

        stopRunning = (Button) findViewById(R.id.stopRunning);
        stopRunning.setEnabled(false);
        stopRunning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gps.stopUsingGPS();
                addToDatabase();
                Intent runningEndIntent = null;
                runningEndIntent = new Intent(getApplicationContext(), RunningEnd.class);
                runningEndIntent.putExtra("startTime", startTime);
                startActivity(runningEndIntent);
            }
        });
        startRunning = (Button) findViewById(R.id.startRunning);
        startRunning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DATE);
                if(minute < 10)
                    startTimeTraining = String.valueOf(day) + "-" + String.valueOf(month + 1) + "-"
                            + String.valueOf(year) + " "+ String.valueOf(hour) + ":" + "0" +
                            String.valueOf(minute);
                else
                    startTimeTraining = String.valueOf(day) + "-" + String.valueOf(month + 1) + "-"
                            + String.valueOf(year) + " " + String.valueOf(hour) + ":" +
                            String.valueOf(minute);

                start();
            }
        });
    }

    private void start() {
        startRunning.setEnabled(false);
        stopRunning.setEnabled(true);
        startTime = System.currentTimeMillis();
        locationDAO.open();
        locationDAO.deleteAll();
        locationDAO.close();
        gps = new GPSTrackerRunning(GPSActivity.this, distance);
        if (gps.canGetLocation()) {
            latitude = gps.getLatitude();
            longitude = gps.getLongitude();
        }
        else {
            android.location.Location l = gps.tryGetLocation();
            latitude = l.getLatitude();
            longitude = l.getLongitude();
        }
        Toast.makeText(getApplicationContext(), getString(R.string.location_string) + "\n" + latitude
                + "\n" + longitude, Toast.LENGTH_LONG).show();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == gps.MY_PERMISSIONS_CODE) {
            gps.showSettingsAlert();
        }

    }

    public void addToDatabase()
    {
        training.open();
        Training t = new Training();
        t.duration = (distance.getText().toString());
        t.start = startTimeTraining;
        t.points = Integer.parseInt(distance.getText().toString());
        t.typeOfTraining="Bieganie";
        training.addTraining(t);
        training.close();

    }

}

