package com.example.homeworkapp.recycler.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.homeworkapp.databinding.ItemCityBinding

class CitiesHolder(binding: ItemCityBinding): RecyclerView.ViewHolder(binding.root) {
    val idtxt = binding.itemCitiesTextId
    val nametxt = binding.itemCitiesTextName
}