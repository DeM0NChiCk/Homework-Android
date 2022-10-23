package com.example.homeworkapp.recycler.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.homeworkapp.R
import com.example.homeworkapp.databinding.ItemRecommendationsBinding
import com.example.homeworkapp.recycler.PlayerAdapter
import com.example.homeworkapp.recycler.data.models.Track
import kotlin.random.Random

class RecHolder(
    private val binding: ItemRecommendationsBinding,
    private val newRec: MutableList<Track>,
    private val startPosition: Int,
    private val adapter: PlayerAdapter
) :
    RecyclerView.ViewHolder(binding.root) {

    private val root = binding.root

    private val header = binding.itemTrackHeader
    private val cardView = binding.itemTrackCardview
    private val cover = binding.itemTrackCover
    private val title = binding.itemTrackTitle
    private val releaseDate = binding.itemTrackReleaseDate
    private val auditionsCount = binding.itemTrackAuditionsCount

    init {
        cardView.setOnClickListener {
            val insertIndex = Random.nextInt(newRec.size + 1)

            newRec.add(
                insertIndex,
                Track(
                    "NEW TRACK",
                    2020,
                    150000,
                    "https://avatars.yandex.net/get-music-content/2357076/4e30457d.a.11226309-1/200x200"
                )
            )

            adapter.notifyItemInserted(startPosition + insertIndex)
        }
    }

    fun bindItem(localPosition: Int) {
        if (localPosition == 0)
            header.visibility = View.VISIBLE
        else
            header.visibility = View.GONE

        val track = newRec[localPosition]

        title.text = track.title
        releaseDate.text = track.releaseDate.toString()
        auditionsCount.text = binding.root.resources.getString(
            R.string.tracks_placeholder, track.auditionsCount
        )

        Glide.with(root.context)
            .load(track.cover)
            .into(cover)
    }
}