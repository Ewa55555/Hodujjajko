package com.example.hodujjajko;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

/**
 * Created by Kasia on 2017-05-23.
 */

public class GPSActivity extends FragmentActivity implements OnMapReadyCallback{

        // GPSTracker class
        GPSTracker gps;
        double longitude;
        double latitude;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.running);
            MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
            Log.i("GA","jestem w on click");
            gps = new GPSTracker(GPSActivity.this);

            // check if GPS enabled
            if(gps.canGetLocation()){

                latitude = gps.getLatitude();
                longitude = gps.getLongitude();

                // \n is for new line
                Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
            }else{
                // can't get location
                // GPS or Network is not enabled
                // Ask user to enable GPS/network in settings
                gps.showSettingsAlert();
            }

        }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Polyline line = googleMap.addPolyline(new PolylineOptions()
                .add(new LatLng(50.004274, 19.883685), new LatLng(50.015224, 19.889263))
                .width(5)
                .color(Color.RED));
//        googleMap.addMarker(new MarkerOptions()
//                .position(new LatLng(latitude, longitude))
//                .title("Marker"));
    }
}

