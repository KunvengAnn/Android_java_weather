package com.example.btl.Helper;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.example.btl.R;
import com.example.btl.pages.MainActivity;

public class NotificationHelper {
    // Notification channel constants
    private static final String CHANNEL_ID = "my_channel_id";
    private static final String CHANNEL_NAME = "My Channel";
    private static final int NOTIFICATION_ID = 1;

    // Method to create and display a notification
    public static void createNotification(Context context, String title, String message) {
        // Check if device is running on Android Oreo (API 26) or higher
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(context); // Create notification channel if it doesn't exist
        }

        // Create an intent to open MainActivity when notification is clicked
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        // Build the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.baseline_anchor_24) // Set small icon for the notification
                .setContentTitle(title) // Set title of the notification
                .setContentText(message) // Set content text of the notification
                .setContentIntent(pendingIntent) // Set the action to be performed when notification is clicked
                .setAutoCancel(true); // Automatically dismiss the notification when clicked

        // Get the notification manager system service
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // Display the notification
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

    // Method to create notification channel (required for Android 8.0+)
    private static void createNotificationChannel(Context context) {
        // Check if device is running on Android Oreo (API 26) or higher
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create notification channel with the specified ID, name, and importance level
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            // Configure the notification channel (optional)
            notificationChannel.enableLights(true); // Enable notification lights
            notificationChannel.setLightColor(Color.RED); // Set notification light color
            // Create the notification channel
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }
}
