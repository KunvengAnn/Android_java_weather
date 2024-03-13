package com.example.btl.models.daysWeather;

import com.example.btl.models.Clouds;
import com.example.btl.models.common.Main;
import com.example.btl.models.common.Sys;
import com.example.btl.models.common.WeatherItem;
import com.example.btl.models.common.Wind;
import com.google.gson.annotations.SerializedName;

/*
   "list": [
       {
           "dt": 1709283600,
           "main": {
               "temp": 288.26,
               "feels_like": 287.45,
               "temp_min": 286.73,
               "temp_max": 288.26,
               "pressure": 1021,
               "sea_level": 1021,
               "grnd_level": 1017,
               "humidity": 62,
               "temp_kf": 1.53
           },
           "weather": [
               {
                   "id": 804,
                   "main": "Clouds",
                   "description": "overcast clouds",
                   "icon": "04d"
               }
           ],
           "clouds": {
               "all": 100
           },
           "wind": {
               "speed": 2.88,
               "deg": 32,
               "gust": 4.54
           },
           "visibility": 10000,
           "pop": 0,
           "sys": {
               "pod": "d"
           },
           "dt_txt": "2024-03-01 09:00:00"
       },
    */
public class ListItemResponse {

    @SerializedName("dt")
    private long dt;

    @SerializedName("main")
    private MainDay main;

    @SerializedName("weather")
    private WeatherItem[] weatherItems;

    @SerializedName("clouds")
    private Clouds clouds;

    @SerializedName("wind")
    private Wind wind;

    @SerializedName("visibility")
    private int visibility;

    @SerializedName("pop")
    private double pop;

    @SerializedName("sys")
    private Sys sys;

    @SerializedName("dt_txt")
    private String dateTimeText;

    // Getters and setters
    public long getDt() {
        return dt;
    }

    public void setDt(long dt) {
        this.dt = dt;
    }

    public MainDay getMain() {
        return this.main;
    }

    public void setMain(MainDay main) {
        this.main = main;
    }

    public WeatherItem[] getWeatherItems() {
        return weatherItems;
    }

    public void setWeatherItems(WeatherItem[] weatherItems) {
        this.weatherItems = weatherItems;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public int getVisibility() {
        return visibility;
    }

    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }

    public double getPop() {
        return pop;
    }

    public void setPop(int pop) {
        this.pop = pop;
    }

    public Sys getSys() {
        return sys;
    }

    public void setSys(Sys sys) {
        this.sys = sys;
    }

    public String getDateTimeText() {
        return dateTimeText;
    }

    public void setDateTimeText(String dateTimeText) {
        this.dateTimeText = dateTimeText;
    }
}
