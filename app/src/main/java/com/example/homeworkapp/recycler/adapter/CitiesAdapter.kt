package com.example.homeworkapp.recycler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.homeworkapp.recycler.data.City
import com.example.homeworkapp.recycler.sort.CitiesSort
import com.example.homeworkapp.recycler.sort.SortingMode
import com.example.homeworkapp.databinding.ItemCityBinding


class CitiesAdapter(
    private var cities: List<City>,
): RecyclerView.Adapter<CitiesHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CitiesHolder {
        LayoutInflater.from(parent.context).let {
            val binding = ItemCityBinding.inflate(it, parent, false)
            return CitiesHolder(binding)
        }
    }
    override fun onBindViewHolder(holder: CitiesHolder, position: Int) {
        holder.idtxt.text = cities[position].id.toString()
        holder.nametxt.text = cities[position].name
    }

    override fun getItemCount() = cities.size

    fun reorderList(sortingMode: SortingMode) {
        cities = CitiesSort.sortCities(cities, sortingMode)
        notifyDataSetChanged()
    }

}