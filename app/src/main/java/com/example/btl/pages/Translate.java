package com.example.btl.pages;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.btl.utils.Constants;

public class Translate extends AppCompatActivity {

    private final View rootView;
    private final Context context;
    private final int searchEditTextId;
    private final TextView textViewNext6Day;
    private final TextView textViewHumidity;
    private final TextView textViewWind;
    private final MenuItem item1;
    private final MenuItem itemThemeMode;
    private final MenuItem itemChangeLang;
    private final TextView itemHeaderDrawer;
    private final TextView textviewNameToolBar;

    public Translate(View rootView, Context context, int searchEditTextId, TextView textViewNext6Day, TextView textViewHumidity, TextView textViewWind, MenuItem item1, MenuItem itemThemeMode, MenuItem itemChangeLang,TextView itemHeaderDrawer,TextView textviewNameToolBar) {
        this.rootView = rootView;
        this.context = context;
        this.searchEditTextId = searchEditTextId;
        this.textViewNext6Day = textViewNext6Day;
        this.textViewHumidity = textViewHumidity;
        this.textViewWind = textViewWind;
        this.item1 = item1;
        this.itemThemeMode = itemThemeMode;
        this.itemChangeLang = itemChangeLang;
        this.itemHeaderDrawer = itemHeaderDrawer;
        this.textviewNameToolBar = textviewNameToolBar;
    }

    public void getLanguage() {
        // Check if language was passed from LanguagePage
        String languageSaveIs = getSelectedLanguage();
        if(languageSaveIs !=null){
            setAppLanguage(languageSaveIs);
        }
    }

    private String getSelectedLanguage() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String savedLanguage = sharedPreferences.getString("language", null);

        // If savedLanguage is null, set it to "en"
        if (savedLanguage == null) {
            savedLanguage = "en";
        }
        return savedLanguage;
    }

    private void setAppLanguage(String language) {
        if ("vi".equals(language)) {
            EditText editTextSearch = rootView.findViewById(searchEditTextId);
            editTextSearch.setHint(Constants.searchEditTextVN);
            textviewNameToolBar.setText("Ứng dụng thời tiết");

            textViewNext6Day.setText("6 Ngày/giờ tiếp theo");
            textViewHumidity.setText("Độ ẩm");
            textViewWind.setText("Gió");
            item1.setTitle("Giới thiệu về nhà phát triển");
            itemHeaderDrawer.setText("Phần Chức năng Hệ Thống");
            itemThemeMode.setTitle("Chế độ chủ đề");
            itemChangeLang.setTitle("Thay đổi ngôn ngữ");
        } else if ("en".equals(language)) {
            EditText editTextSearch = rootView.findViewById(searchEditTextId);
            editTextSearch.setHint(Constants.getSearchEditTextEN);
        }
    }
}
