package com.example.kitajalan.Activity

import Adapter.PagerAdapter
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.kitajalan.Activity.fragment.Welcome1Fragment
import com.example.kitajalan.Activity.fragment.Welcome2Fragment
import com.example.kitajalan.Activity.fragment.Welcome3Fragment
import com.example.kitajalan.R
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator

class WelcomeScreenActivity : AppCompatActivity() {
    private lateinit var viewPager : ViewPager2
    private lateinit var dotsIndicator : DotsIndicator
    private lateinit var btnNext : Button
    private lateinit var btnSkip : Button
    private lateinit var btnBack : Button
    private lateinit var sharedPrefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_welcome_screen)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        sharedPrefs = getSharedPreferences("userPrefs", Context.MODE_PRIVATE)
        val isWelcome = sharedPrefs.getString("isWelcome",null)

        if(isWelcome == "1"){
            val i = Intent(this, LoginActivity::class.java)
            startActivity(i)
            finish()
        }

        viewPager = findViewById(R.id.slideViewPager)
        dotsIndicator = findViewById(R.id.dotIndicator)
        btnNext = findViewById(R.id.nextButton)
        btnBack = findViewById(R.id.backButton)
        btnSkip = findViewById(R.id.skipButton)

        val fragmentList = listOf(Welcome1Fragment(), Welcome2Fragment(), Welcome3Fragment())
        val adapter = PagerAdapter(this, fragmentList)
        viewPager.adapter = adapter

        dotsIndicator.attachTo(viewPager)

        btnNext.setOnClickListener {
            if(viewPager.currentItem < fragmentList.size -1) {
                viewPager.currentItem += 1
            } else {
                finishWelcomeScreen()
            }
        }

        btnBack.setOnClickListener {
            if (viewPager.currentItem > 0) {
                viewPager.currentItem -= 1
            }
        }

        btnSkip.setOnClickListener {
            finishWelcomeScreen()
        }

        viewPager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position == fragmentList.size - 1) {
                    btnNext.text = "Finish"
                    btnSkip.visibility = View.GONE
                } else if (position == 1) {
                    btnNext.text = "Next"
                    btnSkip.visibility = View.VISIBLE
                    btnBack.visibility = View.VISIBLE
                } else {
                    btnNext.text = "Next"
                    btnSkip.visibility = View.VISIBLE
                    btnBack.visibility = View.GONE
                }
            }
        })
    }

    private fun finishWelcomeScreen() {
        val editor = sharedPrefs.edit()
        editor.putString("isWelcome", "1")
        editor.apply()

        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}