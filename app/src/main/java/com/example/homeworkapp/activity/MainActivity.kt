package com.example.homeworkapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.homeworkapp.R
import com.example.homeworkapp.fragment.FirstFragment

class   MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.popBackStack()

        actionBar?.title = "actionBar"

        supportFragmentManager.beginTransaction()
            .replace(
                R.id.fragment_container_primary,
                FirstFragment.getInstance(),
                "breed"
            )
            .commit()
    }
}