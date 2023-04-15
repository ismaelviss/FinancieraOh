package com.example.financieraoh.repositories

import com.example.financieraoh.domain.model.City
import com.example.financieraoh.domain.model.CurrentWeather
import com.example.financieraoh.domain.model.DailyForecast
import com.example.financieraoh.domain.model.HourlyForecast
import com.example.financieraoh.service.ApiWeatherService
import com.google.gson.Gson

class RepositoryWeather(private val apiWeatherService: ApiWeatherService) {

    fun getCurrentWeather(city: City) : CurrentWeather {
        val response = apiWeatherService.getCurrentWeather(city.latitude!!, city.longitude!!).execute()
        if (response.isSuccessful) {
            return response.body()!!
        } else {
            throw Exception("RepositoryWeather->Error al consumir servicio de ApiWeather->${response.code()}->${response.message()}->${Gson().toJson(response.errorBody())}")
        }
    }

    fun getForecastHourly(city: City) : HourlyForecast {
        val response = apiWeatherService.getForecastHourly(city.latitude!!, city.longitude!!).execute()
        if (response.isSuccessful) {
            return response.body()!!
        } else {
            throw Exception("RepositoryWeather->Error al consumir servicio de ApiWeather->${response.code()}->${response.message()}->${Gson().toJson(response.errorBody())}")
        }
    }

    fun getForecastDaily(city: City) : DailyForecast {
        val response = apiWeatherService.getForecastDaily(city.latitude!!, city.longitude!!).execute()
        if (response.isSuccessful) {
            return response.body()!!
        } else {
            throw Exception("RepositoryWeather->Error al consumir servicio de ApiWeather->${response.code()}->${response.message()}->${Gson().toJson(response.errorBody())}")
        }
    }
}