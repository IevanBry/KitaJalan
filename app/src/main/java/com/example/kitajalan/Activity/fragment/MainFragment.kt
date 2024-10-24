package com.example.kitajalan.Activity.fragment

import Adapter.TrendsAdapter
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kitajalan.R
import domain.TrendsDomain

class MainFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var trendsAdapter: TrendsAdapter
    private lateinit var trendsList: ArrayList<TrendsDomain>
    private lateinit var welcomeTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_main, container, false)

        // Inisialisasi RecyclerView dan TextView di sini
        recyclerView = view.findViewById(R.id.recycler)
        welcomeTextView = view.findViewById(R.id.WelcomeText)

        // Mengatur layout manager untuk RecyclerView
        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        // Mengambil data untuk ditampilkan di RecyclerView
        trendsList = ArrayList()
        loadTrendsData()

        // Mengatur adapter untuk RecyclerView
        trendsAdapter = TrendsAdapter(trendsList, requireContext())
        recyclerView.adapter = trendsAdapter

        // Mengambil data dari SharedPreferences
        val sharedPreferences: SharedPreferences = requireContext().getSharedPreferences("userPrefs", Context.MODE_PRIVATE)
        val usernameVal = sharedPreferences.getString("username", "Guest")

        // Menampilkan username di TextView
        welcomeTextView.text = usernameVal.toString().replaceFirstChar { it.uppercase() }

        return view
    }

    // Fungsi untuk memuat data tren
    private fun loadTrendsData() {
        trendsList.add(TrendsDomain("Bali", "Bali merupakan pantai yang indah", "bali"))
        trendsList.add(TrendsDomain("Judul 2", "Subtitle 2", "bali"))
        trendsList.add(TrendsDomain("Judul 3", "Subtitle 3", "bali"))
    }
}
