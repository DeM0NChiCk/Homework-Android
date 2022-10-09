package com.example.homeworkapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.example.homeworkapp.R
import com.example.homeworkapp.databinding.FragmentSecondBinding
import com.example.homeworkapp.util.Constants

class SecondFragment: Fragment() {

    private lateinit var binding: FragmentSecondBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSecondBinding.inflate(layoutInflater)

        activity?.title = getString(R.string.second_fragment)

        changeOnBackPressedNavigationLogic()
        return binding.root
    }

    companion object {
        const val SECOND_FRAGMENT_TAG = "SECOND_FRAGMENT_TAG"
        fun getInstance(bundle: Bundle?): SecondFragment {
            val secondFragment = SecondFragment()
            secondFragment.arguments = bundle
            return secondFragment
        }
    }

    private fun changeOnBackPressedNavigationLogic(){
        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    parentFragmentManager.beginTransaction().replace(
                        Constants.containerID,
                        FirstFragment.getInstance(arguments),
                        FirstFragment.FIRST_FRAGMENT_TAG
                    ).commit()
                }
            }
        )
    }
    override fun onSaveInstanceState(outState: Bundle) {
        if(arguments!=null) {
            outState.putAll(arguments)
        }
        super.onSaveInstanceState(outState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {

        super.onViewStateRestored(savedInstanceState)
        if (savedInstanceState != null) {
            arguments = savedInstanceState
        }
    }

    override fun onResume() {
        super.onResume()

        val colorValue = arguments?.getInt(Constants.COLOR_INDEX_KEY)?.let {
            resources.getIntArray(R.array.iv_colors_array)[it]
        }
        if (colorValue != null) {
            binding.mainScreen.setBackgroundColor(colorValue)
        }
    }
}