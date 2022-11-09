package com.example.homeworkapp.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.homeworkapp.R
import com.example.homeworkapp.databinding.FragmentB2Binding

class B2Fragment: Fragment(R.layout.fragment_b2) {
    private var _binding: FragmentB2Binding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentB2Binding.bind(view)

        val recText = arguments?.getString("ARG_TEXT").orEmpty()
        if(recText.isNotEmpty()) {
            binding.textFragA.text = "You entered: $recText"
        } else{
            binding.textFragA.text = "You didn't enter anything"
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}