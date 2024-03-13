// get the will retrieve forecast data for the next 8 intervals, where each interval is typically 3 hours apart.
// So, it provides a forecast for approximately the next 24 hours,
// https://api.openweathermap.org/data/2.5/forecast?lat=21.5928&lon=105.8442&cnt=7&appid=67ddd13dc6d82ac3921a85d5ab39ecc2


/*By default, this endpoint provides forecast data for intervals typically 3 hours apart for the next 5 days (approximately). Each forecast
 interval includes information such as temperature, humidity, wind speed, weather condition, and more.
    https://api.openweathermap.org/data/2.5/forecast?lat=21.5928&lon=105.8442&appid=67ddd13dc6d82ac3921a85d5ab39ecc2
*/

package com.example.btl.pages;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;  // Import the correct Toolbar class

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl.Helper.NotificationHelper;
import com.example.btl.Helper.NotificationReceiver;
import com.example.btl.Helper.TemperatureCheckService;
import com.example.btl.R;
import com.example.btl.components.AdapterRecycler;
import com.example.btl.components.SnackBarC;
import com.example.btl.models.common.Coord;
import com.example.btl.models.common.Main;
import com.example.btl.models.common.WeatherItem;
import com.example.btl.models.common.Wind;
import com.example.btl.models.currentWeather.CurrentWeatherResponse;
import com.example.btl.models.daysWeather.ListItemResponse;
import com.example.btl.models.daysWeather.WeatherDayResponse;
import com.example.btl.pages.CustomDarkShow.CustomForDark;
import com.example.btl.services.ApiClient;
import com.example.btl.services.ApiServices;
import com.example.btl.services.InternetWifiConnectCheck.ConnectivityReceiver;
import com.example.btl.utils.Constants;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    
    ApiServices apiServices;
    String TADWeather = "Weather";
    private ConnectivityReceiver connectivityReceiver;

    // Declare root view Global of the layout for show snackBar
    View rootView;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    NavigationView navigationView;

    private Toolbar toolbar;  // Declare the Toolbar variable
    private String cityNameG = "";

    EditText editText_cityName;
    TextView textView_cityName;
    TextView textView_ForecastMain;
    TextView textView_HumidityMain;
    TextView textView_WindMain;
    TextView textView_DescirptionMain;

    //all below for recyclerView
    // Recycler View object
    RecyclerView recyclerView;

    // Array list for recycler view data source
    ArrayList<String> source;

    // Layout Manager
    RecyclerView.LayoutManager RecyclerViewLayoutManager;

    private SharedPreferences sharedPreferences;
    private boolean darkModeSwitchToggled = false;


    // Initialize ArrayLists for strings, drawable resource IDs, and temperature strings
    List<String> stringList = new ArrayList<>();
    List<Integer> drawableList = new ArrayList<>();
    List<String> tempList = new ArrayList<>();
    List<String> tempListMix = new ArrayList<>();
    List<String> tempListMin = new ArrayList<>();
    List<String> listHour = new ArrayList<>();

    String savedLanguage = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Start the service
        Intent serviceIntent = new Intent(this, TemperatureCheckService.class);
        startService(serviceIntent);


        // Instantiate the ConnectivityReceiver with the current MainActivity instance
        connectivityReceiver = new ConnectivityReceiver(this);

        // Register the connectivity receiver
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(connectivityReceiver, filter);

        // Initialize rootView after setContentView()
        rootView = findViewById(android.R.id.content);

        toolbar = findViewById(R.id.toolbar);  // Initialize the Toolbar variable
        setSupportActionBar(toolbar);

        // Hide the default title (app name)
        getSupportActionBar().setTitle("");

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,  // Pass your Toolbar instance here
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        );

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.bringToFront();


        // Initialize Retrofit
        Retrofit retrofit = ApiClient.getClient();

        // Create an instance of the ApiServices
        apiServices = retrofit.create(ApiServices.class);

        // Example usage: get weather for a city
        String apiKey = Constants.API_KEY;
        String nameCityGet = getCityName(MainActivity.this);
        Log.d("test", "Retrieved city name: " + nameCityGet);
        if (nameCityGet == null || nameCityGet.isEmpty()) {
            cityNameG = "Thai Nguyen";
            Log.d(TADWeather, "Setting default city name: " + cityNameG);
        } else {
            cityNameG = nameCityGet;
            Log.d(TADWeather, "Using retrieved city name: " + cityNameG);
        }


        // Make the API call
        Call<CurrentWeatherResponse> call = apiServices.getCurrentWeather(cityNameG, apiKey);
        call.enqueue(new Callback<CurrentWeatherResponse>() {
            @Override
            public void onResponse(Call<CurrentWeatherResponse> call, Response<CurrentWeatherResponse> response) {
                if (response.isSuccessful()) {
                    CurrentWeatherResponse currentWeatherResponse = response.body();
                    // Handle the weather data received
                    if (currentWeatherResponse != null) {
                        Main main = currentWeatherResponse.getMain();
                        Coord coord = currentWeatherResponse.getCoord();
                        List<WeatherItem> listWeatherItemRes = currentWeatherResponse.getWeather();

                        fetchWeatherDataDay(coord.getLat(), coord.getLon());

                        if (main != null) {
                            double tempKelvin = main.getTemp();
                            // to Celsius
                            double tempCelsius = tempKelvin - 273;

                            Log.d(TADWeather, "Temperature: " + tempCelsius + "°C");

                            DecimalFormat df = new DecimalFormat("#.00");
                            String formateTempCelsiusMain = df.format(tempCelsius);
                            String textTempCelsius = formateTempCelsiusMain + "°C";
                            textView_ForecastMain.setText(textTempCelsius);
                            String descriptionMain = null;
                            if (listWeatherItemRes != null) {
                                for (WeatherItem item : listWeatherItemRes) {
                                    descriptionMain = item.getDescription();
                                }
                            }
                            double windSpeedMain = currentWeatherResponse.getWind().getSpeed();
                            double humidityMain = main.getHumidity();

                            setWeatherInfo(tempCelsius, descriptionMain, windSpeedMain, humidityMain);

                            // other
                            Log.d(TADWeather, "Feels Like: " + (main.getFeelsLike() - 273) + "°C");
                        }
                    }
                } else {
                    // Handle API error
                    Log.e(TADWeather, "Failed to get weather data. Error code: " + response.code());
                    SnackBarC.showSnackbar(rootView, "Failed to Get data Error code: " + response.code(), Snackbar.LENGTH_LONG);
                }
            }

            @Override
            public void onFailure(Call<CurrentWeatherResponse> call, Throwable t) {
                Log.e(TADWeather, "Failed to fetch weather network errors: " + t.getMessage());
                SnackBarC.showSnackbar(rootView, "Failed to fetch weather network errors: " + t.getMessage(), Snackbar.LENGTH_LONG);
            }
        });

        //init
        editText_cityName = findViewById(R.id.id_searchEditText);
        searchEditTextPressEnter();

        textView_cityName = findViewById(R.id.id_cityName);
        textView_cityName.setText(cityNameG);

        //all
        textView_ForecastMain = findViewById(R.id.id_tempForecastMain);
        textView_DescirptionMain = findViewById(R.id.id_forecastDecription);
        textView_HumidityMain = findViewById(R.id.id_textViewHumidityNumber);
        textView_WindMain = findViewById(R.id.id_textViewWindNumber);


        // Initialize SharedPreferences
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        // Call the method to customize for dark mode
        CustomForDark.customizeEditTextForDarkMode(this, editText_cityName, toolbar, navigationView);
        CustomForDark.customizeAllTextviewColorWhite(this, getWindow().getDecorView().getRootView());

        // Inflate the drawer menu layout
        NavigationView navigationView = findViewById(R.id.nav_view);

        // Get the menu from navigation view
        Menu menu = navigationView.getMenu();

        // Find the menu item containing the SwitchCompat view
        MenuItem menuItem = menu.findItem(R.id.nav_item2);

        // Get the action view for the menu item (which contains the SwitchCompat)
        View actionView = menuItem.getActionView();

        // Find the SwitchCompat view within the action view
        SwitchCompat switchDarkMode = actionView.findViewById(R.id.id_switchDarkMode);
        // Retrieve dark mode state from SharedPreferences
        boolean isDarkMode = getDarkModePreference();
        applyTheme(isDarkMode);

        switchDarkMode.setChecked(isDarkMode);

        // Set up dark mode switch listener
        switchDarkMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            darkModeSwitchToggled = true;

            saveDarkModePreference(isChecked);
            applyTheme(isChecked);
        });

        TextView textViewNext6Day = findViewById(R.id.id_textviewNext6Day);
        TextView textViewHumidity = findViewById(R.id.id_Humidity);
        TextView textViewWind = findViewById(R.id.id_windMain);
        navigationView = findViewById(R.id.nav_view);
        menu = navigationView.getMenu();
        MenuItem item1 = menu.findItem(R.id.nav_item1);
        MenuItem itemThemeMode = menu.findItem(R.id.nav_item2);
        MenuItem itemChangeLang = menu.findItem(R.id.nav_changeLanguages);
        // Find the header view within the NavigationView
        View headerView = navigationView.getHeaderView(0);

        // Find the TextView within the header view
        TextView headerTextViewDrawer  = headerView.findViewById(R.id.id_headerDrawerNavigationView);
        TextView textViewNameToolBar = findViewById(R.id.id_nameOfToolBar);

        // Get the saved language
        savedLanguage = getSelectedLanguage();

        Translate translate = new Translate(rootView, this, R.id.id_searchEditText,textViewNext6Day,textViewHumidity,textViewWind,item1,itemThemeMode,itemChangeLang,headerTextViewDrawer,textViewNameToolBar);
        translate.getLanguage();

        // from Language Page
        boolean isSelectedFromLangPage = getIntent().getBooleanExtra("isSelected", false);
        if (isSelectedFromLangPage) {
            if(savedLanguage.equals("vi")){
                SnackBarC.showSnackbar(rootView, "Vietnamese", Snackbar.LENGTH_LONG);
            }else{
                SnackBarC.showSnackbar(rootView, "English", Snackbar.LENGTH_LONG);
            }
        }

        // Initialize RecyclerView instance
        recyclerView = findViewById(R.id.id_recyclerview);
    }
    // end OnCreate
    private void ShowRecycler() {
        // Create adapter instance
        AdapterRecycler adapter = new AdapterRecycler(stringList, drawableList, tempList, listHour, tempListMix, tempListMin);

        // Set LinearLayoutManager for RecyclerView
        RecyclerViewLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(RecyclerViewLayoutManager);

        // Set adapter to RecyclerView
        recyclerView.setAdapter(adapter);
    }

    private String getVietnameseWeatherDescription(String englishDescription) {
        String[] lsEnglishDescriptions = Constants.WEATHER_STATUS_EN;
        String[] lsVietnameseDescriptions = Constants.WEATHER_STATUS_VI;

        for (int i = 0; i < lsEnglishDescriptions.length; i++) {
            if (lsEnglishDescriptions[i].equals(englishDescription)) {
                return lsVietnameseDescriptions[i];
            }
        }

        // If no matching description found, return the original English description
        return englishDescription;
    }

    private void setWeatherInfo(double temperature, String description, double windSpeed, double humidity) {
        DecimalFormat df = new DecimalFormat("#.00");

        // Format temperature
        String formattedTemperature = df.format(temperature) + "°C";
        textView_ForecastMain.setText(formattedTemperature);

        // get language from sharedPreferences
        savedLanguage = getSelectedLanguage();
        if(savedLanguage.equals("vi")){
            String vietnameseDescription = getVietnameseWeatherDescription(description);
            // Set weather description
            textView_DescirptionMain.setText(vietnameseDescription);
        }else if(savedLanguage.equals("en")){
            // set with english
            textView_DescirptionMain.setText(description);
        }



        // Format and set wind speed
        String formattedWindSpeed = ": " + df.format(windSpeed) + " Km/h";
        textView_WindMain.setText(formattedWindSpeed);

        // Format and set humidity
        String formattedHumidity = ": " + df.format(humidity) + "%";
        textView_HumidityMain.setText(formattedHumidity);
    }

    // Method to save the city name to SharedPreferences
    public void saveCityName(Context context, String cityName) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("cityName", cityName);
        editor.apply();
    }

    // Method to get the saved city name from SharedPreferences
    public String getCityName(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString("cityName", "");
    }
    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        int itemId = menuItem.getItemId();
        if (itemId == R.id.nav_item1) {
            SnackBarC.showSnackbar(rootView, "I don't know", Snackbar.LENGTH_LONG);
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        } else if (itemId == R.id.nav_changeLanguages) {
            Intent intent = new Intent(MainActivity.this, LanguagePage.class);
            startActivity(intent);
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    private void applyTheme(boolean isDarkMode) {
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    private void saveDarkModePreference(boolean isDarkMode) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("dark_mode", isDarkMode);
        editor.apply();
    }

    private String getSelectedLanguage() {
        String savedLanguage = sharedPreferences.getString("language", null);

        // If savedLanguage is null, set it to "en"
        if (savedLanguage == null) {
            savedLanguage = "en";
        }
        return savedLanguage;
    }

    private boolean getDarkModePreference() {
        return sharedPreferences.getBoolean("dark_mode", false); // Default to false if preference is not found
    }

    private void fetchWeatherDataDay(double latitude, double longitude) {
        // Call the API method
        Call<WeatherDayResponse> call = apiServices.getWeatherDay(latitude, latitude, 42, Constants.API_KEY);

        // Enqueue the API call
        call.enqueue(new Callback<WeatherDayResponse>() {
            @Override
            public void onResponse(Call<WeatherDayResponse> call, Response<WeatherDayResponse> response) {
                if (response.isSuccessful()) {
                    WeatherDayResponse weatherDayResponse = response.body();
                    if (weatherDayResponse != null) {
                        List<ListItemResponse> ListitemResponse = weatherDayResponse.getListItemResponse();

                        // Clear stringList before adding new items
                        stringList.clear();
                        tempList.clear();
                        for (ListItemResponse item : ListitemResponse) {
                            double tempKelvin = item.getMain().getTemp();
                            item.getDateTimeText();
                            String dateStr = item.getDateTimeText();
                            String[] dateSpit = dateStr.split(" ");
                            String hour = dateSpit[1];

                            double tempCelsius = tempKelvin - 273;
                            DecimalFormat df = new DecimalFormat("#.00");
                            String formattedTemp = df.format(tempCelsius);
                            String dayOfWeek = getDayOfWeek(dateSpit[0]);
                            //Log.d(TADWeather, "date: " + dateSpit[0]);
                            Log.d(TADWeather, "date: " + dayOfWeek);
                            Log.d(TADWeather, "hour: " + hour);

                            // Format the hour to 12-hour format with AM or PM
                            SimpleDateFormat sdf24 = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
                            SimpleDateFormat sdf12 = new SimpleDateFormat("hh:mm a", Locale.getDefault());
                            Date time = null;
                            try {
                                time = sdf24.parse(dateSpit[1]);
                            } catch (ParseException e) {
                                throw new RuntimeException(e);
                            }
                            String formattedHour = sdf12.format(time);

                            double tempKelvinMax = item.getMain().getTempMax();
                            double tempMaxCelcius = tempKelvinMax - 273;
                            String formattedTempMAX = df.format(tempMaxCelcius);

                            double tempKelvinMin = item.getMain().getTempMin();
                            double tempMinCelcius = tempKelvinMin - 273;
                            String formattedTempMIN = df.format(tempMinCelcius);

                            tempListMin.add(formattedTempMIN);
                            tempListMix.add(formattedTempMAX);

                            listHour.add(formattedHour);

                            stringList.add(dayOfWeek);
                            drawableList.add(R.drawable.cloud);

                            tempList.add(formattedTemp);
                            Log.d(TADWeather, "templist: " + tempList);

                        }
                        ShowRecycler();
                    }
                } else {
                    Log.e("Weather", "Failed to fetch weather data. Error code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<WeatherDayResponse> call, Throwable t) {
                // Handle network errors
                Log.e("Weather", "Failed to fetch weather data. Error: " + t.getMessage());
            }
        });
    }

    private String getDayOfWeek(String dateString) {
        try {
            // Parse the date string into a Date object 2024-03-02
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            Date date = sdf.parse(dateString);

            // Use Calendar to get the day of the week
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

            String[] daysOfWeek = null;

            //check something for change
            if (savedLanguage.equals("vi")){
                 daysOfWeek = Constants.DAYS_OF_WEEK_VIETNAMESE;
            }else{
                // Convert day of week to a string representation
                daysOfWeek = Constants.DAYS_OF_WEEK;
            }
            return daysOfWeek[dayOfWeek - 1]; // Adjust for 0-based index
        } catch (ParseException e) {
            e.printStackTrace();
            return "Invalid Date";
        }
    }

    private void searchEditTextPressEnter() {
        editText_cityName.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (isActionDone(actionId, event) || isActionSearch(actionId, event) || isActionNext(actionId, event)) {
                    // Handle the "Done", "Search", or "Next" action
                    handleSearchAction();
                    editText_cityName.clearFocus();
                    // Close the keyboard
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(editText_cityName.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });

        // Handle the Enter key press event when the action is not handled by the EditorActionListener
        editText_cityName.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN && (keyCode == KeyEvent.KEYCODE_ENTER || keyCode == KeyEvent.KEYCODE_NUMPAD_ENTER)) {
                    // Handle the Enter key press event
                    handleSearchAction();
                    // Close the keyboard
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(editText_cityName.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });
    }
    @Override
    public void onBackPressed() {
        if (editText_cityName.isFocused()) {
            // Clear focus
            editText_cityName.clearFocus();

            // Close the keyboard
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(editText_cityName.getWindowToken(), 0);
        } else {
            super.onBackPressed();
        }
    }

    // for keyboard press enter
    private boolean isActionDone(int actionId, KeyEvent event) {
        return actionId == EditorInfo.IME_ACTION_DONE
                || (event != null && event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_ENTER);
    }

    // for keyboard press enter
    private boolean isActionSearch(int actionId, KeyEvent event) {
        return actionId == EditorInfo.IME_ACTION_SEARCH
                || (event != null && event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_ENTER);
    }

    // for keyboard press enter
    private boolean isActionNext(int actionId, KeyEvent event) {
        return actionId == EditorInfo.IME_ACTION_NEXT
                || (event != null && event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_ENTER);
    }


    private void handleSearchAction() {
        // Get the city name from the EditText Search
        String cityName = editText_cityName.getText().toString();

        if (cityName != null) {
            String apiKey = Constants.API_KEY;
            Call<CurrentWeatherResponse> call = apiServices.getCurrentWeather(cityName, apiKey);

            // Enqueue the API call
            call.enqueue(new Callback<CurrentWeatherResponse>() {
                @Override
                public void onResponse(Call<CurrentWeatherResponse> call, Response<CurrentWeatherResponse> response) {
                    if (response.isSuccessful()) {
                        // request success change name city on UI
                        textView_cityName.setText(cityName);

                        // save SharedPreferences
                        saveCityName(MainActivity.this, cityName);

                        CurrentWeatherResponse currentWeatherResponse = response.body();
                        // Handle the weather data received
                        if (currentWeatherResponse != null) {
                            Main main = currentWeatherResponse.getMain();
                            Wind windRes = currentWeatherResponse.getWind();
                            List<WeatherItem> lsResWeatherItems = currentWeatherResponse.getWeather();
                            Coord coord = currentWeatherResponse.getCoord();

                            if (main != null) {
                                double tempKelvin = main.getTemp();
                                // Convert temperature to Celsius
                                double tempCelsius = tempKelvin - 273;
                                Log.d(TADWeather, "Temperature: " + cityName + tempCelsius + "°C");

                                // Log other weather data
                                Log.d(TADWeather, "Feels Like: " + (main.getFeelsLike() - 273) + "°C");

                                String descriptionRes = null;
                                for (WeatherItem item : lsResWeatherItems) {
                                    descriptionRes = item.getDescription();
                                }

                                setWeatherInfo(tempCelsius, descriptionRes, windRes.getSpeed(), main.getHumidity());

                                fetchWeatherDataDay(coord.getLat(), coord.getLon());
                            }
                        }
                    } else {
                        // Handle API error
                        Log.e(TADWeather, "Failed to get weather data. Error code: " + response.code());
                        SnackBarC.showSnackbar(rootView, "Failed to get weather Please Correct City Name. Error code: " + response.code(), Snackbar.LENGTH_LONG);
                    }
                }

                @Override
                public void onFailure(Call<CurrentWeatherResponse> call, Throwable t) {
                    // Handle network errors
                    Log.e("Weather", "Failed to fetch weather data due to network errors: " + t.getMessage());
                    SnackBarC.showSnackbar(rootView, "Failed to fetch weather data due to network errors: " + t.getMessage(), Snackbar.LENGTH_LONG);
                }
            });
        }

        // Log the city name
        Log.d(TADWeather, "City: " + cityName);
        editText_cityName.setText("");
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Check for internet and Wi-Fi connection when the activity comes into the foreground
        if (!darkModeSwitchToggled) {
            checkInternetConnection();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!darkModeSwitchToggled) {
            checkInternetConnection();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Reset the dark mode switch toggled flag when the activity goes into the background
        darkModeSwitchToggled = false;

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Unregister the connectivity receiver when the app is destroyed
        if (connectivityReceiver != null) {
            unregisterReceiver(connectivityReceiver);
        }

    }

    public void checkInternetConnection() {
        // Check for internet OR Wi-Fi connection
        if (!isConnectedToInternet() && !isWifiConnected()) {
            // No internet or Wi-Fi connection
            SnackBarC.showSnackbar(rootView, "internet connection fails!!", Snackbar.LENGTH_LONG);
        }
    }

    //check connect internet
    private boolean isConnectedToInternet() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager != null) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }

        return false;
    }


    //check connect wifi
    private boolean isWifiConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager != null) {
            NetworkInfo wifiNetworkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            return wifiNetworkInfo != null && wifiNetworkInfo.isConnected();
        }

        return false;
    }
}