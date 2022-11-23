package com.example.homeworkapp.activity

import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.homeworkapp.R
import com.example.homeworkapp.alarmlogic.receiver.AirplaneReceiver
import com.example.homeworkapp.databinding.ActivityMainBinding
import com.example.homeworkapp.fragment.MainFragment

class MainActivity: AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private var broadcastReceiver: AirplaneReceiver? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setAirplaneReceiver()
        setContentView(binding.root)
    }

    override fun onDestroy() {
        removeAirplaneReceiver()
        super.onDestroy()
    }

    private fun callOnNetworkStateChanged(isConnected: Boolean) {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.container)

        navHostFragment?.childFragmentManager?.fragments?.forEach {
            if (it is MainFragment) {
                it.onNetworkStateChanged(isConnected)
            }
        }
    }

    private fun setAirplaneReceiver() {
        broadcastReceiver = AirplaneReceiver { isAirplaneModeEnabled ->
            callOnNetworkStateChanged(!isAirplaneModeEnabled)
        }
        val intentFilter = IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED)
        this.registerReceiver(broadcastReceiver!!, intentFilter)
    }

    private fun removeAirplaneReceiver() {
        broadcastReceiver?.let {
            this.unregisterReceiver(it)
        }
    }

}