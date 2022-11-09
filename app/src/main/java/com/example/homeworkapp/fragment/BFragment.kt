package com.example.homeworkapp.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.homeworkapp.R
import com.example.homeworkapp.databinding.FragmentBBinding

class BFragment: Fragment(R.layout.fragment_b) {
    private var _binding: FragmentBBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentBBinding.bind(view)

        with(binding){
            btnNext.setOnClickListener{
                val strText = textInputEditText.text.toString()
                val bundle = Bundle()
                bundle.putString(
                    "ARG_TEXT",
                    strText
                )
                findNavController().navigate(R.id.action_BFragment_to_B2Fragment,bundle)
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}