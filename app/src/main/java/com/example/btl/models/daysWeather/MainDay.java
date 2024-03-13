package com.example.btl.models.daysWeather;

import com.google.gson.annotations.SerializedName;

public class MainDay {
    /*
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

    @SerializedName("sea_level")
    private int seaLevel;

    @SerializedName("grnd_level")
    private int groundLevel;

    @SerializedName("humidity")
    private int humidity;

    @SerializedName("temp_kf")
    private double tempKf;

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

    public int getSeaLevel() {
        return seaLevel;
    }

    public void setSeaLevel(int seaLevel) {
        this.seaLevel = seaLevel;
    }

    public int getGroundLevel() {
        return groundLevel;
    }

    public void setGroundLevel(int groundLevel) {
        this.groundLevel = groundLevel;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public double getTempKf() {
        return tempKf;
    }

    public void setTempKf(double tempKf) {
        this.tempKf = tempKf;
    }
}

