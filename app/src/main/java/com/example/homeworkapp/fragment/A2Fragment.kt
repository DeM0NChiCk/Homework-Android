package com.example.homeworkapp.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.homeworkapp.R
import com.example.homeworkapp.databinding.FragmentA2Binding

class A2Fragment: Fragment(R.layout.fragment_a2) {
    private var _binding: FragmentA2Binding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentA2Binding.bind(view)

        with(binding){
            btnNextA2.setOnClickListener {
                findNavController().navigate(R.id.action_A2Fragment_to_A3Fragment)
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}