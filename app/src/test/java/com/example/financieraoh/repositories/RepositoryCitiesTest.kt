package com.example.financieraoh.repositories

import com.example.financieraoh.service.ApiCityService
import com.example.financieraoh.service.ApiWeatherService
import com.example.financieraoh.util.ServiceUtils
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test

class RepositoryCitiesTest {

    private lateinit var server: MockWebServer

    private lateinit var apiCityService: ApiCityService
    private lateinit var apiWeatherService: ApiWeatherService
    private lateinit var repositoryCities: RepositoryCities
    private lateinit var repositoryWeather: RepositoryWeather

    @Before
    fun setup() {
        server = MockWebServer()
        apiCityService = ServiceUtils.provideApiCityService(server)
        apiWeatherService = ServiceUtils.provideApiWeatherService(server)
        repositoryCities = RepositoryCities(apiCityService)
        repositoryWeather = RepositoryWeather(apiWeatherService)

    }

    @Test
    fun test_getCitiesFilter() {
        runBlocking {
            server.enqueue(ServiceUtils.enqueueMockResponse("cities.json"))

            val responseBody = repositoryCities.getCities("gua")
            val request = server.takeRequest()

            assertThat(responseBody).isNotNull()
            assertThat(responseBody).isNotEmpty()
            assertThat(responseBody).hasSize(30)
            assertThat(request.path).isEqualTo("/city?name=gua&limit=30")
        }
    }

    @After
    fun tearDown() {
        server.shutdown()
    }
}