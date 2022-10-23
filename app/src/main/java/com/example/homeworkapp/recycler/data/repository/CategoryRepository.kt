package com.example.homeworkapp.recycler.data.repository

import com.example.homeworkapp.R
import com.example.homeworkapp.recycler.data.models.Category

class CategoryRepository {
    fun getAllCategories() = listOf(
        Category(
            R.drawable.ic_playlist,
            "Playlists"
        ),
        Category(
            R.drawable.ic_track,
            "Tracks"
        ),
        Category(
            R.drawable.ic_album,
            "Albums"
        ),
        Category(
            R.drawable.ic_avtor,
            "Artists"
        ),
        Category(
            R.drawable.ic_podcast,
            "Podcasts"
        ),
        Category(
            R.drawable.ic_downloads,
            "Downloaded tracks"
        ),
        Category(
            R.drawable.ic_device_tracks,
            "Local tracks"
        )
    )
}