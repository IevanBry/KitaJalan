package com.example.kitajalan.Activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.kitajalan.R
import androidx.fragment.app.Fragment
import com.example.kitajalan.Activity.fragment.MainFragment
import com.example.kitajalan.Activity.fragment.MoreFragment
import com.example.kitajalan.Activity.fragment.SearchFragment
import com.example.kitajalan.Activity.fragment.SettingsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigationView)

        bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> replaceFragment(MainFragment())
                R.id.search-> replaceFragment(SearchFragment())
                R.id.message-> replaceFragment(MessageFragment())
                R.id.settings -> replaceFragment(SettingsFragment())
                R.id.more -> replaceFragment(MoreFragment())
                else -> {

                }
            }
            true
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, MainFragment())
            .commit()

//        val btnWebView: Button = findViewById(R.id.btnWebView)
//        val btnLogout: Button = findViewById(R.id.logout)

//        btnWebView.setOnClickListener{
//            val i = Intent(this, WebViewActivity::class.java)
//            startActivity(i)
//        }
//
//        btnLogout.setOnClickListener{
//            val editor = sharedPreferences.edit()
//            editor.clear()
//            editor.apply()
//
//            val i = Intent(this, LoginActivity::class.java)
//            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//            startActivity(i)
//            finish()
//        }
//    }
    }
    private fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container,fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}