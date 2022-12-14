package com.example.homeworkapp.activity

import android.content.Intent
import android.content.IntentFilter
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.homeworkapp.databinding.ActivityMainBinding
import com.example.homeworkapp.receiver.LocationReceiver
import com.example.homeworkapp.service.MyLocationService

class MainActivity: AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private var locationReceiver: LocationReceiver? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setLocationReceiver()
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onDestroy() {
        stopService(MyLocationService.getServiceIntent(applicationContext))
        locationReceiver?.let {
            this.unregisterReceiver(it)
        }

        super.onDestroy()
    }

    private fun setLocationReceiver() {
        locationReceiver = LocationReceiver()
        val intentFilter = IntentFilter()
        intentFilter.apply {
            addAction(LocationManager.MODE_CHANGED_ACTION)
            addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED)
        }
        this.registerReceiver(locationReceiver!!, intentFilter)
    }
}