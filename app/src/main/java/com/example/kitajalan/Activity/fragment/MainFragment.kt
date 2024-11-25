package com.example.kitajalan.Activity.fragment

import Adapter.AutoSliderAdapter
import Adapter.GridAdapter
import Adapter.GridItem
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
import androidx.viewpager2.widget.ViewPager2
import com.example.kitajalan.R
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator
import Domain.TrendsDomain
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.kitajalan.Activity.basic_api.data.network.RetrofitInstance
import com.example.kitajalan.Activity.basic_api.data.repository.UserRepository
import com.example.kitajalan.Activity.basic_api.ui.viewModel.UserViewModel
import com.example.kitajalan.Activity.basic_api.utils.ViewModelFactory
import com.example.kitajalan.databinding.FragmentMainBinding

class MainFragment : Fragment() {
    private var _binding : FragmentMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView
    private lateinit var trendsAdapter: TrendsAdapter
    private lateinit var trendsList: ArrayList<TrendsDomain>
    private lateinit var welcomeTextView: TextView
    private lateinit var seeAll: TextView
    private lateinit var gridRecyclerView: RecyclerView

    private val userViewModel: UserViewModel by lazy {
        val repository = UserRepository(RetrofitInstance.getJsonPlaceHolderApi())
        ViewModelProvider(
            this,
            ViewModelFactory(UserViewModel::class.java) { UserViewModel(repository) }
        )[UserViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMainBinding.inflate(inflater, container, false)

//        val view = inflater.inflate(R.layout.fragment_main, container, false)
        setupAutoSlider(binding)
//        setupRecyclerView(view)
        setupGridView(binding)
//        setupRecyclerView(binding)
        setupNewsHorizontalApi(binding)

//        return view
        return binding.root
    }

    private fun setupRecyclerView(binding: FragmentMainBinding) {
//        recyclerView = view.findViewById(R.id.recycler)
//        welcomeTextView = view.findViewById(R.id.WelcomeText)
//        seeAll = view.findViewById(R.id.btnSeeAll)
        val recyclerView = binding.recycler
        val welcomeTextView = binding.WelcomeText
        val seeAll = binding.btnSeeAll

        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        trendsList = ArrayList()
        loadTrendsData()

        trendsAdapter = TrendsAdapter(trendsList, requireContext())
        recyclerView.adapter = trendsAdapter

        val sharedPreferences: SharedPreferences = requireContext().getSharedPreferences("userPrefs", Context.MODE_PRIVATE)
        val usernameVal = sharedPreferences.getString("username", "Guest")
        welcomeTextView.text = usernameVal?.replaceFirstChar { it.uppercase() }

        seeAll.setOnClickListener {
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, SeeAllFragment())
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }

    private fun loadTrendsData() {
        trendsList.add(TrendsDomain("Bali", "Bali merupakan pantai yang indah", "bali",
            "Bali is a beautiful Indonesian island known for its stunning beaches, vibrant culture, " +
                    "and lush rice terraces. It's a popular destination for tourists seeking relaxation, " +
                    "adventure, and unique experiences.", "500.000"))
        trendsList.add(TrendsDomain("Asia Heritage", "Subtitle 2", "asia_farm", "Asia heritage merupakan wisata yang ada di Pekanbaru", "400.000"))
        trendsList.add(TrendsDomain("Judul 3", "Subtitle 3", "borobudur_2", "Description for Judul 3, with highlights and points of interest.", "800.000"))
    }

    private fun setupAutoSlider(binding: FragmentMainBinding) {
        val images = listOf(
            R.drawable.image_slider3,
            R.drawable.slider_image2,
            "https://images.unsplash.com/photo-1516117172878-fd2c41f4a759?w=1024"
        )
//        val viewPager : ViewPager2 = view.findViewById(R.id.auto_slider)
//        val dotsIndicator : WormDotsIndicator = view.findViewById(R.id.worn_indicator)

//        viewPager.adapter = AutoSliderAdapter(images, viewPager)
//        dotsIndicator.attachTo(viewPager)

        binding.autoSlider.adapter = AutoSliderAdapter(images, binding.autoSlider)
        binding.wornIndicator.attachTo(binding.autoSlider)
    }

    private fun setupGridView(binding: FragmentMainBinding){
//        gridRecyclerView = view.findViewById(R.id.recycler_grid)
        val gridRecyclerView = binding.recyclerGrid

        val gridItems = listOf(
            GridItem("Beach",R.drawable.cat1),
            GridItem("Camps",R.drawable.cat2),
            GridItem("Forest",R.drawable.cat3),
            GridItem("Desert",R.drawable.cat4),
            GridItem("Mountain",R.drawable.cat5),
            GridItem("Hiking",R.drawable.cat5)
        )
        gridRecyclerView.layoutManager = androidx.recyclerview.widget.GridLayoutManager(context, 3)
        val gridAdapter = GridAdapter(gridItems) { selectedItem ->
            handleGridItemClick(selectedItem)
        }
        gridRecyclerView.adapter = gridAdapter
    }
    private fun handleGridItemClick(item: GridItem) {
        when (item.title) {
            "Menu1" -> {
            }
            "Menu2" -> {
            }
            "Menu3" -> {
            }
            else -> {
            }
        }
    }
    private fun setupNewsHorizontalApi(binding: FragmentMainBinding) {
        val adapter = TrendsAdapter(ArrayList(), requireContext())
        userViewModel.getUsers().observe(viewLifecycleOwner) { response ->
            if (response != null) {
                Log.d("MainFragment", "Received response: ${response.size} items")
                val newsItems = response.map { data ->
                    TrendsDomain(
                        picAddress = "https://images.unsplash.com/photo-1516117172878-fd2c41f4a759?w=1024",
                        title = data.name,
                        subtitle = "Subtitle for ${data.name}",
                        description = "Description for ${data.name}",
                        price = "Price for ${data.name}",
                        isFavorite = false
                    )
                }
                adapter.updateData(ArrayList(newsItems))
            }
        }

        // Set the adapter to the RecyclerView
        binding.recycler.adapter = adapter
        binding.recycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }

}
