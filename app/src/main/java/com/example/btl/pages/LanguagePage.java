package com.example.btl.pages;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.btl.R;
import com.example.btl.components.SnackBarC;
import com.google.android.material.snackbar.Snackbar;

public class LanguagePage extends AppCompatActivity {
    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.languagepage);
        View rootView = findViewById(android.R.id.content);

        // Initialize SharedPreferences
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        LinearLayout vietnameseLayout = findViewById(R.id.id_vietnamesLang);
        LinearLayout englishLayout = findViewById(R.id.id_EnglishLang);

        vietnameseLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveLanguagePreference("vi");

                // Start MainActivity
                Intent intent = new Intent(LanguagePage.this, MainActivity.class);
                intent.putExtra("isSelected", true);
                startActivity(intent);
            }
        });

        englishLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveLanguagePreference("en");

                // Start MainActivity
                Intent intent = new Intent(LanguagePage.this, MainActivity.class);
                intent.putExtra("isSelected", true);
                startActivity(intent);
            }
        });
    }

    private void saveLanguagePreference(String language) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("language", language);
        editor.apply();
    }

}

