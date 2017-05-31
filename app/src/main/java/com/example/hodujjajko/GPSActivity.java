package com.example.hodujjajko;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.location.*;
import android.os.Bundle;
import android.sax.StartElementListener;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.Calendar;
import java.util.List;


public class GPSActivity extends Activity {

    GPSTrackerRunning gps;
    double longitude;
    double latitude;
    private Button stopRunning;
    private Button startRunning;
    private TextView distance;
    private TextView timeRunning;
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
                //mapFragment.getView().setVisibility(View.VISIBLE);
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
                if(minute<10)
                    startTimeTraining = String.valueOf(day)+ "-" + String.valueOf(month+1)+"-"+ String.valueOf(year)+" "+String.valueOf(hour)+":"+"0"+String.valueOf(minute);
                else
                    startTimeTraining = String.valueOf(day)+ "-" + String.valueOf(month+1)+"-"+ String.valueOf(year)+" "+String.valueOf(hour)+":"+String.valueOf(minute);

                start();
            }
        });

        //mapFragment.getView().setVisibility(View.GONE);
        Log.i("GA", "jestem w on click");

    }

    private void start() {
        startRunning.setEnabled(false);
        stopRunning.setEnabled(true);
        startTime = System.currentTimeMillis();
        locationDAO.open();
        Log.i("GPS", "usuwam baze");
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
        Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.i("GPS","onrequestpermission");
        if (requestCode == gps.MY_PERMISSIONS_CODE) {
            Log.i("Gps", "request w ifie");
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

