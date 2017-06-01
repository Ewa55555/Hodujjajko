package com.example.hodujjajko;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrainingAlert extends Activity {
    private double temperature;
    private ForecastService forecastService;
    private double longitude;
    private double latitude;
    private String temperatureString;
    SensorManager sensorManager;
    Sensor gravitySensor;
    SensorEventListener gravityEventListener;
    private Intent intent;
    private MediaPlayer mp;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        forecastService = new ForecastService();
        temperatureString = "";
        mp = MediaPlayer.create(getApplicationContext(),R.raw.there_is_a_fire_somewhere);
        mp.setLooping(true);
        mp.start();
        intent = getIntent();
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        gravitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        if (gravitySensor == null){
            Toast.makeText(this,getString(R.string.sensor_unavailable_string), Toast.LENGTH_SHORT).show();
        }
        else {
            gravityEventListener = new SensorEventListener() {
                @Override
                public void onSensorChanged(SensorEvent event) {
                    if(event.values[1]>0.5f){
                        mp.stop();
                    }
                }

                @Override
                public void onAccuracyChanged(Sensor sensor, int accuracy) {

                }
            };
        }
        weather();
    }
    private void alert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder
                .setTitle(getString(R.string.notification_string))
                .setMessage(getString(R.string.alert_message_string)+" "+intent.getStringExtra("name")+"\n"+
                        temperatureString)
                .setCancelable(false)
                .setPositiveButton(getString(R.string.ok_string), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        mp.stop();
                        finish();
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    }
    private void weather(){
        GPSTracker gpsTracker = new GPSTracker(getApplicationContext());
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if (gpsTracker.canGetLocation() && (
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)) {
            latitude = gpsTracker.getLatitude();
            longitude = gpsTracker.getLongitude();
            Callback<Forecast> callback = new Callback<Forecast>() {
                @Override
                public void onResponse(Call<Forecast> call, Response<Forecast> response) {
                    temperature = (response.body().getCurrently().getTemperature() - 32) * 5 / 9;
                    temperatureString = getString(R.string.weather_message_string)+
                            (int)temperature+getString(R.string.degrees_string);
                    alert();
                }
                @Override
                public void onFailure(Call<Forecast> call, Throwable t) {
                    Log.i("Main",t.getMessage());
                }
            };
            forecastService.LoadForecastDate(callback, latitude ,longitude );
        } else {
            alert();
        }

    }
    @Override
    public void onResume(){
        super.onResume();
        sensorManager.registerListener(gravityEventListener, gravitySensor, SensorManager.SENSOR_DELAY_FASTEST);
    }
}

