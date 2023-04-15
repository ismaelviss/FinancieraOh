package com.example.financieraoh.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.financieraoh.commons.ResultGet
import com.example.financieraoh.domain.DomainCity
import com.example.financieraoh.domain.model.City
import com.example.financieraoh.domain.model.CurrentWeather
import com.example.financieraoh.domain.model.DailyForecast
import com.example.financieraoh.domain.model.HourlyForecast
import kotlinx.coroutines.*

class MainViewModel(private val domainCity: DomainCity) : ViewModel() {

    private val _resultGetCities = MutableLiveData<ResultGet<List<City>>>()
    val resultGetCities: MutableLiveData<ResultGet<List<City>>>
        get() = _resultGetCities

    private val _resultCurrentWeather = MutableLiveData<ResultGet<CurrentWeather>>()
    val resultCurrentWeather: MutableLiveData<ResultGet<CurrentWeather>>
        get() = _resultCurrentWeather

    private val _resultCurrentWeatherList = MutableLiveData<ResultGet<List<CurrentWeather>>>()
    val resultCurrentWeatherList: MutableLiveData<ResultGet<List<CurrentWeather>>>
        get() = _resultCurrentWeatherList

    private val _resultHourlyForecast = MutableLiveData<ResultGet<HourlyForecast>>()
    val resultHourlyForecast: MutableLiveData<ResultGet<HourlyForecast>>
        get() = _resultHourlyForecast

    private val _resultDailyForecast = MutableLiveData<ResultGet<DailyForecast>>()
    val resultDailyForecast: MutableLiveData<ResultGet<DailyForecast>>
        get() = _resultDailyForecast

    fun getCitiesFilter(filter: String) {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                _resultGetCities.value = ResultGet.Loading()
                 withContext(Dispatchers.IO) { ResultGet.Success(domainCity.getCitiesFilter(filter)) }.let { _resultGetCities.value = it }
            } catch (ex: Exception) {
                ex.printStackTrace()
                Log.e(MainViewModel::class.java.name, "getCitiesFilter", ex)
                _resultGetCities.value = ResultGet.Error(ex)
            }
        }
    }

    fun getCurrentWeatherCity(city: City) {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                _resultCurrentWeather.value = ResultGet.Loading()
                withContext(Dispatchers.IO) { ResultGet.Success(domainCity.getCurrentWeatherCity(city))}.let { _resultCurrentWeather.value = it  }
            } catch (ex: Exception) {
                ex.printStackTrace()
                Log.e(MainViewModel::class.java.name, "getCurrentWeatherCity", ex)
                _resultCurrentWeather.value = ResultGet.Error(ex)
            }
        }
    }

    fun getCurrentWeatherCity(cities: List<City>) {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val currentWeatherList = mutableListOf<CurrentWeather>()
                _resultCurrentWeatherList.value = ResultGet.Loading()
                withContext(Dispatchers.IO) {
                    cities
                        .parallelStream()
                        .forEach {
                            val response = domainCity.getCurrentWeatherCity(it)
                            response.city = it
                            currentWeatherList.add(response)
                        }
                }
                _resultCurrentWeatherList.value = ResultGet.Success(currentWeatherList)
            } catch (ex: Exception) {
                ex.printStackTrace()
                Log.e(MainViewModel::class.java.name, "getCurrentWeatherCity", ex)
                _resultCurrentWeatherList.value = ResultGet.Error(ex)
            }
        }
    }

    fun getForecastHourly(city: City) {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                _resultHourlyForecast.value = ResultGet.Loading()
                withContext(Dispatchers.IO) { ResultGet.Success(domainCity.getForecastHourly(city))}.let { _resultHourlyForecast.value = it  }
            } catch (ex: Exception) {
                ex.printStackTrace()
                Log.e(MainViewModel::class.java.name, "getForecastHourly", ex)
                _resultHourlyForecast.value = ResultGet.Error(ex)
            }
        }
    }

    fun getForecastDaily(city: City) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                _resultDailyForecast.value = ResultGet.Loading()
                withContext(Dispatchers.IO) { ResultGet.Success(domainCity.getForecastDaily(city))}.let { _resultDailyForecast.value = it  }
            } catch (ex: Exception) {
                ex.printStackTrace()
                Log.e(MainViewModel::class.java.name, "getForecastDaily", ex)
                _resultDailyForecast.value = ResultGet.Error(ex)
            }
        }
    }
}