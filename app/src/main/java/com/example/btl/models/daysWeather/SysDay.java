package com.example.btl.models.daysWeather;

import com.google.gson.annotations.SerializedName;

public class SysDay {
    /*
     "sys": {
                "pod": "d"
            },
     */
    @SerializedName("pod")
    private String pod;

    // getter and setter
    public void setPod(String pod){
        this.pod = pod;
    }

    public String getPod(){
        return this.pod;
    }
}
