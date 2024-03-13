package com.example.btl.models.daysWeather;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/*
{
    "cod": "200",
    "message": 0,
    "cnt": 7,
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

public class WeatherDayResponse {
    @SerializedName("cod")
    private int cod;

    @SerializedName("message")
    private String message;

    @SerializedName("cnt")
    private int cnt;

    @SerializedName("list")
    private List<ListItemResponse> list;

    // all getters and setters

    public void setCod(int cod) {
        this.cod = cod;
    }

    public int getCod() {
        return cod;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    public int getCnt() {
        return cnt;
    }

    public void setList(List<ListItemResponse> list) {
        this.list = list;
    }

    public List<ListItemResponse> getListItemResponse() {
        return list;
    }
}