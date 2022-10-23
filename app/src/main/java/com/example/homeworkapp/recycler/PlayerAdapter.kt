package com.example.homeworkapp.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.homeworkapp.databinding.ItemCategoryBinding
import com.example.homeworkapp.databinding.ItemPlaylistBinding
import com.example.homeworkapp.databinding.ItemRecommendationsBinding
import com.example.homeworkapp.recycler.data.enums.PlayerRecyclerViewTypes
import com.example.homeworkapp.recycler.data.models.Category
import com.example.homeworkapp.recycler.data.models.Playlist
import com.example.homeworkapp.recycler.data.models.Track
import com.example.homeworkapp.recycler.holder.CategoryHolder
import com.example.homeworkapp.recycler.holder.PlaylistHolder
import com.example.homeworkapp.recycler.holder.RecHolder

class PlayerAdapter(
    private val playlists: List<Playlist>,
    private val categories: List<Category>,
    private val newRec: MutableList<Track>,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val stateList = IntArray(playlists.size)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder{
        return when (viewType) {
            PlayerRecyclerViewTypes.PLAYLIST_TYPE.tapeValue -> {
                PlaylistHolder(
                    ItemPlaylistBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false,
                    ), stateList
                )
            }
            PlayerRecyclerViewTypes.CATEGORIES_TYPE.tapeValue -> {
                CategoryHolder(
                    ItemCategoryBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            else -> {
                return RecHolder(
                    ItemRecommendationsBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ),
                    newRec,
                    categories.size + playlists.size,
                    this
                )
            }
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(getItemViewType(position)) {
            PlayerRecyclerViewTypes.PLAYLIST_TYPE.tapeValue -> {
                (holder as? PlaylistHolder)?.bindItem(
                    playlists[position],
                    position
                )
            }
            PlayerRecyclerViewTypes.CATEGORIES_TYPE.tapeValue -> {
                (holder as? CategoryHolder)?.bindItem(
                    categories[position - playlists.size],
                    position
                )
            }
            else -> {
                (holder as? RecHolder)?.bindItem(
                    position - playlists.size - categories.size
                )
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < playlists.size)
            PlayerRecyclerViewTypes.PLAYLIST_TYPE.tapeValue
        else if (position <  playlists.size + categories.size)
            PlayerRecyclerViewTypes.CATEGORIES_TYPE.tapeValue
        else
            PlayerRecyclerViewTypes.NEW_REC_TYPE.tapeValue
    }

    override fun getItemCount(): Int = newRec.size + categories.size + playlists.size

}