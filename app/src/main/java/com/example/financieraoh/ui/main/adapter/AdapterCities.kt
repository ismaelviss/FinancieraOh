package com.example.financieraoh.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.financieraoh.R
import com.example.financieraoh.domain.model.City
import com.example.financieraoh.domain.model.CurrentWeather
import com.squareup.picasso.Picasso

class AdapterCities(private val itemOnClickListener: OnItemClickListener)
    : RecyclerView.Adapter<AdapterCities.ViewHolder>() {

    private lateinit var cities: MutableList<CurrentWeather>

    fun setData(items: List<CurrentWeather>) {
        cities = items.toMutableList()
        if (this.cities != null && this.cities.size > 2) this.cities.reverse()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_view_city_weather, parent,false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return cities.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding(cities[position], itemOnClickListener)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var nameCity: TextView

        init {
            nameCity = itemView.findViewById(R.id.name_city)
        }

        fun binding(currentWeather: CurrentWeather, itemOnClickListener: OnItemClickListener) {
            val datum = currentWeather.data?.first()!!
            itemView.findViewById<TextView>(R.id.name_city).text = currentWeather.city?.name
            itemView.findViewById<TextView>(R.id.description).text = datum.weather?.description
            itemView.findViewById<TextView>(R.id.temp).text = datum.temp.toString()
            itemView.findViewById<TextView>(R.id.app_temp).text = datum.appTemp.toString()

            Picasso.get()
                .load("https://www.weatherbit.io/static/img/icons/${datum.weather?.icon}.png")
                .placeholder(R.drawable.progress_animation)
                .error(R.drawable.sun_icon)
                .into(itemView.findViewById<ImageView>(R.id.img_weather))

            itemView.setOnClickListener {
                itemOnClickListener.onItemClick(currentWeather.city!!)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(city: City)
    }
}
