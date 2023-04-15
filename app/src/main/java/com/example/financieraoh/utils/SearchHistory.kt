package com.example.financieraoh.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.financieraoh.domain.model.City
import com.google.gson.Gson
import java.io.Serializable
import java.util.ArrayList

class SearchHistory {
    companion object {
        private val SEARCH_HISTORY_KEY = "_SEARCH_HISTORY_KEY"
        private val MAX_HISTORY_ITEMS = 10
        private var prefs: SharedPreferences? = null

        fun getSearchHistory(context: Context): MutableList<City> {
            prefs = context.getSharedPreferences("PREF_RECENT_SEARCH", Context.MODE_PRIVATE)
            val json = prefs!!.getString(SEARCH_HISTORY_KEY, "")
            if (json == "") return ArrayList()
            val searchObject: SearchObject = Gson().fromJson(json, SearchObject::class.java)
            return if (searchObject?.items != null && searchObject.items.isNotEmpty()) searchObject.items else ArrayList()
        }

        fun addSearchHistory(context: Context, city: City) {
            val searchObject = SearchObject(getSearchHistory(context))

            val searchCity = searchObject.items.firstOrNull{x -> x.name == city.name && x.country == city.country}
            if (searchCity != null)
                searchObject.items.remove(searchCity)

            searchObject.items.add(city)

            if (searchObject.items.size > MAX_HISTORY_ITEMS)
                searchObject.items.removeAt(0)

            val json = Gson().toJson(searchObject, SearchObject::class.java)
            prefs!!.edit().putString(SEARCH_HISTORY_KEY, json).apply()
        }
    }

    private class SearchObject(items: MutableList<City>) : Serializable {
        var items: MutableList<City> = ArrayList()
        init {
            this.items = items
        }
    }
}