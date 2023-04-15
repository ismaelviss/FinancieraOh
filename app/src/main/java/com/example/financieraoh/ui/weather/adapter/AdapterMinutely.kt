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

class AdapterMinutely(private val minutelyList: List<Datum>): RecyclerView.Adapter<AdapterMinutely.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_view_minutely, parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(minutelyList[position])
    }

    override fun getItemCount(): Int {
        return minutelyList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @SuppressLint("SetTextI18n")
        fun bind(minutely: Datum){
            itemView.findViewById<TextView>(R.id.temp).text = "${minutely.temp} °C"
            itemView.findViewById<TextView>(R.id.precip).text = "${minutely.precip} mm/hr"
            itemView.findViewById<TextView>(R.id.minutely_text).text = "Hora ${UtilDate.timeFormat(minutely.timestampLocal)}"
            itemView.findViewById<TextView>(R.id.description).text = minutely.weather?.description

            Picasso.get()
                .load("https://www.weatherbit.io/static/img/icons/${minutely.weather?.icon}.png")
                .placeholder(R.drawable.progress_animation)
                .error(R.drawable.sun_icon)
                .into( itemView.findViewById<ImageView>(R.id.image_weather))
        }
    }
}