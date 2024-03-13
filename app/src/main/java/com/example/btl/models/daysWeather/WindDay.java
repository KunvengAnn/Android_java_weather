package com.example.btl.models.daysWeather;

import com.google.gson.annotations.SerializedName;

public class WindDay {
    /*
                "wind": {
                "speed": 2.88,
                "deg": 32,
                "gust": 4.54
            },
     */

    @SerializedName("speed")
    private double speed;

    @SerializedName("deg")
    private int deg;

    @SerializedName("gust")
    private double gust;

    // Getters and setters
    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public int getDeg() {
        return deg;
    }

    public void setDeg(int deg) {
        this.deg = deg;
    }

    public double getGust() {
        return gust;
    }

    public void setGust(double gust) {
        this.gust = gust;
    }
}
