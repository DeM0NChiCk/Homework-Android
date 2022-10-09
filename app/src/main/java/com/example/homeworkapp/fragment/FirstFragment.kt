package com.example.homeworkapp.fragment

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.homeworkapp.R
import com.example.homeworkapp.databinding.FragmentFirstBinding
import com.example.homeworkapp.util.Constants

class FirstFragment: Fragment() {

    private lateinit var binding: FragmentFirstBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFirstBinding.inflate(layoutInflater)

        activity?.title = getString(R.string.first_fragment)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initClickListeners()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initClickListeners() {
        with(binding) {
            btnOpenToThirdFragment.setOnClickListener {
                parentFragmentManager.beginTransaction().replace(
                    Constants.containerID,
                    ThirdFragment.getInstance(arguments),
                    ThirdFragment.THIRD_FRAGMENT_TAG
                ).commit()
            }

            btnGenerateRandomNumber.setOnClickListener {
                saveValueToBundle((10..1000).random())
                Toast.makeText(context, "Number has been generated", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun saveValueToBundle(randomNumber : Int){
        arguments?.apply {
            putInt(Constants.RANDOM_NUMBER_KEY, randomNumber)
        }
    }

    companion object {
        const val FIRST_FRAGMENT_TAG = "FIRST_FRAGMENT_TAG"
        fun getInstance(bundle: Bundle?): FirstFragment {
            val firstFragment = FirstFragment()
            firstFragment.arguments = bundle
            return firstFragment
        }
    }
    override fun onSaveInstanceState(outState: Bundle) {
        outState.putAll(arguments)

        super.onSaveInstanceState(outState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if (savedInstanceState != null) {
            arguments = savedInstanceState
        }
    }
}