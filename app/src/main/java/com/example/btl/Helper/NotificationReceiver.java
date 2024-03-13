package com.example.btl.Helper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        // Get the title and message from the intent extras
        String title = intent.getStringExtra("title");
        String message = intent.getStringExtra("message");

        NotificationScheduler.createNotification(context, title, message);
    }
}
