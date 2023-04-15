package com.example.financieraoh.ui.weather.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.financieraoh.R
import com.example.financieraoh.commons.UtilDate
import com.example.financieraoh.domain.model.Datum
import com.squareup.picasso.Picasso

class AdapterDaily(private val dailyList: List<Datum>) : RecyclerView.Adapter<AdapterDaily.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_view_daily, parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dailyList[position])
    }

    override fun getItemCount(): Int {
        return dailyList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @SuppressLint("SetTextI18n")
        fun bind(minutely: Datum){

            itemView.findViewById<TextView>(R.id.date).text = UtilDate.dateFormatDaily(minutely.datetime)
            itemView.findViewById<TextView>(R.id.description).text = minutely.weather?.description
            itemView.findViewById<TextView>(R.id.temp).text = "${minutely.maxTemp}°/${minutely.minTemp}°"

            Picasso.get()
                .load("https://www.weatherbit.io/static/img/icons/${minutely.weather?.icon}.png")
                .placeholder(R.drawable.progress_animation)
                .error(R.drawable.sun_icon)
                .into(itemView.findViewById<ImageView>(R.id.image_weather))
        }
    }
}