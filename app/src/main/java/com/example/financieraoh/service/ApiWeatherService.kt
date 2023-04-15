package com.example.financieraoh.service

import com.example.financieraoh.domain.model.CurrentWeather
import com.example.financieraoh.domain.model.DailyForecast
import com.example.financieraoh.domain.model.HourlyForecast
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryName

interface ApiWeatherService {

    @GET("current")
    fun getCurrentWeather(@Query("lat") latitude: Double, @Query("lon") longitude: Double, @Query("include") include: String = "minutely", @Query("lang") lang: String = "es") : Call<CurrentWeather>

    @GET("forecast/hourly")
    fun getForecastHourly(@Query("lat") latitude: Double, @Query("lon") longitude: Double, @Query("hours") hours: String = "24", @Query("lang") lang: String = "es") : Call<HourlyForecast>

    @GET("forecast/daily")
    fun getForecastDaily(@Query("lat") latitude: Double, @Query("lon") longitude: Double, @Query("days") days: String = "16", @Query("lang") lang: String = "es") : Call<DailyForecast>
}