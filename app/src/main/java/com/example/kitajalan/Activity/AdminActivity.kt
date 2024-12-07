package com.example.kitajalan.Activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.kitajalan.Activity.fragment.AdminDestinationFragment
import com.example.kitajalan.Activity.fragment.MainFragment
import com.example.kitajalan.Activity.fragment.MessageFragment
import com.example.kitajalan.Activity.fragment.MoreFragment
import com.example.kitajalan.Activity.fragment.SearchFragment
import com.example.kitajalan.Activity.fragment.SettingsFragment
import com.example.kitajalan.R
import com.example.kitajalan.databinding.ActivityAdminBinding
import com.example.kitajalan.databinding.ActivityMainBinding

class AdminActivity : AppCompatActivity() {

    private var _binding: ActivityAdminBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        _binding = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }

        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> replaceFragment(AdminDestinationFragment())
                R.id.search -> replaceFragment(SearchFragment())
                R.id.settings -> replaceFragment(SettingsFragment())
                else -> {

                }
            }
            true
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, AdminDestinationFragment())
            .commit()
    }

    private fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}