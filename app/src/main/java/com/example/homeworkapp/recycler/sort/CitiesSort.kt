package com.example.homeworkapp.recycler.sort

import com.example.homeworkapp.recycler.data.City

class CitiesSort {
    companion object {
        fun sortCities(cities: List<City>, sortingMode: SortingMode): List<City> {
            return when (sortingMode) {
                SortingMode.BY_NAME_ASC -> cities.sortedBy { it.name }
                SortingMode.BY_NAME_DESC -> cities.sortedByDescending { it.name }
                SortingMode.BY_ID_ASC -> cities.sortedBy { it.id }
                SortingMode.BY_ID_DESC -> cities.sortedByDescending { it.id }
            }
        }
    }
}