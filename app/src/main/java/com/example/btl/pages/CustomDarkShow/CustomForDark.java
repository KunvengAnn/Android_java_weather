package com.example.btl.pages.CustomDarkShow;

import android.content.Context;
import android.content.res.Configuration;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import com.example.btl.R;
import com.google.android.material.navigation.NavigationView;

public class CustomForDark {

    public static void customizeEditTextForDarkMode(Context context, EditText editText, Toolbar toolbar, NavigationView navView) {
        int nightModeFlags = context.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        if (nightModeFlags == Configuration.UI_MODE_NIGHT_YES) {
            // Dark mode
            editText.setBackgroundResource(R.color.darkColorPrimaryDark);
            toolbar.setBackgroundResource(R.color.darkColorPrimary);
            navView.setBackgroundResource(R.color.darkColorPrimaryDark);

        }
    }

    public static void customizeAllTextviewColorWhite(Context context, View view) {
        int nightModeFlags = context.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        if (nightModeFlags == Configuration.UI_MODE_NIGHT_YES) {
            // Dark mode
            changeTextColor(view, context.getResources().getColor(android.R.color.white));
        }
    }

    // change color all textview to white
    private static void changeTextColor(View view, int color) {
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                View child = viewGroup.getChildAt(i);
                changeTextColor(child, color);
            }
        } else if (view instanceof TextView) {
            TextView textView = (TextView) view;
            textView.setTextColor(color);
        }
    }
}
