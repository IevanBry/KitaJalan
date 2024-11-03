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
    private lateinit var seeAll: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)

        recyclerView = view.findViewById(R.id.recycler)
        welcomeTextView = view.findViewById(R.id.WelcomeText)
        seeAll = view.findViewById(R.id.btnSeeAll)

        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        trendsList = ArrayList()
        loadTrendsData()

        trendsAdapter = TrendsAdapter(trendsList, requireContext())
        recyclerView.adapter = trendsAdapter

        val sharedPreferences: SharedPreferences = requireContext().getSharedPreferences("userPrefs", Context.MODE_PRIVATE)
        val usernameVal = sharedPreferences.getString("username", "Guest")

        welcomeTextView.text = usernameVal.toString().replaceFirstChar { it.uppercase() }

        seeAll.setOnClickListener {
             val transaction = parentFragmentManager.beginTransaction()
             transaction.replace(R.id.fragment_container, SeeAllFragment())
             transaction.addToBackStack(null)
             transaction.commit()
        }

        return view
    }

    private fun loadTrendsData() {
        trendsList.add(TrendsDomain("Bali", "Bali merupakan pantai yang indah", "bali",
            "Bali is a beautiful Indonesian island known for its stunning beaches, vibrant culture, " +
                    "and lush rice terraces. It's a popular destination for tourists seeking relaxation, " +
                    "adventure, and unique experiences.", "500.000"))
        trendsList.add(TrendsDomain("Asia Heritage", "Subtitle 2", "asia_farm", "Asia heritage merupakan wisata yang ada di Pekanbaru", "400.000"))
        trendsList.add(TrendsDomain("Judul 3", "Subtitle 3", "borobudur_2", "Description for Judul 3, with highlights and points of interest.", "800.000"))
    }
}
