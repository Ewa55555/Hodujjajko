package com.example.hodujjajko;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.*;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrainingAlert extends Activity {
    private double temperature;
    private ForecastService forecastService;
    private double longitude;
    private double latitude;
    private String temperatureString;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        forecastService = new ForecastService();
        temperatureString = "";
//        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//        final Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
        final MediaPlayer mp = MediaPlayer.create(getApplicationContext(),R.raw.there_is_a_fire_somewhere);
        mp.setLooping(true);
        mp.start();
        Intent intent = getIntent();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder
                .setTitle("Powiadomienie")
                .setMessage(getString(R.string.alert_message_string)+" "+intent.getStringExtra("name")+"\n"+
                temperatureString)
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        Log.i("alert", "usuwam");
                        mp.stop();
                        finish();
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    }
    private void test(){
        GPSTracker gpsTracker = new GPSTracker(getApplicationContext());
        if (gpsTracker.canGetLocation()) {
            latitude = gpsTracker.getLatitude();
            longitude = gpsTracker.getLongitude();
            Callback<Forecast> callback = new Callback<Forecast>() {
                @Override
                public void onResponse(Call<Forecast> call, Response<Forecast> response) {
                    temperature = (response.body().getCurrently().getTemperature() - 32) * 5 / 9;
                    temperatureString = getString(R.string.weather_message_string)+
                            (int)temperature+getString(R.string.degrees_string);
                }
                @Override
                public void onFailure(Call<Forecast> call, Throwable t) {
                    Log.i("Main",t.getMessage());
                }
            };
            forecastService.LoadForecastDate(callback, latitude ,longitude );
        } else {
            gpsTracker.showSettingsAlert();
        }

    }
}

