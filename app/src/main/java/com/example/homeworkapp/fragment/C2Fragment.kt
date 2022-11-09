package com.example.homeworkapp.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.homeworkapp.R
import com.example.homeworkapp.activity.MainActivity
import com.example.homeworkapp.databinding.FragmentC2Binding

class C2Fragment: Fragment(R.layout.fragment_c2) {
    private var _binding: FragmentC2Binding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentC2Binding.bind(view)
        (requireActivity() as? MainActivity)?.changeBtnNavVisibility(false)
        with(binding) {
            btnNextC3.setOnClickListener{
                findNavController().navigate(R.id.action_C2Fragment_to_C3Fragment)
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}