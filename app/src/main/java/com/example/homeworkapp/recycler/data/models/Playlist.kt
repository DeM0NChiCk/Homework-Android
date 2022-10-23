package com.example.homeworkapp.recycler.data.models

data class Playlist (
    val title: String,
    val tracksCount: Int,
    val durationHours: Int,
    val coversUrls: List<String>
)