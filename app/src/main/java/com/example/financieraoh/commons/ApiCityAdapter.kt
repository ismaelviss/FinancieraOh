package com.example.financieraoh.commons

import com.example.financieraoh.service.ApiCityService
import com.example.financieraoh.service.ApiWeatherService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class ApiCityAdapter {
    companion object {
        private var API_SERVICE: ApiCityService? = null

        private val BASE_URL = "https://api.api-ninjas.com/v1/"

        @JvmStatic
        fun getApiService(): ApiCityService? {

            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)

            val okHttpClient = OkHttpClient().newBuilder()
                .addInterceptor(logging)
                .readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .addNetworkInterceptor(Interceptor { chain ->
                    val requestBuilder: Request.Builder = chain.request().newBuilder()
                    requestBuilder.header("X-Api-Key", "vnzXFC/y3G0dPDM7JWvVrw==tDtlSeDoeCMpH3BX")
                    chain.proceed(requestBuilder.build())
                })
                .build()

            if (API_SERVICE == null) {

                val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build()
                API_SERVICE = retrofit.create(ApiCityService::class.java)
            }
            return API_SERVICE
        }
    }
}