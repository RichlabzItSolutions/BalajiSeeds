package com.balajiseeds;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.provider.Settings;

import com.balajiseeds.utils.CheckAndStart;

import java.util.Map;

public class SplashScreenActivity extends AppCompatActivity {

    ActivityResultLauncher<String[]> permissionLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        permissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), new ActivityResultCallback<Map<String, Boolean>>() {
            @Override
            public void onActivityResult(Map<String, Boolean> permissionResults) {
                // Check the results for each permission
                boolean allPermissionsGranted = true;
                for (Map.Entry<String, Boolean> entry : permissionResults.entrySet()) {
                    String permission = entry.getKey();
                    boolean isGranted = entry.getValue();
                    if (!isGranted) {
                        // Handle the case where permission is not granted
                        allPermissionsGranted = false;
                        // You can break the loop here if you want to handle permissions individually
                    }
                }

                if (allPermissionsGranted) {
                    // All permissions are granted, proceed with your next steps
                    startNext();
                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(SplashScreenActivity.this)
                            .setMessage("You must manually allow location permission and notification from settings to continue")
                            .setPositiveButton("Open Settings", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                                    intent.setData(uri);
                                    startActivity(intent);
                                }
                            })
                            .setNegativeButton("Cancel", null)
                            .setOnDismissListener(new DialogInterface.OnDismissListener() {
                                @Override
                                public void onDismiss(DialogInterface dialog) {
                                    checkPermission2();
                                }
                            })
                            .create();
                    alertDialog.show();
                    // Handle the case where not all permissions are granted
                    // You might want to inform the user or take appropriate action
                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        checkPermission2();
    }

    public void checkPermission2() {
        String[] permissionsToRequest = new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.POST_NOTIFICATIONS};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permissionLauncher.launch(permissionsToRequest);
        } else {
            startNext();
        }
    }


    private void checkAndRequestLocationPermissions() {
        if (checkPermission(Manifest.permission.ACCESS_FINE_LOCATION)) {
            startNext();
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                requestPermission(Manifest.permission.ACCESS_FINE_LOCATION);
            } else {
                requestPermission(Manifest.permission.ACCESS_FINE_LOCATION);
            }
        }
    }

    private boolean checkPermission(String permission) {
        return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission(String permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            requestPermissionLauncher.launch(permission);
        }
    }

    private void startNext() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Intent intent = new Intent();
            String packageName = getPackageName();
            PowerManager pm = (PowerManager) getSystemService(POWER_SERVICE);
            if (!pm.isIgnoringBatteryOptimizations(packageName)) {
                intent.setAction(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
                intent.setData(Uri.parse("package:" + packageName));
                startActivityForResult(intent, 111);
            } else {
                startHandler();
            }
        } else {
            startHandler();
        }


    }

    private void startHandler() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                new CheckAndStart(SplashScreenActivity.this);
            }
        }, 2000);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 111) {
            if (resultCode == RESULT_OK) {
                // User allowed battery optimization
                startHandler();
            } else {
                startNext();
            }
        }
    }

    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    startNext();
                } else {
                    requestPermission(Manifest.permission.ACCESS_FINE_LOCATION);
                }
            });


}
