package com.example.btl.models.common;

import com.google.gson.annotations.SerializedName;

public class Main {
    /*
    "main": {
        "temp": 280.97,
        "feels_like": 278.99,
        "temp_min": 279.94,
        "temp_max": 282.38,
        "pressure": 1019,
        "humidity": 71
    },
    */

    @SerializedName("temp")
    private double temp;

    @SerializedName("feels_like")
    private double feelsLike;

    @SerializedName("temp_min")
    private double tempMin;

    @SerializedName("temp_max")
    private double tempMax;

    @SerializedName("pressure")
    private int pressure;

    @SerializedName("humidity")
    private int humidity;

    // Getters and setters

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public double getFeelsLike() {
        return feelsLike;
    }

    public void setFeelsLike(double feelsLike) {
        this.feelsLike = feelsLike;
    }

    public double getTempMin() {
        return tempMin;
    }

    public void setTempMin(double tempMin) {
        this.tempMin = tempMin;
    }

    public double getTempMax() {
        return tempMax;
    }

    public void setTempMax(double tempMax) {
        this.tempMax = tempMax;
    }

    public int getPressure() {
        return pressure;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }
}
