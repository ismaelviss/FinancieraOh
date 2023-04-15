package com.example.financieraoh.domain.model

import com.google.gson.annotations.SerializedName

data class Datum (
    @SerializedName("app_temp")
    val appTemp: Double? = null,

    @SerializedName("app_max_temp")
    val appMaxTemp: Double? = null,
    @SerializedName("app_min_temp")
    val appMinTemp: Double? = null,

    val aqi: Long? = null,
    @SerializedName("city_name")
    var cityName: String? = null,
    val clouds: Long? = null,
    val countryCode: String? = null,
    val datetime: String? = null,
    val dewpt: Double? = null,
    val dhi: Double? = null,
    val dni: Double? = null,
    val elevAngle: Double? = null,
    val ghi: Double? = null,
    val gust: Double? = null,
    val hAngle: Long? = null,
    val lat: Double? = null,
    val lon: Double? = null,
    val obTime: String? = null,
    val pod: String? = null,
    val precip: Double? = null,
    val pres: Double? = null,
    val rh: Long? = null,
    val slp: Double? = null,
    val snow: Long? = null,
    val solarRAD: Double? = null,
    val sources: List<String>? = null,
    val stateCode: String? = null,
    val station: String? = null,
    val sunrise: String? = null,
    val sunset: String? = null,
    val temp: Double? = null,
    val timezone: String? = null,
    val ts: Long? = null,
    val uv: Double? = null,

    val weather: Weather? = null,
    val windCdir: String? = null,
    val windCdirFull: String? = null,
    val windDir: Long? = null,

    @SerializedName("wind_spd")
    val windSpd: Double? = null,

    @SerializedName("timestamp_local")
    val timestampLocal: String? = null,

    @SerializedName("timestamp_utc")
    val timestampUTC: String? = null,

    @SerializedName("max_temp")
    val maxTemp: Double? = null,
    @SerializedName("min_temp")
    val minTemp: Double? = null
)
