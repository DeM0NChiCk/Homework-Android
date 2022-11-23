package com.example.homeworkapp.fragment

import android.content.Context
import android.os.Bundle
import android.os.SystemClock
import android.provider.Settings
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.homeworkapp.R
import com.example.homeworkapp.alarmlogic.setter.AlarmSetter
import com.example.homeworkapp.databinding.FragmentMainBinding
import com.google.android.material.textfield.TextInputEditText

class MainFragment: Fragment(R.layout.fragment_main) {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private var isConnected = true
    private val alarmSetter by lazy { AlarmSetter(requireContext()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentMainBinding.bind(view)

        with(binding) {
            ntfCheckbox.setOnCheckedChangeListener { _, isChecked ->
                longTextNtfInfo.isEnabled = isChecked
            }
            setTextWatchers()

            btnAlarmNtfSet.setOnClickListener {
                with(binding) {
                    alarmSetter.setAlarm(
                        editTextNtfTitle.text.toString(),
                        editTextNtfInfo.text.toString(),
                        editTextNtfTime.text.toString().toLong(),
                        if (ntfCheckbox.isChecked)
                            editTextLongNtfInfo.text.toString()
                        else null
                    )
                }
            }

            btnAlarmNtfCancel.setOnClickListener {
                alarmSetter.cancelAlarm()
            }

            btnShowElapsedRealtime.setOnClickListener{
                Toast.makeText(
                    requireContext(),
                    "ElapsedRealtime: ${SystemClock.elapsedRealtime()} ms",
                    Toast.LENGTH_LONG
                ).show()
            }

            if (isAirplaneModeOn(requireContext())) {
                onNetworkStateChanged(false)
            }

        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    fun onNetworkStateChanged(isConnected: Boolean) {
        this.isConnected = isConnected
        toggleAllInputs(isConnected)
    }

    private fun toggleAllInputs(isEnabled: Boolean) {
        with(binding) {
            textNtfTitle.isEnabled = isEnabled
            textNtfInfo.isEnabled = isEnabled
            textNtfTime.isEnabled = isEnabled
            ntfCheckbox.isEnabled = isEnabled
            btnAlarmNtfCancel.isEnabled = isEnabled
            btnShowElapsedRealtime.isEnabled = isEnabled

            if (ntfCheckbox.isChecked) {
                longTextNtfInfo.isEnabled = isEnabled
            }

            if (isEnabled) {
                checkSetAlarmButton()
            } else {
                btnAlarmNtfSet.isEnabled = false
            }
        }
    }

    private fun setTextWatchers() {
        val textWatcher: TextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                checkSetAlarmButton()
            }
        }

        with(binding) {
            editTextNtfTitle.addTextChangedListener(textWatcher)
            editTextNtfInfo.addTextChangedListener(textWatcher)
            editTextNtfTime.addTextChangedListener(textWatcher)
        }
    }

    private fun checkSetAlarmButton() {
        with(binding) {
            if (!isConnected) return

            btnAlarmNtfSet.isEnabled =
                !editTextNtfTitle.text.isNullOrBlank() &&
                        !editTextNtfInfo.text.isNullOrBlank() &&
                        !editTextNtfTime.text.isNullOrBlank()
        }
    }

    private fun isAirplaneModeOn(context: Context): Boolean {
        return Settings.Global.getInt(
            context.contentResolver,
            Settings.Global.AIRPLANE_MODE_ON, 0
        ) != 0
    }

}