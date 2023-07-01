package com.example.sobesgbusmmap.eduApp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.sobesgbusmmap.R
import com.example.sobesgbusmmap.databinding.EduActivityMainBinding
import com.example.sobesgbusmmap.eduApp.view.classes.ClassesFragment
import com.example.sobesgbusmmap.eduApp.view.home.HomeFragment
import com.google.android.material.navigation.NavigationBarView

class EduMainActivity : AppCompatActivity() {

    private lateinit var binding: EduActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = EduActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bottomNavigationView.setOnItemSelectedListener(NavigationBarView.OnItemSelectedListener {

            when (it.itemId) {
                R.id.menu_action_home -> {
                    openFragment(HomeFragment())
                    true
                }

                R.id.menu_action_classes -> {
                    openFragment(ClassesFragment())
                    true
                }

                else -> {
                    false
                }

            }
        })
    }

    private fun openFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.edu_container, fragment
            ).commit()
    }
}