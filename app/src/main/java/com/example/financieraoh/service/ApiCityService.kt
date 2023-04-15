package com.example.financieraoh.service

import com.example.financieraoh.domain.model.City
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiCityService {

    @GET("city")
    fun getCities(@Query("name") name: String, @Query("limit") limit: Int = 30) : Call<List<City>>
}