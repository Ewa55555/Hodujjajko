package com.example.hodujjajko;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;

/**
 * Created by Ewunia on 29.05.2017.
 */

public class TrainingAlert extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder
                .setTitle("Powiadomienie")
                .setMessage(intent.getStringExtra("name"))
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        Log.i("alert", "usuwam");
                        finish();
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    }
}

