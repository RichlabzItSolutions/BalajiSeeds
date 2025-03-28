package com.balajiseeds.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.util.List;

public class LogoutReciver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        new SharedPref(context).clearAll();
        try {
            ((Activity) context).finishAndRemoveTask();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Toast.makeText(context, "Called Logout", Toast.LENGTH_SHORT).show();
        Intent serviceIntent = new Intent(context, ForegroundService.class);
        context.stopService(serviceIntent);
        try {
            ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

            // Get the list of running tasks
            List<ActivityManager.AppTask> appTasks = activityManager.getAppTasks();

            // Iterate through the tasks and finish each activity
            for (ActivityManager.AppTask appTask : appTasks) {
                appTask.finishAndRemoveTask();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
