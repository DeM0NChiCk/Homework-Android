package com.example.homeworkapp.recycler.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.homeworkapp.R
import com.example.homeworkapp.databinding.ItemPlaylistBinding
import com.example.homeworkapp.recycler.data.models.Playlist

class PlaylistHolder(
    private val binding: ItemPlaylistBinding,
    private val coverStates: IntArray
): RecyclerView.ViewHolder(binding.root) {
    private val root = binding.root
    private val header = binding.itemPlaylistHeader
    private val cover = binding.itemPlaylistCover
    private val title = binding.itemPlaylistTitle
    private val tracksCount = binding.itemPlaylistTracksCount
    private val duration = binding.itemPlaylistDuration

    private var playlist: Playlist? = null
    private var localPosition: Int? = null

    init {
        cover.setOnClickListener {
            if (playlist == null || localPosition == null)
                return@setOnClickListener

            val coverState = coverStates[localPosition!!]

            if (coverState == 2)
                coverStates[localPosition!!] = 0
            else
                coverStates[localPosition!!]++

            loadCover(playlist!!, localPosition!!)
        }
    }
    fun bindItem(playlist: Playlist, localPosition: Int) {
        this.playlist = playlist
        this.localPosition = localPosition

        if (localPosition == 0)
            header.visibility = View.VISIBLE
        else
            header.visibility = View.GONE

        title.text = playlist.title
        tracksCount.text = binding.root.resources.getString(
            R.string.tracks_placeholder, playlist.tracksCount
        )
        duration.text = binding.root.resources.getString(
            R.string.hours_placeholder, playlist.durationHours
        )
        loadCover(playlist, localPosition)
    }

    private fun loadCover(playlist: Playlist, localPosition: Int) {
        Glide.with(root.context)
            .load(playlist.coversUrls[coverStates[localPosition]])
            .into(cover)
    }
}