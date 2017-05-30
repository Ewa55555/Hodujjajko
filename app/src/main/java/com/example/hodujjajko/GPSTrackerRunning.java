package com.example.hodujjajko;


import android.content.Context;
import android.util.Log;
import android.widget.TextView;

public class GPSTrackerRunning extends GPSTracker{
    private TextView textView;

    public GPSTrackerRunning(Context context, TextView textView)
    {
        super(context);
        locationDAO = new LocationDAO(context);
        this.textView = textView;
    }

    @Override
    public void onLocationChanged(android.location.Location location) {
        locationDAO.open();
        Log.i("GPS", "onlocationchanged");
        if (!locationDAO.fetchAllData().isEmpty()) {
            double oldLatitude = latitude;
            double oldLongitude = longitude;
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            float[] results = new float[1];
            results[0] = 0;
            android.location.Location.distanceBetween(oldLatitude, oldLongitude,
                    latitude, longitude, results);
            Log.i("GPS", "distans " + results[0]);
            Float distance = Float.parseFloat(textView.getText().toString()) + results[0];

            textView.setText(distance.toString());

            Log.i("GPS","old" +oldLatitude+" "+oldLongitude+" location"+latitude+" "+longitude);
        }
        locationDAO.addLocation(new com.example.hodujjajko.Location(latitude, longitude));
        locationDAO.close();

    }
}
