package com.balajiseeds.utils;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.balajiseeds.R;
import com.balajiseeds.utils.LocationHelper;

import java.util.Calendar;

public class ForegroundService extends Service {
    private static final String CHANNEL_ID = "ForegroundServiceChannel";
    private static final String ACTION_ALARM = "com.balajiseeds.ACTION_ALARM";
    private LocationHelper locationHelper;
    private PowerManager.WakeLock wakeLock;
    private LogoutReciver logoutReceiver = new LogoutReciver();


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        scheduleAlarm(getApplicationContext());
        acquireWakeLock();
        IntentFilter filter = new IntentFilter("com.balajiseeds.ACTION_ALARM");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                registerReceiver(logoutReceiver, filter, Context.RECEIVER_NOT_EXPORTED);

        }
        locationHelper.requestNewLocationData();
        String input = intent.getStringExtra("inputExtra");
        createNotificationChannel();

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Foreground Service")
                .setContentText(input)
                .setSmallIcon(R.drawable.logo)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setOngoing(true)
                .build();

        startForeground(1, notification);


        return START_NOT_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        locationHelper = new LocationHelper(getApplicationContext());
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Foreground Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }

    public void scheduleAlarm(Context context) {
        AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        // Create an Intent with a custom action
        Intent intent = new Intent(context, LogoutReciver.class);
        intent.setAction(ACTION_ALARM);

        // Get a PendingIntent for the alarm
        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 121, intent, PendingIntent.FLAG_IMMUTABLE);

        // Create a Calendar instance and set it to the desired time
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);

        // Check if the desired time is in the future
        if (calendar.getTimeInMillis() > System.currentTimeMillis()) {
            // Set the alarm based on the SDK version
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && alarmMgr.canScheduleExactAlarms()) {
                alarmMgr.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmIntent);
            } else {
                alarmMgr.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmIntent);
            }
        }

        Log.e("Alarm", alarmMgr.getNextAlarmClock()+"Alarm scheduled for: " + calendar.getTime());
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(logoutReceiver);
        releaseWakeLock();
        if (locationHelper != null) {
            locationHelper.removeLocationUpdates();
        }

        super.onDestroy();
    }

    private void acquireWakeLock() {
        PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        if (powerManager != null) {
            wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "LocationService::WakeLock");
            wakeLock.acquire();
        }
    }

    private void releaseWakeLock() {
        if (wakeLock != null && wakeLock.isHeld()) {
            wakeLock.release();
            wakeLock = null;
        }
    }
}
