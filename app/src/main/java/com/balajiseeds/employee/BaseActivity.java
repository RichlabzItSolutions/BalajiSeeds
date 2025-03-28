package com.balajiseeds.employee;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {

    private LocationManager locationManager;
    private AlertDialog alertDialog;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (!isGPSEnabled()) {
            showGPSAlertDialog();
        }

        checkGPSStatusPeriodically();
    }

    private boolean isGPSEnabled() {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    private void showGPSAlertDialog() {
        alertDialog = new AlertDialog.Builder(this)
                .setTitle("GPS Required")
                .setMessage("GPS is required for this app. Please turn on GPS.")
                .setCancelable(false)
                .setPositiveButton("Turn On GPS", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(intent);
                    }
                })
                .create();
        try {
            alertDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void checkGPSStatusPeriodically() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!isGPSEnabled()) {
                    if (alertDialog == null || !alertDialog.isShowing()) {
                        showGPSAlertDialog();
                    }
                } else {
                    if (alertDialog != null && alertDialog.isShowing()) {
                        alertDialog.dismiss();
                    }
                }
                handler.postDelayed(this, 1000); // Check every 1 second
            }
        }, 1000);
    }
}