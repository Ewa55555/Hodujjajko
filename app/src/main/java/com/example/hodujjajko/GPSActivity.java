package com.example.hodujjajko;

import android.graphics.Color;
import android.os.Bundle;
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

import java.util.List;


public class GPSActivity extends FragmentActivity implements OnMapReadyCallback {

    GPSTracker gps;
    double longitude;
    double latitude;
    private Button stopRunning;
    private Button startRunning;
    private TextView distance;
    LocationDAO locationDAO;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.running);
        locationDAO = new LocationDAO(this);

        distance = (TextView) findViewById(R.id.distance);
        stopRunning = (Button) findViewById(R.id.stopRunning);
        stopRunning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mapFragment.getView().setVisibility(View.VISIBLE);
                alo();
                gps.stopUsingGPS();

            }
        });
        startRunning = (Button) findViewById(R.id.startRunning);
        startRunning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start();
            }
        });

        //mapFragment.getView().setVisibility(View.GONE);
        Log.i("GA", "jestem w on click");

    }

    private void start() {
       locationDAO.open();
       locationDAO.deleteAll();
        gps = new GPSTracker(GPSActivity.this, distance);
        if (gps.canGetLocation()) {
            latitude = gps.getLatitude();
            longitude = gps.getLongitude();
            Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
        } else {
            gps.showSettingsAlert();
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        locationDAO.open();
        List<Location> locations = locationDAO.fetchAllData();
        PolylineOptions polylineOptions = new PolylineOptions();
        for (int i = 0; i < locations.size() - 1; i++) {
            polylineOptions.add(new LatLng(locations.get(i).longitude, locations.get(i).latitude),
                    new LatLng(locations.get(i + 1).longitude, locations.get(i + 1).latitude));
        }
        polylineOptions.width(5).color(Color.RED);
        Polyline line = googleMap.addPolyline(polylineOptions);
        locationDAO.close();
    }

    public void alo(){
        final MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }
}

