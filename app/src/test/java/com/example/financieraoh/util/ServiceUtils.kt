package com.example.financieraoh.util

import com.example.financieraoh.repositories.RepositoryCitiesTest
import com.example.financieraoh.service.ApiCityService
import com.example.financieraoh.service.ApiWeatherService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ServiceUtils {
    companion object {
        fun provideApiCityService(server: MockWebServer) : ApiCityService {
            val service = retrofit(server)
            return service.create(ApiCityService::class.java)
        }

        fun provideApiWeatherService(server: MockWebServer) : ApiWeatherService {
            val service = retrofit(server)
            return service.create(ApiWeatherService::class.java)
        }

        private fun retrofit(server: MockWebServer): Retrofit {
            val builder = OkHttpClient().newBuilder()
                .readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)

            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            builder.addInterceptor(interceptor)

            val okHttpClient = builder.build()

            val service = Retrofit.Builder()
                .baseUrl(server.url(""))
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
            return service
        }

        fun enqueueMockResponse(fileName: String) : MockResponse {
            val inputStream = RepositoryCitiesTest::class.java.classLoader?.getResourceAsStream(fileName)
            val source = inputStream?.source()?.buffer()
            val mockResponse = MockResponse()
            mockResponse.setBody(source?.readString(Charsets.UTF_8) ?: "")
            return mockResponse
        }

        fun responseString(fileName: String) : String {
            val inputStream = RepositoryCitiesTest::class.java.classLoader?.getResourceAsStream(fileName)
            val source = inputStream?.source()?.buffer()
            return source?.readString(Charsets.UTF_8) ?: ""
        }
    }
}