package com.example.homeworkapp.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.homeworkapp.R
import com.example.homeworkapp.databinding.FragmentCBinding

class CFragment: Fragment(R.layout.fragment_c) {
    private var _binding: FragmentCBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCBinding.bind(view)

        with(binding) {
            btnNextC2.setOnClickListener{
                findNavController().navigate(R.id.action_CFragment_to_C2Fragment)
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}