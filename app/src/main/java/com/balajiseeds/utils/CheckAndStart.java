package com.balajiseeds.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.balajiseeds.LoginActivity;
import com.balajiseeds.SplashScreenActivity;

public class CheckAndStart {
    public CheckAndStart(Context context) {
        SharedPref sharedPref = new SharedPref(context);
        if (sharedPref.getString(SharedPref.token) != null && !sharedPref.getString(SharedPref.token).isEmpty()) {
            Log.d("token", "" + sharedPref.getString(SharedPref.token));
            if (sharedPref.getString(SharedPref.userType).equals("1")) {
                //admin
                context.startActivity(new Intent(context, com.balajiseeds.admin.AdminMainActivity.class));
                ((Activity) context).finish();
            }
            if (sharedPref.getString(SharedPref.userType).equals("6")) {
                //employee
                context.startActivity(new Intent(context, com.balajiseeds.employee.EmployeeMainActivity.class));
                ((Activity) context).finish();
            }
        } else {
            context.startActivity(new Intent(context, LoginActivity.class));
            ((Activity) context).finish();
        }
    }
}
