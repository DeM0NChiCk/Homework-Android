package com.example.homeworkapp.fragment

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.homeworkapp.R
import com.example.homeworkapp.activity.MainActivity
import com.example.homeworkapp.databinding.FragmentC3Binding

class C3Fragment: Fragment (R.layout.fragment_c3) {
    private var _binding: FragmentC3Binding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentC3Binding.bind(view)
        (requireActivity() as? MainActivity)?.changeBtnNavVisibility(true)
        with(binding) {
            btnNextC.setOnClickListener{
                findNavController().navigate(R.id.action_C3Fragment_to_CFragment)
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}