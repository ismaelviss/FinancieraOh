package com.example.financieraoh.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.financieraoh.MainActivity
import com.example.financieraoh.R
import com.example.financieraoh.commons.ResultGet
import com.example.financieraoh.domain.model.City
import com.example.financieraoh.domain.model.CurrentWeather
import com.example.financieraoh.ui.main.adapter.AdapterCities
import com.example.financieraoh.ui.weather.FragmentWeather
import com.example.financieraoh.utils.SearchHistory

class MainFragment() : Fragment() {

    private var recyclerView: RecyclerView? = null
    private var adapter: AdapterCities? = null
    private var layoutManager: RecyclerView.LayoutManager? = null

    private lateinit var searchBar: CardView

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, MainActivity.NewInstanceFactory(requireContext()))[MainViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchBar = view.findViewById(R.id.search_bar)
        ViewCompat.setTransitionName(searchBar, "simple_fragment_transition")
        view.findViewById<View>(R.id.bt_search).setOnClickListener { onSearchBarClicked() }
        view.findViewById<View>(R.id.lyt_content).setOnClickListener { onSearchBarClicked() }

        observableCurrentWeatherCityList()
        val cities = SearchHistory.getSearchHistory(requireContext())
        if (cities.isNotEmpty()) {
            view.findViewById<View>(R.id.empty).visibility = View.GONE
            view.findViewById<View>(R.id.cities).visibility = View.VISIBLE
            viewModel.getCurrentWeatherCity(cities)
        }
        else {
            view.findViewById<View>(R.id.empty).visibility = View.VISIBLE
            view.findViewById<View>(R.id.cities).visibility = View.GONE
        }

    }

    private fun observableCurrentWeatherCityList() {
        viewModel.resultCurrentWeatherList.observe(viewLifecycleOwner) {
            when(it) {
                is ResultGet.Success -> {
                    initRecyclerView(it.data)
                }
                else -> {}
            }
        }
    }

    private fun initRecyclerView(data: List<CurrentWeather>) {

        recyclerView = view?.findViewById(R.id.cities)
        layoutManager = LinearLayoutManager(
            this.requireContext()
        )

        adapter = AdapterCities(object : AdapterCities.OnItemClickListener {
            override fun onItemClick(city: City) {
                currentWeather(city)
            }

        })
        recyclerView?.layoutManager = layoutManager
        recyclerView?.adapter = adapter

        adapter?.setData(data)
    }

    private fun currentWeather(city: City) {
        val fragmentWeather: FragmentWeather = FragmentWeather.newInstance(city)

        parentFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.container, fragmentWeather)
            .commit()
    }

    private fun onSearchBarClicked() {
        val simpleFragmentB: FragmentSearchBar = FragmentSearchBar.newInstance()

        parentFragmentManager.beginTransaction()
            .addSharedElement(searchBar, ViewCompat.getTransitionName(searchBar)!!)
            .addToBackStack(null)
            .replace(R.id.container, simpleFragmentB)
            .commit()
    }
}