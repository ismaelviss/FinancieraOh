package com.example.financieraoh.ui.main.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.financieraoh.R
import com.example.financieraoh.domain.model.City

class AdapterSearchCities(var itemOnClickListener: OnItemClickListener) : RecyclerView.Adapter<AdapterSearchCities.ViewHolder>() {
    private var cities: List<City>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_view_city, parent,false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return cities?.size?:0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        cities?.let {  holder.binding(it[position], itemOnClickListener) }
    }

    fun setData(items: List<City>) {
        cities = items
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var nameCity: TextView

        init {
            nameCity = itemView.findViewById(R.id.name_city)
        }

        @SuppressLint("SetTextI18n")
        fun binding(city: City, itemOnClickListener: OnItemClickListener) {
            nameCity.text = "${city.name}, ${city.country}"
            itemView.setOnClickListener {
                itemOnClickListener.onItemClick(city)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(city: City)
    }
}