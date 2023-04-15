package com.example.financieraoh.domain.model

data class CurrentWeather (
    val count: Long? = null,
    val data: List<Datum>? = null,
    val minutely: List<Minutely>? = null
) {
    var city: City? = null
}
