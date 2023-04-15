package com.example.financieraoh.domain

import com.example.financieraoh.domain.model.City
import com.example.financieraoh.domain.model.CurrentWeather
import com.example.financieraoh.domain.model.DailyForecast
import com.example.financieraoh.domain.model.HourlyForecast
import com.example.financieraoh.repositories.RepositoryCities
import com.example.financieraoh.repositories.RepositoryWeather

class DomainCity(private val repositoryCities: RepositoryCities, private val repositoryWeather: RepositoryWeather) {

    fun getCitiesFilter(name: String) : List<City> {

        if (name.trim().length < 2)
            throw IllegalArgumentException("El parametro para la busqueda de ciudad debe ser de mas de dos caracteres.")

        return repositoryCities.getCities(name.trim())
    }

    fun getCurrentWeatherCity(city: City) : CurrentWeather {
        return repositoryWeather.getCurrentWeather(city)
    }

    fun getForecastHourly(city: City) : HourlyForecast {
        return repositoryWeather.getForecastHourly(city)
    }

    fun getForecastDaily(city: City) : DailyForecast {
        return repositoryWeather.getForecastDaily(city)
    }
}