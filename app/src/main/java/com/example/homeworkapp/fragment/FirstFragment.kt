package com.example.homeworkapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.homeworkapp.R
import com.example.homeworkapp.databinding.FragmentDescriptionBinding
import com.example.homeworkapp.recycler.BreedAdapter
import com.example.homeworkapp.databinding.FragmentFirstBinding
import com.example.homeworkapp.recycler.BreedRepository

class FirstFragment: Fragment() {

    private lateinit var binding: FragmentFirstBinding
    var adapter: BreedAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val breeds = BreedRepository().getBreed()

        if(adapter == null){
            adapter = BreedAdapter(requireActivity(), breeds){
                parentFragmentManager.beginTransaction()
                    .replace(
                        R.id.fragment_container_primary,
                        DescriptionFragment.getInstance(it),
                        "description"
                    )
                    .addToBackStack("breeds")
                    .commit()
            }
        }

        with(binding){
            rvBreeds.adapter = adapter
        }
    }

    companion object {
        fun getInstance() = FirstFragment()
    }
}