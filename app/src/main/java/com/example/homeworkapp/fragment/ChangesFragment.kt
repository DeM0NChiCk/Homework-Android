package com.example.homeworkapp.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.example.homeworkapp.R
import com.example.homeworkapp.databinding.FragmentChangesBinding


class ChangesFragment: Fragment() {

    private lateinit var binding: FragmentChangesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChangesBinding.inflate(inflater, container, false)

        with (requireArguments()) {
            changeCounter(getInt("counter", 0))
            changeColor(getInt("color", 0))
        }

        return binding.root
    }
    fun changeCounter(value: Int) {
        if (value == 0) {
            binding.textContent.visibility = View.INVISIBLE
        }
        else {
            binding.textContent.text = value.toString()
            binding.textContent.visibility = View.VISIBLE
        }
    }
    fun changeColor(color: Int) {
        binding.imageContent.setBackgroundColor(color)
    }
    companion object {
        fun getInstance(counter: Int, color: Int): ChangesFragment{
            val changesFragment = ChangesFragment()

            val arguments = bundleOf(
                "counter" to counter,
                "color" to color,
            )
            changesFragment.arguments = arguments

            return changesFragment
        }
    }
}