package com.example.financieraoh.ui.weather

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.financieraoh.MainActivity
import com.example.financieraoh.R
import com.example.financieraoh.commons.ResultGet
import com.example.financieraoh.domain.model.City
import com.example.financieraoh.domain.model.Datum
import com.example.financieraoh.domain.model.Minutely
import com.example.financieraoh.ui.main.MainViewModel
import com.example.financieraoh.ui.weather.adapter.AdapterDaily
import com.example.financieraoh.ui.weather.adapter.AdapterMinutely
import com.squareup.picasso.Picasso

class FragmentWeather : Fragment() {

    private lateinit var viewModel: MainViewModel
    private lateinit var city: City

    private var recyclerViewMinutely: RecyclerView? = null
    private var adapterMinutely: RecyclerView.Adapter<AdapterMinutely.ViewHolder>? = null
    private var layoutManagerMinutely: RecyclerView.LayoutManager? = null

    private var recyclerViewDaily: RecyclerView? = null
    private var adapterDaily: RecyclerView.Adapter<AdapterDaily.ViewHolder>? = null
    private var layoutManagerDaily: RecyclerView.LayoutManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, MainActivity.NewInstanceFactory(requireContext()))[MainViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_weather, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observableResultCurrentWeather()
        observableResultForecastHourly()
        observableResultForecastDaily()

        viewModel.getCurrentWeatherCity(city)
        viewModel.getForecastHourly(city)
        viewModel.getForecastDaily(city)
    }

    private fun observableResultForecastDaily() {
        viewModel.resultDailyForecast.observe(viewLifecycleOwner) {
            when(it) {
                is ResultGet.Success -> {
                    it.data.data?.let { it1 -> listDaily(it1) }
                }
                else -> {}
            }
        }    }

    private fun observableResultForecastHourly() {
        viewModel.resultHourlyForecast.observe(viewLifecycleOwner) {
            when(it) {
                is ResultGet.Success -> {
                    it.data.data?.let { it1 -> listMinutely(it1) }
                }
                else -> {}
            }
        }
    }

    private fun observableResultCurrentWeather() {
        viewModel.resultCurrentWeather.observe(viewLifecycleOwner) {
            when(it) {
                is ResultGet.Success -> {
                    val currentWeather = it.data.data?.first()
                    val imageView = view?.findViewById<ImageView>(R.id.img_weather)

                    view?.findViewById<TextView>(R.id.name_city)?.text = city.name
                    view?.findViewById<TextView>(R.id.temp)?.text = "${currentWeather?.temp.toString()} °C"
                    view?.findViewById<TextView>(R.id.app_temp)?.text = "${currentWeather?.appTemp.toString()} °C"
                    view?.findViewById<TextView>(R.id.humidity)?.text = "${currentWeather?.rh.toString()}%"
                    view?.findViewById<TextView>(R.id.pres)?.text = "${currentWeather?.pres.toString()} mB"
                    view?.findViewById<TextView>(R.id.wind_spd)?.text = "${currentWeather?.windSpd.toString()} km/h"
                    view?.findViewById<TextView>(R.id.uv)?.text = currentWeather?.uv.toString()
                    view?.findViewById<TextView>(R.id.description)?.text = currentWeather?.weather?.description

                    Picasso.get()
                        .load("https://www.weatherbit.io/static/img/icons/${currentWeather?.weather?.icon}.png")
                        .placeholder(R.drawable.progress_animation)
                        .error(R.drawable.sun_icon)
                        .into(imageView)
                }
                is ResultGet.Error -> {
                    it.exception.printStackTrace()
                }
                else -> {}
            }
        }
    }

    private fun listMinutely(minutely: List<Datum>) {
        recyclerViewMinutely = view?.findViewById(R.id.minutely)
        adapterMinutely = AdapterMinutely(minutely)
        layoutManagerMinutely = LinearLayoutManager(
            this.requireContext(),
            RecyclerView.HORIZONTAL,
            false
        )
        recyclerViewMinutely?.layoutManager = layoutManagerMinutely
        recyclerViewMinutely?.adapter = adapterMinutely
    }

    private fun listDaily(daily: List<Datum>) {
        recyclerViewDaily = view?.findViewById(R.id.daily)
        adapterDaily = AdapterDaily(daily)
        layoutManagerMinutely = LinearLayoutManager(
            this.requireContext(),
            RecyclerView.VERTICAL,
            false
        )
        recyclerViewDaily?.layoutManager = layoutManagerMinutely
        recyclerViewDaily?.adapter = adapterDaily
    }

    companion object {


        @JvmStatic
        fun newInstance(city: City) =
            FragmentWeather().apply {
                this.city = city
            }
    }
}