package com.example.sobesgbusmmap.filmApp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.sobesgbusmmap.filmApp.view.FilmListFragment
import com.example.sobesgbusmmap.databinding.ActivityMainBinding
import com.example.sobesgbusmmap.R

class FilmMainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.container, FilmListFragment()
            ).commit()
    }
}