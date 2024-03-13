package com.example.btl.models;

import com.google.gson.annotations.SerializedName;

public class Clouds {

    /*
    "clouds": {
        "all": 100
    },
     */
    @SerializedName("all")
    private  int all;
    public int getAll() { return all; }

    public void setAll(int all){ this.all = all; }
}
