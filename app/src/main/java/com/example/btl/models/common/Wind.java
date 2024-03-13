package com.example.btl.models.common;

import com.google.gson.annotations.SerializedName;

public class Wind {
    // wind: ខ្យល់
    @SerializedName("deg")
    private  double deg;

    @SerializedName("speed")
    private double speed;

    // all setter getter
    public void setDeg(double deg){ this.deg= deg; }

    public double getDeg(){ return deg; }

    public void setSpeed(double speed){ this.speed=speed; }

    public double getSpeed(){ return speed; }

}
