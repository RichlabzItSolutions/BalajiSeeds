package com.balajiseeds.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Looper;
import android.os.PowerManager;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.balajiseeds.employee.models.ModelTracking;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class LocationHelper {
    private final FusedLocationProviderClient mFusedLocationClient;
    Context context;
    private int count = 0;
    private PowerManager.WakeLock wakeLock;
    private LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            handleLocationResult(locationResult.getLastLocation());
        }
    };

    private LocationCallback repetingLocationCallback = new LocationCallback() {
        public void onLocationResult(LocationResult locationResult) {

            Location l = locationResult.getLastLocation();
            Log.e("locationUpdate", l.getLatitude() + "===" + l.getLongitude() + "==count==" + count);
            //LOCATION CHANGE
            new WebServices(context).AddEmpTracking(new ModelTracking.TrackingRequest(String.valueOf(l.getLatitude()), String.valueOf(l.getLongitude())));
//            new WebServices(context).AddEmpTracking(new ModelTracking.TrackingRequest(String.valueOf(21.408686), String.valueOf(79.9279701)));

        }
    };

    public LocationHelper(Context context) {
        this.context = context;
        this.mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
    }

    @SuppressLint("MissingPermission")
    public void getLastLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        handleLocationResult(task.getResult());
                    }
                });
            } else {
                showLocationSettings();
            }
        } else {
            requestPermissions();
        }
    }

    @SuppressLint("MissingPermission")
    public void requestNewLocationData() {
        PowerManager powerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        if (powerManager != null) {
            wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "LocationWakeLock:LocationHelper");
            wakeLock.acquire();
        }

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(5 * 60 * 1000);
        mLocationRequest.setFastestInterval(5 * 60 * 1000);
        mLocationRequest.setNumUpdates(1000);
//        LocationManager locationManager=(LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
//        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000, 0f, new LocationListener() {
//            @Override
//            public void onLocationChanged(@NonNull Location location) {
//                Location l=location;
//                Log.e("locationUpdate",l.getLatitude()+"==="+l.getLongitude());
//                new WebServices(context).AddEmpTracking(new ModelTracking.TrackingRequest(String.valueOf(l.getLatitude()),String.valueOf(l.getLongitude())));
//
//            }
//        });
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, repetingLocationCallback, Looper.myLooper());
    }

    public void removeLocationUpdates() {
        if (mLocationCallback != null) {
            mFusedLocationClient.removeLocationUpdates(mLocationCallback);
        }
    }

    private void handleLocationResult(Location location) {
        if (location != null) {
            Log.e("lat", "" + location.getLatitude());
            Log.e("lon", "" + location.getLongitude());
            //LOCATION CHANGE
            Constants.lat = location.getLatitude();
            Constants.lon = location.getLongitude();
            // Do something with the location
        } else {
            Log.e("Location Error", "Unable to retrieve location");
            // Handle the error accordingly
        }
    }

    public boolean checkPermissions() {
        int coarseLocationPermission = ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION);
        int fineLocationPermission = ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION);
        return coarseLocationPermission == PackageManager.PERMISSION_GRANTED && fineLocationPermission == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        try {
            ActivityCompat.requestPermissions((Activity) context,
                    new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION}, 111);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return locationManager != null &&
                (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER));
    }

    private void showLocationSettings() {
        Toast.makeText(context, "Please turn on your location...", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
