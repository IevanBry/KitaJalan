package com.example.kitajalan.Activity.fragment

import Adapter.TrendsAdapter
import Domain.TrendsDomain
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kitajalan.Activity.basic_api.data.model.ProductPostRequest
import com.example.kitajalan.Activity.basic_api.data.network.RetrofitInstance
import com.example.kitajalan.Activity.basic_api.data.repository.ProductRepository
import com.example.kitajalan.Activity.basic_api.data.repository.UserRepository
import com.example.kitajalan.Activity.basic_api.ui.viewModel.ProductViewModel
import com.example.kitajalan.Activity.basic_api.ui.viewModel.Resource
import com.example.kitajalan.Activity.basic_api.ui.viewModel.UserViewModel
import com.example.kitajalan.Activity.basic_api.utils.ViewModelFactory
import com.example.kitajalan.R
import com.example.kitajalan.databinding.FragmentInformationBinding
import com.example.kitajalan.databinding.FragmentNotificationBinding
import com.google.android.material.snackbar.Snackbar

class NotificationFragment : Fragment() {
    private var _binding : FragmentNotificationBinding? = null
    private val binding get() = _binding!!

    private  val productViewModel: ProductViewModel by activityViewModels {
        ViewModelFactory(ProductViewModel::class.java){
            val repository = ProductRepository(RetrofitInstance.getCrudApi())
            ProductViewModel(repository)
        }
    }
    private lateinit var adapter: TrendsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentNotificationBinding.inflate(inflater, container, false)

        adapter = TrendsAdapter(ArrayList(),requireContext())
        binding.productList.adapter = adapter
        binding.productList.layoutManager = LinearLayoutManager(this.context)

        getProduct()

        return binding.root
//        return inflater.inflate(R.layout.fragment_notification, container, false)
    }

    private fun getProduct(){
        productViewModel.getProducts(requireContext())
        productViewModel.data.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Empty -> {
                    Log.d("Data Product", "Data Kosong. (${resource.message})")
                    binding.emptyProduct.root.visibility = View.VISIBLE
                    binding.loadingProduct.root.visibility = View.GONE
                    binding.errorProduct.root.visibility = View.GONE
                    binding.productList.visibility = View.GONE

                    binding.emptyProduct.emptyMessage.text = resource.message
                }
                is Resource.Error -> {
                    Log.e("Data User", resource.message.toString())
                    binding.emptyProduct.root.visibility = View.GONE
                    binding.loadingProduct.root.visibility = View.GONE
                    binding.errorProduct.root.visibility = View.VISIBLE
                    binding.productList.visibility = View.GONE

                    binding.errorProduct.errorMessage.text = resource.message

                    binding.errorProduct.retryButton.setOnClickListener{
                        productViewModel.getProducts(requireContext(),true)
                    }
                }
                is Resource.Loading -> {
                    Log.d("Data User", "Mohon Tunggu...")
                    binding.emptyProduct.root.visibility = View.GONE
                    binding.loadingProduct.root.visibility = View.VISIBLE
                    binding.errorProduct.root.visibility = View.GONE
                    binding.productList.visibility = View.GONE
                }
                is Resource.Success -> {
                    Log.d("Data User", "Data berhasil didapatkan")
                    binding.emptyProduct.root.visibility = View.GONE
                    binding.loadingProduct.root.visibility = View.GONE
                    binding.errorProduct.root.visibility = View.GONE
                    binding.productList.visibility = View.VISIBLE

                    val productItem = resource.data!!.items.mapIndexed { index, data ->
                        TrendsDomain(
                            picAddress = "https://images.unsplash.com/photo-1516117172878-fd2c41f4a759?w=1024",
                            title = data.name,
                            subtitle = "Subtitle for ${data.name}",
                            description = "Description for ${data.name}",
                            price = "Price for ${data.name}",
                                isFavorite = false
                        )
                    }
                    adapter.updateData(ArrayList(productItem))
                }
            }
        }
    }
    private fun createProduct() {
        val title = "Buku"
        val price = "5000"
        val description = "hallo"

        val products = listOf(
            ProductPostRequest(title = title, description = description, price = price)
        )
        productViewModel.createProduct(requireContext(), products)
        productViewModel.createStatus.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    // Show a loading indicator for create operation
                }

                is Resource.Success -> {
                    Snackbar.make(
                        binding.root,
                        "Product created successfully!",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }

                is Resource.Error -> {
                    Snackbar.make(
                        binding.root,
                        resource.message ?: "Failed to create product.",
                        Snackbar.LENGTH_LONG
                    ).show()
                }
                else -> {}
            }
        }
    }

}