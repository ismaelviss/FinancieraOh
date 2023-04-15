package com.example.financieraoh.repositories

import android.content.Context
import android.util.Log
import com.example.financieraoh.domain.model.City
import com.example.financieraoh.service.ApiCityService
import com.example.financieraoh.service.ApiWeatherService
import com.google.gson.Gson
import javax.inject.Singleton

@Singleton
class RepositoryCities(private val apiCityService: ApiCityService) {

    @Synchronized
    fun getCities(name: String): List<City> {
        val response = apiCityService.getCities(name).execute()
        Log.i(RepositoryCities::class.java.name, "consulta de ciudad: ${response.body()?.size}")
        if (response.isSuccessful)
            return response.body()!!
        else
            throw Exception("RepositoryCities->Error al consumir servicio de ApiCityService->${response.code()}->${response.message()}->${Gson().toJson(response.errorBody())}")
    }
}