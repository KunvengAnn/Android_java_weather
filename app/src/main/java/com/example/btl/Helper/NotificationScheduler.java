package com.example.btl.Helper;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.example.btl.R;
import com.example.btl.pages.MainActivity;

import java.util.Calendar;

public class NotificationScheduler {
    // Method to schedule a notification to be shown every day at 6 AM
    public static void scheduleDailyNotification(Context context) {
        // Create an intent to broadcast to the NotificationReceiver
        Intent intent = new Intent(context, NotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Set up the alarm to trigger at 6 AM every day
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 6);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    // Method to cancel the daily notification
    public static void cancelDailyNotification(Context context) {
        // Create an intent to broadcast to the NotificationReceiver
        Intent intent = new Intent(context, NotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Cancel the alarm
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
    }

    // Method to create a custom notification with a specified title and message
    public static void createNotification(Context context, String title, String message) {
        final String CHANNEL_ID = "my_channel_id";
        final int NOTIFICATION_ID = 1234;

        // Create an intent to open MainActivity when notification is clicked
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        // Build the notification with the provided title and message
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.baseline_anchor_24)
                .setContentTitle(title) // Set title of the notification
                .setContentText(message) // Set content text of the notification
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        // Get the notification manager system service
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // Display the notification
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // For Android Oreo and above, create a notification channel
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Channel Name", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        // Display the notification
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }
}
