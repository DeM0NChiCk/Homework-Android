package com.example.homeworkapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import androidx.fragment.app.Fragment
import com.example.homeworkapp.databinding.FragmentPlayerBinding
import com.example.homeworkapp.recycler.PlayerAdapter
import com.example.homeworkapp.recycler.data.repository.CategoryRepository
import com.example.homeworkapp.recycler.data.repository.PlaylistRepository
import com.example.homeworkapp.recycler.data.repository.TrackRepository

class PlayerFragment: Fragment() {

    private lateinit var binding: FragmentPlayerBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlayerBinding.inflate(inflater, container, false)

        val playlist = PlaylistRepository().getPinnedPlaylists()
        val category = CategoryRepository().getAllCategories()
        val newRec = TrackRepository().getRecTrack()

        with(binding.rvBreeds) {
                adapter = PlayerAdapter(
                    playlist,
                    category,
                    newRec.toMutableList()
                )
            }

        return binding.root
    }

    companion object {
        fun getInstance() = PlayerFragment()
    }
}