package com.example.hodujjajko;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;


public class GPSTracker extends Service implements LocationListener {
    private final Context context;
    boolean isGPSOn =false;
    boolean isNetworkOn= false;
    boolean canGetLocation = false;
    Location location;
    double latitude;
    double longitude;
    public LocationManager locationManager;
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 2;
    private static final long MIN_TIME_BW_UPDATES = 1000 * 30;
    public int  MY_PERMISSIONS_CODE;
    LocationDAO locationDAO;

    public GPSTracker(Context context)
    {
        this.context = context;
        locationDAO = new LocationDAO(context);
        tryGetLocation();
    }

    public Location tryGetLocation() {
        if (Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSIONS_CODE);
                return null;
        }
        else{
            return onPermissionsReady();
        }

    }


    public Location onPermissionsReady() {

        locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
        isGPSOn = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetworkOn = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        try{
        if (!isGPSOn && !isNetworkOn) {
        } else {

            this.canGetLocation = true;
            if (isNetworkOn) {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                if (locationManager != null) {
                    location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    if (location != null) {
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                    }
                }
            }
            if (isGPSOn) {
                if (location == null) {
                    locationManager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    if (locationManager != null) {
                        location = locationManager
                                .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                    }
                }
            }
        }}
        catch(SecurityException e){
            e.printStackTrace();
        }
        return location;
    }


    public void stopUsingGPS(){
        if(locationManager != null){
            locationManager.removeUpdates(GPSTracker.this);
        }
    }

    public double getLatitude(){
        if(location != null){
            latitude = location.getLatitude();
        }
        return latitude;
    }

    public double getLongitude(){
        if(location != null){
            longitude = location.getLongitude();
        }
        return longitude;
    }
    public boolean canGetLocation() {
        return this.canGetLocation;
    }

    public void showSettingsAlert(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setMessage(getString(R.string.alert_settings_string));

        alertDialog.setPositiveButton(getString(R.string.settings_string), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                context.startActivity(intent);
                onPermissionsReady();
            }
        });

        alertDialog.setNegativeButton(getString(R.string.delete), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        alertDialog.show();

    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
