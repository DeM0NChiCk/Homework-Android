package com.example.homeworkapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.homeworkapp.R
import com.example.homeworkapp.databinding.ActivityMainBinding
import com.example.homeworkapp.fragment.ThirdFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val containerID: Int = R.id.fragment_container_primary

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        if (savedInstanceState != null) {
            return
        }

        supportFragmentManager.beginTransaction().add(
            containerID,
            ThirdFragment.getInstance(Bundle()),
            ThirdFragment.THIRD_FRAGMENT_TAG
        )
            .commit()
    }
}