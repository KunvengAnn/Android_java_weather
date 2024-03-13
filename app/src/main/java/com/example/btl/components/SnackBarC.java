package com.example.btl.components;

import android.graphics.Color;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

public class SnackBarC {

    // View when call you can pass
    //        View rootView = findViewById(android.R.id.content);
    public static void showSnackbar(View view, String message, int duration) {
        View snackbarView;
        Snackbar snackbar = Snackbar.make(view, message, duration);
        snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(Color.parseColor("#4CAF50")); // Set background color
        snackbar.setActionTextColor(Color.WHITE); // Set action text color
        snackbar.show();
    }

    public static void showSnackbarWithAction(View view, String message, String actionText, View.OnClickListener listener) {
        View snackbarView;
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_INDEFINITE);
        snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(Color.parseColor("#4CAF50")); // Set background color
        snackbar.setActionTextColor(Color.WHITE); // Set action text color
        snackbar.setAction(actionText, listener); // Set action button
        snackbar.show();
    }
}
