package com.example.homeworkapp.fragment

import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.homeworkapp.R
import com.example.homeworkapp.databinding.FragmentIntroductoryBinding
import kotlin.random.Random

class IntroductoryFragment: Fragment() {
    private lateinit var binding: FragmentIntroductoryBinding

    private var counter = 0
    private var color_image = Color.BLUE

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentIntroductoryBinding.inflate(inflater, container, false)

        binding.apply {
            btnShowChanges.setOnClickListener{
                parentFragmentManager.beginTransaction()
                    .replace(
                        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE)
                            R.id.fragment_container_secondary
                        else
                            R.id.fragment_container_primary,
                        ChangesFragment.getInstance(counter, color_image),"changes"
                    )
                    .addToBackStack("introductory")
                    .commit()
            }
            btnIncreaseValue.setOnClickListener {
                counter++
                val fragment = parentFragmentManager.findFragmentByTag("changes")?: return@setOnClickListener
                if(!fragment.isAdded) return@setOnClickListener

                (fragment as ChangesFragment).changeCounter(counter)

            }
            btnChangeColor.setOnClickListener {
                color_image = Color.rgb(
                    Random.nextInt(0,255),
                    Random.nextInt(0,255),
                    Random.nextInt(0,255),
                )
                val fragment = parentFragmentManager.findFragmentByTag("changes") ?: return@setOnClickListener
                if (!fragment.isAdded) return@setOnClickListener

                (fragment as ChangesFragment).changeColor(color_image)
            }
        }

        return binding.root
    }
    companion object {
        fun getInstance() = IntroductoryFragment()
    }
}