package com.example.composition.presentation.screens.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.composition.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        initStartFragment()
    }

//    private fun initStartFragment() {
//        supportFragmentManager.beginTransaction()
//            .replace(R.id.main_activity_holder, WelcomeFragment.newInstance())
//            .commit()
//    }
}