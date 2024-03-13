package com.example.btl.models.common;

import com.google.gson.annotations.SerializedName;

public class WeatherItem {
    /*
       "weather": [
        {
            "id": 804,
            "main": "Clouds",
            "description": "overcast clouds",
            "icon": "04d"
        }
    ],
     */
    @SerializedName("icon")
    private String icon;

    @SerializedName("description")
    private String description;

    @SerializedName("main")
    private String main;

    @SerializedName("id")
    private int id;

    // all getter and setter
    public void setIcon(String icon){ this.icon=icon; }

    public String getIcon(){ return icon; }

    public void setDescription(String description){ this.description=description; }

    public String getDescription(){ return description; }

    public void setMain(String main){ this.main=main; }

    public String getMain(){ return main; }

    public void setId(int id){ this.id=id; }

    public int getId(){ return id; }

}
