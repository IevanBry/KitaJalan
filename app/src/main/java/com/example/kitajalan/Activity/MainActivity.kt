package com.example.kitajalan.Activity

import Adapter.TrendsAdapter
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kitajalan.R
import domain.TrendsDomain

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var trendsAdapter: TrendsAdapter
    private lateinit var trendsList: ArrayList<TrendsDomain>

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // Inisialisasi RecyclerView
        recyclerView = findViewById(R.id.recycler)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        // Mengambil data untuk ditampilkan
        trendsList = ArrayList()
        loadTrendsData()

        // Mengatur adapter untuk RecyclerView
        trendsAdapter = TrendsAdapter(trendsList, this)
        recyclerView.adapter = trendsAdapter


        val textView : TextView = findViewById(R.id.WelcomeText)
        val sharedPreferences : SharedPreferences = getSharedPreferences("userPrefs", Context.MODE_PRIVATE)
        val usernameVal = sharedPreferences.getString("username", null)

        val btnWebView: Button = findViewById(R.id.btnWebView)
        val btnLogout: Button = findViewById(R.id.logout)

        textView.setText("Selamat datang " + usernameVal.toString())

        btnWebView.setOnClickListener{
            val i = Intent(this, WebViewActivity::class.java)
            startActivity(i)
        }

        btnLogout.setOnClickListener{
            val editor = sharedPreferences.edit()
            editor.clear()
            editor.apply()

            val i = Intent(this, LoginActivity::class.java)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(i)
            finish()
        }
    }
    private fun loadTrendsData() {
        trendsList.add(TrendsDomain("Bali", "Bali merupakan pantai yang indah", "bali"))
        trendsList.add(TrendsDomain("Judul 2", "Subtitle 2", "bali"))
        trendsList.add(TrendsDomain("Judul 3", "Subtitle 3", "bali"))
        // Tambahkan lebih banyak data sesuai kebutuhan
    }
}