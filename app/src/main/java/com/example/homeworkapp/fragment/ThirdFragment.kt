package com.example.homeworkapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.example.homeworkapp.R
import com.example.homeworkapp.databinding.FragmentThirdBinding
import com.example.homeworkapp.util.Constants
import com.example.homeworkapp.fragment.ThirdFragment as ThirdFragment1

class ThirdFragment: Fragment() {

    private lateinit var binding: FragmentThirdBinding

    private var colorIndex = 0

    private var isNavigatingBack = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentThirdBinding.inflate(layoutInflater)

        activity?.title = getString(R.string.third_fragment)

        changeOnBackPressedNavigationLogic()
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun saveValueToBundle(colorIndex: Int) {
        arguments?.apply {
            putInt(Constants.COLOR_INDEX_KEY, colorIndex)
        }
    }

    private fun initViews() {
        val randomNumberValue = arguments?.getInt(Constants.RANDOM_NUMBER_KEY).toString()
        if (randomNumberValue != "null" && randomNumberValue != "0") {
            binding.tvRandomNumber.text = randomNumberValue
        }
    }

    companion object {
        const val THIRD_FRAGMENT_TAG = "THIRD_FRAGMENT_TAG"
        fun getInstance(bundle: Bundle?): ThirdFragment1 {
            val thirdFragment = ThirdFragment1()
            thirdFragment.arguments = bundle
            return thirdFragment
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        if (arguments != null) {
            outState.putAll(arguments)
        }
        super.onSaveInstanceState(outState)
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

    override fun onStop() {
        super.onStop()
        if (!isNavigatingBack) {
            colorIndex++
            colorIndex %= resources.getIntArray(R.array.iv_colors_array).size
            saveValueToBundle(colorIndex)
        }
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if (savedInstanceState != null) {
            this.arguments = savedInstanceState
        }
    }

    private fun changeOnBackPressedNavigationLogic() {
        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    isNavigatingBack = true
                    parentFragmentManager.beginTransaction().replace(
                        Constants.containerID,
                        SecondFragment.getInstance(arguments),
                        SecondFragment.SECOND_FRAGMENT_TAG
                    ).commit()
                }
            }
        )
    }
}
