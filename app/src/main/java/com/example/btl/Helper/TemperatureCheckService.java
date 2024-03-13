package com.example.btl.Helper;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.example.btl.R;
import com.example.btl.models.common.Main;
import com.example.btl.models.currentWeather.CurrentWeatherResponse;
import com.example.btl.pages.MainActivity;
import com.example.btl.services.ApiClient;
import com.example.btl.services.ApiServices;
import com.example.btl.utils.Constants;

import java.text.DecimalFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class TemperatureCheckService extends Service {
    private static final String TAG = "TemperatureCheckService";
    private static final int NOTIFICATION_ID = 1; // Unique ID for the notification
    private static final String CHANNEL_ID = "channel"; // Unique ID for the notification channel
    private static final int REQUEST_CODE = 12345; // Define your request code here

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @SuppressLint("ForegroundServiceType")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Perform temperature check and show notification
        makeAPICall(getApplicationContext());

        // Create the notification channel
        createNotificationChannel();

        // Create and show the notification
        Notification notification = createNotification("Checking temperature...");
        startForeground(NOTIFICATION_ID, notification);

        // Schedule next execution after a certain interval
        scheduleTemperatureCheck(getApplicationContext());

        // Return START_STICKY to ensure the service restarts if it gets killed by the system
        return START_STICKY;
    }

    // Create a notification for the service
    private Notification createNotification(String contentText) {
        // Create an intent to open the main activity when notification is tapped
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Build the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Temperature")
                .setContentText(contentText)
                .setSmallIcon(R.drawable.baseline_anchor_24)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true); // Automatically cancel the notification when clicked

        // Return the notification
        return builder.build();
    }

    // Create the notification channel
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            channel.enableVibration(false);
            channel.enableLights(false);
            channel.setSound(null, null);
            channel.setShowBadge(false); // Hide badge count number when startForeground()
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    // Method to schedule the temperature check every hour
    public void scheduleTemperatureCheck(Context context) {
        // Set up the alarm to trigger every hour
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, TemperatureCheckService.class);
        PendingIntent pendingIntent = PendingIntent.getService(context, REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Set the alarm to trigger every hour
        long intervalMillis = 60 * 60 * 1000; // 1 hour in milliseconds
        long triggerAtMillis = System.currentTimeMillis();
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, triggerAtMillis, intervalMillis, pendingIntent);

        makeAPICall(context);
    }

    public static String getCityName(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString("cityName", "");
    }

    public void makeAPICall(final Context context) {
        // Initialize SharedPreferences
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String nameCity = "Thai Nguyen";
        if (getCityName(context) != null && !getCityName(context).isEmpty()) {
            nameCity = getCityName(context);
        }

        // Initialize Retrofit
        Retrofit retrofit = ApiClient.getClient();
        ApiServices apiServices = retrofit.create(ApiServices.class);

        Call<CurrentWeatherResponse> call = apiServices.getCurrentWeather(nameCity, Constants.API_KEY);
        call.enqueue(new Callback<CurrentWeatherResponse>() {
            @Override
            public void onResponse(Call<CurrentWeatherResponse> call, Response<CurrentWeatherResponse> response) {
                if (response.isSuccessful()) {
                    CurrentWeatherResponse currentWeatherResponse = response.body();
                    if (currentWeatherResponse != null) {
                        Main main = currentWeatherResponse.getMain();
                        if (main != null) {
                            double tempKelvin = main.getTemp();
                            double tempCelsius = tempKelvin - 273.15; // Convert from Kelvin to Celsius

                            DecimalFormat df = new DecimalFormat("#.00");
                            String formateTempCelsiusMain = df.format(tempCelsius);

//                            if (tempCelsius > 25) {
                                // Display notification with temperature
                                NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                                notificationManager.notify(NOTIFICATION_ID, createNotification("Temperature is " + formateTempCelsiusMain + "°C"));
//                            }
                        }
                    }
                } else {
                    Log.e("TemperatureCheck", "Failed to get weather data. Error code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<CurrentWeatherResponse> call, Throwable t) {
                Log.e("TemperatureCheck", "Failed to get weather data. Error: " + t.getMessage());
            }
        });
    }
}
