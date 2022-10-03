package com.example.homeworkapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.homeworkapp.R
import com.example.homeworkapp.fragment.ChangesFragment
import com.example.homeworkapp.fragment.IntroductoryFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container_primary,
                IntroductoryFragment.getInstance(),
                "introductory")
            .commit()

        val fragment = supportFragmentManager.findFragmentByTag("changes")?: return
        supportFragmentManager.beginTransaction().remove(fragment).commit()
    }
}