package com.example.sobesgbusmmap.pulseApp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.sobesgbusmmap.R
import com.example.sobesgbusmmap.databinding.PulseActivityMainBinding
import com.example.sobesgbusmmap.pulseApp.view.PulseFragment

class PulseMainActivity : AppCompatActivity() {

    private lateinit var binding: PulseActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = PulseActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction().replace(R.id.container, PulseFragment())
            .commit()
    }
}