package com.example.financieraoh.domain.model

import com.google.gson.annotations.SerializedName

data class Minutely (
    val precip: Double? = null,
    val snow: Long? = null,
    val temp: Double? = null,
    val ts: Long? = null,
    @SerializedName("timestamp_local")
    val timestampLocal: String? = null,
    val timestampUTC: String? = null
)
