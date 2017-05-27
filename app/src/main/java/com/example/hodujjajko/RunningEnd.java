package com.example.hodujjajko;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class RunningEnd extends FragmentActivity implements OnMapReadyCallback {

    LocationDAO locationDAO;
    private TextView durationTime;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.running_end);
        locationDAO = new LocationDAO(this);
        final MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        durationTime = (TextView)findViewById(R.id.durationTime);
        long duration = System.currentTimeMillis() - getIntent().getExtras().getLong("startTime");
        durationTime.setText(convertTime(duration));

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.i("RunningEnd", "przygotowywuje mape");
        locationDAO.open();
        List<Location> locations = locationDAO.fetchAllData();
        googleMap.animateCamera( CameraUpdateFactory.newLatLngZoom(new LatLng(locations.get(0).longitude,
                locations.get(0).latitude), 17.0f ) );

        PolylineOptions polylineOptions = new PolylineOptions();
        for (int i = 0; i < locations.size() - 1; i++) {
            Log.i("RunningEnd", locations.get(i).longitude+" "+locations.get(i).latitude);
            polylineOptions.add(new LatLng(locations.get(i).longitude, locations.get(i).latitude),
                    new LatLng(locations.get(i + 1).longitude, locations.get(i + 1).latitude));
            Log.i("RunningEnd","dodaje linie między "+ locations.get(i).longitude+" "+ locations.get(i).latitude+" a "
                    +locations.get(i + 1).longitude+" " +locations.get(i + 1).latitude);
        }
        polylineOptions.width(1000).color(Color.RED);
        Polyline line = googleMap.addPolyline(polylineOptions);
        locationDAO.close();
    }

    public String convertTime(long millis){
        String s = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis)
                        - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                TimeUnit.MILLISECONDS.toSeconds(millis) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))
        );
        return s;
    }
}
