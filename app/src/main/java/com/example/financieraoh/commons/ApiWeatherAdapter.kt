package com.example.financieraoh.commons

import com.example.financieraoh.service.ApiWeatherService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class ApiWeatherAdapter {
    companion object {
        private var API_SERVICE: ApiWeatherService? = null

        private val BASE_URL = "https://api.weatherbit.io/v2.0/"

        @JvmStatic
        fun getApiService(): ApiWeatherService? {

            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)

            val okHttpClient = OkHttpClient().newBuilder()
                .addInterceptor(logging)
                .readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .addNetworkInterceptor(Interceptor { chain ->
                    var request = chain.request()

                    val url = request.url.newBuilder()
                        .addQueryParameter("key", "9ab076caf0c548b9934f9d6ea9f22d99").build()
                    request = request.newBuilder().url(url).build();
                    chain.proceed(request)
                })
                .build()

            if (API_SERVICE == null) {

                val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build()
                API_SERVICE = retrofit.create(ApiWeatherService::class.java)
            }
            return API_SERVICE
        }
    }
}