package com.example.btl.models.common;

import com.google.gson.annotations.SerializedName;

public class Sys {
    /*
    "sys": {
        "type": 2,
        "id": 2075535,
        "country": "GB",
        "sunrise": 1709016699,
        "sunset": 1709055324
    },
    */

    @SerializedName("type")
    private int type;

    @SerializedName("id")
    private int id;

    @SerializedName("country")
    private String country;

    @SerializedName("sunrise")
    private long sunrise;

    @SerializedName("sunset")
    private long sunset;

    // Getters and setters

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public long getSunrise() {
        return sunrise;
    }

    public void setSunrise(long sunrise) {
        this.sunrise = sunrise;
    }

    public long getSunset() {
        return sunset;
    }

    public void setSunset(long sunset) {
        this.sunset = sunset;
    }
}
