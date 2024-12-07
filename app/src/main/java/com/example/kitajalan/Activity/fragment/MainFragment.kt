package com.example.kitajalan.Activity.fragment

import Adapter.AutoSliderAdapter
import Adapter.DestinationAdapter
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
import com.example.kitajalan.R
import com.example.kitajalan.Activity.basic_api.data.model.TrendsDomain
import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.example.kitajalan.Activity.basic_api.data.network.RetrofitInstance
import com.example.kitajalan.Activity.basic_api.data.network.RetrofitInstance.getSerpApi
import com.example.kitajalan.Activity.basic_api.data.repository.SerpApiRepository
import com.example.kitajalan.Activity.basic_api.data.repository.UserRepository
import com.example.kitajalan.Activity.basic_api.ui.viewModel.DestinationViewModel
import com.example.kitajalan.Activity.basic_api.ui.viewModel.Resource
import com.example.kitajalan.Activity.basic_api.ui.viewModel.UserViewModel
import com.example.kitajalan.Activity.basic_api.utils.DestinationViewModelFactory
import com.example.kitajalan.Activity.basic_api.utils.ViewModelFactory
import com.example.kitajalan.databinding.FragmentMainBinding

class MainFragment : Fragment() {
    private var _binding : FragmentMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var trendsAdapter: TrendsAdapter
    private lateinit var destinationAdapter: DestinationAdapter

    private var trendsList: List<TrendsDomain> = emptyList()

    private val viewModel by lazy {
        val repository = SerpApiRepository(getSerpApi())
        ViewModelProvider(
            this,
            DestinationViewModelFactory(DestinationViewModel::class.java) { DestinationViewModel(repository) }
        )[DestinationViewModel::class.java]
    }

    private val userViewModel: UserViewModel by activityViewModels {
        ViewModelFactory(UserViewModel::class.java) {
            val repository = UserRepository(RetrofitInstance.getJsonPlaceHolderApi())
            UserViewModel(repository)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)

        val sharedPreferences: SharedPreferences = requireContext().getSharedPreferences("userPrefs", Context.MODE_PRIVATE)
        val usernameVal = sharedPreferences.getString("username", "Guest")
        binding.username.text = usernameVal?.replaceFirstChar { it.uppercase() }

        setupAutoSlider(binding)
        setupGridView(binding)
        setupNewsHorizontalApi(binding)
        setupRecyclerViewAndLoadData(binding)

        return binding.root
    }

    private fun setupAutoSlider(binding: FragmentMainBinding) {
        val images = listOf(
            R.drawable.image_slider3,
            R.drawable.slider_image2,
            "https://images.unsplash.com/photo-1516117172878-fd2c41f4a759?w=1024"
        )
        binding.autoSlider.adapter = AutoSliderAdapter(images, binding.autoSlider)
        binding.wornIndicator.attachTo(binding.autoSlider)
    }

    private fun setupGridView(binding: FragmentMainBinding) {
        val gridRecyclerView = binding.recyclerGrid

        val gridItems = listOf(
            GridItem("Beach", R.drawable.cat1),
            GridItem("Camps", R.drawable.cat2),
            GridItem("Forest", R.drawable.cat3),
            GridItem("Desert", R.drawable.cat4),
            GridItem("Mountain", R.drawable.cat5),
            GridItem("Hiking", R.drawable.cat5)
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
        val adapter = TrendsAdapter(emptyList(), requireContext()) { title ->
        }

        userViewModel.getUsers(requireContext())
        userViewModel.data.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Empty -> {
                    binding.emptyNewHoriList.root.visibility = View.VISIBLE
                    binding.loadingNewHoriList.root.visibility = View.GONE
                    binding.errorNewHorilist.root.visibility = View.GONE
                    binding.recycler.visibility = View.GONE
                    binding.emptyNewHoriList.emptyMessage.text = resource.message
                    Log.d("Data User", "Data Kosong. (${resource.message})")
                }
                is Resource.Error -> {
                    binding.emptyNewHoriList.root.visibility = View.GONE
                    binding.loadingNewHoriList.root.visibility = View.GONE
                    binding.errorNewHorilist.root.visibility = View.VISIBLE
                    binding.recycler.visibility = View.GONE
                    binding.errorNewHorilist.errorMessage.text = resource.message
                    binding.errorNewHorilist.retryButton.setOnClickListener {
                        userViewModel.getUsers(requireContext(), true)
                    }
                    Log.d("Data User", resource.message.toString())
                }
                is Resource.Loading -> {
                    binding.emptyNewHoriList.root.visibility = View.GONE
                    binding.loadingNewHoriList.root.visibility = View.VISIBLE
                    binding.errorNewHorilist.root.visibility = View.GONE
                    binding.recycler.visibility = View.GONE
                    Log.d("Data User", "Mohon Tunggu..")
                }
                is Resource.Success -> {
                    binding.emptyNewHoriList.root.visibility = View.GONE
                    binding.loadingNewHoriList.root.visibility = View.GONE
                    binding.errorNewHorilist.root.visibility = View.GONE
                    binding.recycler.visibility = View.VISIBLE
                    Log.d("Data User", "Data berhasil didapatkan")
                }
            }
        }
        binding.recycler.adapter = adapter
        binding.recycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun setupRecyclerViewAndLoadData(binding: FragmentMainBinding) {
        destinationAdapter = DestinationAdapter(emptyList())
        binding.recyclerDestinasi.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.recyclerDestinasi.adapter = destinationAdapter

        viewModel.destinations.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    binding.loadingDestination.root.visibility = View.VISIBLE
                    binding.recyclerDestinasi.visibility = View.GONE
                    binding.emptyDestination.root.visibility = View.GONE
                    binding.errorDestination.root.visibility = View.GONE
                }

                is Resource.Success -> {
                    binding.loadingDestination.root.visibility = View.GONE
                    binding.recyclerDestinasi.visibility = View.VISIBLE
                    binding.emptyDestination.root.visibility = View.GONE
                    binding.errorDestination.root.visibility = View.GONE
                    destinationAdapter.updateData(resource.data ?: emptyList())
                }

                is Resource.Error -> {
                    binding.loadingDestination.root.visibility = View.GONE
                    binding.recyclerDestinasi.visibility = View.GONE
                    binding.emptyDestination.root.visibility = View.GONE
                    binding.errorDestination.root.visibility = View.VISIBLE
                    binding.errorDestination.retryButton.setOnClickListener {
                        viewModel.fetchDestinations(requireContext(), "Bali")
                    }
                }

                is Resource.Empty -> {
                    binding.loadingDestination.root.visibility = View.GONE
                    binding.recyclerDestinasi.visibility = View.GONE
                    binding.emptyDestination.root.visibility = View.VISIBLE
                    binding.errorDestination.root.visibility = View.GONE
                }
            }
        }
        viewModel.fetchDestinations(requireContext(), "Bali")
    }
}