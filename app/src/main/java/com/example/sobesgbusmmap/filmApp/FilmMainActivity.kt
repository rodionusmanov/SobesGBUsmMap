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
//        setSupportActionBar(findViewById(R.id.main_toolbar))

        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.container, FilmListFragment()
            ).commit()
    }

    /*override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.map_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }*/

    /*override fun onBackPressed() {
        val fragment = this.supportFragmentManager.findFragmentById(R.id.container)
        (fragment as IonBackPressed).onBackPressed().not().let {
            super.onBackPressed()
        }
    }*/
}