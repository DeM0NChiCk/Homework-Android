package com.example.homeworkapp.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.homeworkapp.R
import com.example.homeworkapp.databinding.FragmentABinding

class AFragment: Fragment(R.layout.fragment_a) {
    private var _binding: FragmentABinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentABinding.bind(view)

        with(binding){
            btnNextA.setOnClickListener {
                findNavController().navigate(R.id.action_AFragment_to_A2Fragment)
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}