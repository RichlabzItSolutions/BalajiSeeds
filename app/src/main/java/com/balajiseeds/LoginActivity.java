package com.balajiseeds;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;


import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.InputType;

import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import com.balajiseeds.admin.AdminMainActivity;
import com.balajiseeds.databinding.ActivityLoginBinding;
import com.balajiseeds.employee.BaseActivity;
import com.balajiseeds.models.ModelLogin;
import com.balajiseeds.utils.CheckAndStart;
import com.balajiseeds.utils.Constants;
import com.balajiseeds.utils.LocationHelper;
import com.balajiseeds.utils.SharedPref;
import com.balajiseeds.utils.WebServices;

import java.util.Map;

public class LoginActivity extends BaseActivity {
    ActivityLoginBinding binding;
    WebServices webServices;
    private LocationHelper locationHelper;
    ActivityResultLauncher<String[]> permissionLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setPermissionCheck();
        locationHelper = new LocationHelper(getApplicationContext());

        webServices = new WebServices(LoginActivity.this);

        binding.ivShowPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                togglePasswordVisibility();
            }
        });
        binding.tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               checkPermission2();
            }
        });
        binding.tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
            }
        });


        binding.tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
            }
        });


        locationHelper.getLastLocation();
    }

    private void Login() {
        String mob = binding.etMob.getText().toString();
        if (!mob.isEmpty() && mob.length() == 10 && Integer.valueOf(mob.substring(0, 1)) > 5) {
            ModelLogin.LoginRequest loginRequest = new ModelLogin.LoginRequest(binding.etMob.getText().toString(), binding.etPassword.getText().toString(), String.valueOf(Constants.lat), String.valueOf(Constants.lon));
            webServices.Login(loginRequest, new WebServices.onLogin() {
                @Override
                public void onLoggedIn(ModelLogin.Data data) {
                    SharedPref sharedPref = new SharedPref(LoginActivity.this);
                    sharedPref.setString(SharedPref.mobile, binding.etMob.getText().toString());
                    sharedPref.setString(SharedPref.userId, data.getUserId());
                    sharedPref.setString(SharedPref.token, data.getToken());
                    sharedPref.setString(SharedPref.userType, data.getUserType());

                    Log.d("token", data.getToken());
//                        if(data.getUserType().equals("1")) {
//                            startActivity(new Intent(LoginActivity.this, AdminMainActivity.class));
//                            finish();
//                        }
                    new CheckAndStart(LoginActivity.this);
                }
            });
        } else {
            Toast.makeText(LoginActivity.this, "Enter valid mobile number", Toast.LENGTH_SHORT).show();
            binding.etMob.requestFocus();
        }
    }

    private void setPermissionCheck() {
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
                    Login();
                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this)
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
    public void checkPermission2() {
        String[] permissionsToRequest = new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.POST_NOTIFICATIONS};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permissionLauncher.launch(permissionsToRequest);
        } else {
            Login();
        }
    }


    private void togglePasswordVisibility() {
        if (binding.etPassword.getTransformationMethod() == null) {
            binding.etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            binding.ivShowPassword.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.icon_ionic_ios_eye_off, null));
        } else {
            binding.etPassword.setTransformationMethod(null);
            binding.ivShowPassword.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.baseline_visibility_24, null));
        }

        // Move cursor to the end of the text
        binding.etPassword.setSelection(binding.etPassword.getText().length());
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 111) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locationHelper.getLastLocation();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
//        if (locationHelper.checkPermissions()) {
        locationHelper.getLastLocation();
//        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webServices.dismissDialog();
    }
}
