package com.example.financieraoh.repositories

import com.example.financieraoh.domain.model.City
import com.google.common.truth.Truth.assertThat
import com.example.financieraoh.service.ApiWeatherService
import com.example.financieraoh.util.ServiceUtils
import com.example.financieraoh.util.ServiceUtils.Companion.enqueueMockResponse
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Test

class RepositoryWeatherTest {
    private lateinit var server: MockWebServer

    private lateinit var apiWeatherService: ApiWeatherService
    private lateinit var repositoryWeather: RepositoryWeather

    @Before
    fun setup() {
        server = MockWebServer()
        apiWeatherService = ServiceUtils.provideApiWeatherService(server)
        repositoryWeather = RepositoryWeather(apiWeatherService)
    }

    @Test
    fun test_current_weather() {
        runBlocking {
            server.enqueue(enqueueMockResponse("currentWeather.json"))

            val responseBody = repositoryWeather.getCurrentWeather(City("Guayaquil", -2.20584, -79.90795, "EC", 1, false))
            val request = server.takeRequest()

            assertThat(responseBody).isNotNull()
            assertThat(responseBody.count == 1L).isTrue()
            assertThat(responseBody.data?.first()?.appTemp == 36.5).isTrue()
            assertThat(responseBody.data?.first()?.temp == 31.0).isTrue()
            assertThat(responseBody.data?.first()?.weather?.description).isEqualTo("Cielo despejado")
            assertThat(responseBody.data?.first()?.windSpd).isEqualTo(1.5)
            assertThat(responseBody.data?.first()?.weather?.icon).isEqualTo("c01d")
            assertThat(request.path).isEqualTo("/current?lat=-2.20584&lon=-79.90795&include=minutely&lang=es")
        }
    }

    @Test
    fun test_ForecastHourly() {
        runBlocking {
            server.enqueue(enqueueMockResponse("forecastHourly.json"))

            val responseBody = repositoryWeather.getForecastHourly(City("Guayaquil", -2.20584, -79.90795, "EC", 1, false))
            val request = server.takeRequest()

            assertThat(responseBody).isNotNull()
            assertThat(responseBody.data?.size).isEqualTo(24)
            assertThat(responseBody.data?.first()?.appTemp == 37.5).isTrue()
            assertThat(responseBody.data?.first()?.temp == 31.3).isTrue()
            assertThat(responseBody.data?.first()?.weather?.description).isEqualTo("Aguacero")
            assertThat(responseBody.data?.first()?.windSpd).isEqualTo(2.57)
            assertThat(responseBody.data?.first()?.weather?.icon).isEqualTo("r05d")
            assertThat(request.path).isEqualTo("/forecast/hourly?lat=-2.20584&lon=-79.90795&hours=24&lang=es")
        }
    }

    @Test
    fun test_ForecastDaily() {
        runBlocking {
            server.enqueue(enqueueMockResponse("forecastDaily.json"))

            val responseBody = repositoryWeather.getForecastDaily(City("Guayaquil", -2.20584, -79.90795, "EC", 1, false))
            val request = server.takeRequest()

            assertThat(responseBody).isNotNull()
            assertThat(responseBody.data?.size).isEqualTo(16)
            assertThat(responseBody.data?.first()?.minTemp == 22.8).isTrue()
            assertThat(responseBody.data?.first()?.maxTemp == 30.5).isTrue()
            assertThat(responseBody.data?.first()?.weather?.description).isEqualTo("Tormenta con lluvia intensa")
            assertThat(responseBody.data?.first()?.windSpd).isEqualTo(2.2)
            assertThat(responseBody.data?.first()?.weather?.icon).isEqualTo("t03d")
            assertThat(request.path).isEqualTo("/forecast/daily?lat=-2.20584&lon=-79.90795&days=16&lang=es")
        }
    }
}