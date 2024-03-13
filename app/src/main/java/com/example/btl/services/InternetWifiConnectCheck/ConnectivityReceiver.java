package com.example.btl.services.InternetWifiConnectCheck;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.btl.pages.MainActivity;

public class ConnectivityReceiver extends BroadcastReceiver {
    private final MainActivity mainActivity;

    public ConnectivityReceiver(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    @Override
    public void onReceive(Context context, Intent intent) {
        // Check for internet and Wi-Fi connection when connectivity changes
        mainActivity.checkInternetConnection();
    }
}
