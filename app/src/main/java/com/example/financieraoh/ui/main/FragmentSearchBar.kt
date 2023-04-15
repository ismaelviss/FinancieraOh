package com.example.financieraoh.ui.main

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.transition.TransitionInflater
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
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
import com.example.financieraoh.ui.main.adapter.AdapterSearchCities
import com.example.financieraoh.ui.weather.FragmentWeather
import com.example.financieraoh.utils.SearchHistory
import com.google.gson.Gson

class FragmentSearchBar() : Fragment() {

    private lateinit var viewModel: MainViewModel
    private lateinit var adapterSearchCities: AdapterSearchCities

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, MainActivity.NewInstanceFactory(requireContext()))[MainViewModel::class.java]
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_bar, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<View>(R.id.bt_menu).setOnClickListener { requireActivity().onBackPressed() }

        val imageView = view.findViewById<CardView>(R.id.fragment_b_image)
        ViewCompat.setTransitionName(imageView, "simple_fragment_transition")

        val recyclerSuggestion = view.findViewById<RecyclerView>(R.id.recyclerSuggestion)

        recyclerSuggestion.layoutManager = LinearLayoutManager(context)
        recyclerSuggestion.setHasFixedSize(true)
        adapterSearchCities = AdapterSearchCities(object : AdapterSearchCities.OnItemClickListener {
            override fun onItemClick(city: City) {
                SearchHistory.addSearchHistory(requireContext(), city)
                currentWeather(city)
            }
        })
        recyclerSuggestion.adapter = adapterSearchCities

        openKeyboard()
        searchEditText()
        observableCities()
    }

    private fun currentWeather(city: City) {
        val fragmentWeather: FragmentWeather = FragmentWeather.newInstance(city)

        parentFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.container, fragmentWeather)
            .commit()
    }

    private fun observableCities() {
        viewModel.resultGetCities.observe(viewLifecycleOwner) {
            when(it) {
                is ResultGet.Success -> {
                    adapterSearchCities.setData(it.data)
                    Log.i("prueba", Gson().toJson(it.data))
                }
                is ResultGet.Error -> {
                    it.exception.printStackTrace()
                }
                else -> {}
            }
        }
    }

    private fun openKeyboard() {
        val subject = requireView().findViewById<EditText>(R.id.subject)
        subject.requestFocus()
        val inputMethodManager: InputMethodManager = requireContext().getSystemService(android.app.Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(subject, InputMethodManager.SHOW_IMPLICIT)
    }

    private fun searchEditText() {
        view?.findViewById<EditText>(R.id.subject)?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) = Unit

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.getCitiesFilter(s.toString())
            }
        })
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            FragmentSearchBar()
    }
}