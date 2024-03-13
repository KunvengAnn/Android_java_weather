package com.example.btl.services;


//using an interface with Retrofit allows you to define the structure of API requests and responses
// in a clear and abstract manner, enabling easy integration with Retrofit and providing flexibility
// and separation of concerns in your codebase.

import com.example.btl.models.currentWeather.CurrentWeatherResponse;
import com.example.btl.models.daysWeather.WeatherDayResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiServices {
    /**
     * Get current weather data for a specific city.
     *
     * @param city   Name of the city for which to retrieve weather data.
     * @param apiKey API key for accessing the weather API.
     * @return An instance of {@link CurrentWeatherResponse} containing the current weather information for the specified city.
     * link http://api.openweathermap.org/data/2.5/weather?q=Thai nguyen&appid=67ddd13dc6d82ac3921a85d5ab39ecc2
     */
    @GET("weather")
    Call<CurrentWeatherResponse> getCurrentWeather(
            @Query("q") String city,
            @Query("appid") String apiKey
    );

    //https://api.openweathermap.org/data/2.5/forecast?lat=21.5928&lon=105.8442&cnt=42&appid=67ddd13dc6d82ac3921a85d5ab39ecc2
    // this link it response json in hourly 6 day for cnt 42
    @GET("forecast")
    Call<WeatherDayResponse> getWeatherDay(
            @Query("lat") double latitude,
            @Query("lon") double longitude,
            @Query("cnt") int count,
            @Query("appid") String apiKey
    );
}

