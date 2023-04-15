package com.example.financieraoh.domain.model

data class HourlyForecast (
    val cityName: String? = null,
    val countryCode: String? = null,
    val data: List<Datum>? = null,
    val lat: Double? = null,
    val lon: Double? = null,
    val stateCode: String? = null,
    val timezone: String? = null
)

enum class Pod {
    D,
    N
}

enum class Description {
    Aguacero,
    LluviaLigera,
    LluviaModerada,
    NubesDispersas
}