package com.example.movies.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.movies.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*if (savedInstanceState == null) {
            val fragment = MoviesFragment.newInstance()
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container_view, fragment)
                .commit()
        }*/

    }
}