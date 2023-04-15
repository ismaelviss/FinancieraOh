package com.example.financieraoh

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.financieraoh.commons.ApiCityAdapter
import com.example.financieraoh.commons.ApiWeatherAdapter
import com.example.financieraoh.domain.DomainCity
import com.example.financieraoh.repositories.RepositoryCities
import com.example.financieraoh.repositories.RepositoryWeather
import com.example.financieraoh.service.ApiWeatherService
import com.example.financieraoh.ui.main.MainFragment
import com.example.financieraoh.ui.main.MainViewModel
import com.example.financieraoh.utils.Tools


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow()
        }

        Tools.setSystemBarColor(this, R.color.grey_5)
        Tools.setSystemBarLight(this)
    }

    @Suppress(names = ["SingletonConstructor"])
    open class NewInstanceFactory(val context: Context)
        : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            val repository = RepositoryCities(ApiCityAdapter.getApiService()!!)
            val repositoryWeather = RepositoryWeather(ApiWeatherAdapter.getApiService()!!)
            val domain = DomainCity(repository,repositoryWeather)
            return MainViewModel(domain) as T
        }
    }
}