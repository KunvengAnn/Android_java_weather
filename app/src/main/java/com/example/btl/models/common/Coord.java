package com.example.btl.models.common;

import com.google.gson.annotations.SerializedName;

public class Coord {
    @SerializedName("lon")
    private double lon;

    @SerializedName("lat")
    private double lat;

    // all getter and setter
    public void setLon(double lon){ this.lon=lon; }

    public double getLon(){ return lon; }

    public void setLat(double lat){ this.lat=lat; }

    public double getLat() { return lat; }
}
