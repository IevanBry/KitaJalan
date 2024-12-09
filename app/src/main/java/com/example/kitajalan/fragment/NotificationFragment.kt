package com.example.kitajalan.fragment

import Adapter.NewsHorizontalAdapter
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kitajalan.basic_api.data.local.database.AppDatabase
import com.example.kitajalan.basic_api.data.local.entity.ProductEntity
import com.example.kitajalan.basic_api.data.model.NewsHorizontalModel
import com.example.kitajalan.basic_api.data.network.RetrofitInstance
import com.example.kitajalan.basic_api.data.repository.ProductRepository
import com.example.kitajalan.basic_api.ui.viewModel.ProductViewModel
import com.example.kitajalan.basic_api.ui.viewModel.Resource
import com.example.kitajalan.basic_api.utils.ViewModelFactory
import com.example.kitajalan.databinding.FragmentNotificationBinding

class NotificationFragment : Fragment() {
    private var _binding : FragmentNotificationBinding? = null
    private val binding get() = _binding!!

    private  val productViewModel: ProductViewModel by activityViewModels {
        ViewModelFactory(ProductViewModel::class.java){
            val repository = ProductRepository(
                RetrofitInstance.getCrudApi(),
                AppDatabase.getDatabase(requireContext()).productDao()
            )
            ProductViewModel(repository)
        }
    }

    private lateinit var adapter : NewsHorizontalAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNotificationBinding.inflate(inflater, container, false)

        adapter = NewsHorizontalAdapter(mutableListOf())

        binding.productList.adapter = adapter
        binding.productList.layoutManager = LinearLayoutManager(this.context)

        getProductLocal()
        createProductLocal()

        return binding.root
    }

    private fun getProductLocal() {
        productViewModel.getProductsLocal()
        productViewModel.dataLocal.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    Log.d("Get Product Room","Memulai pengambilan data lokal Product")
                    binding.emptyProduct.root.visibility = View.GONE
                    binding.loadingProduct.root.visibility = View.VISIBLE
                    binding.errorProduct.root.visibility = View.GONE
                    binding.productList.visibility = View.GONE
                }

                is Resource.Success -> {
                    Log.d("Get Product Room","Data lokal Product berhasil diambil")

                    binding.emptyProduct.root.visibility = View.GONE
                    binding.loadingProduct.root.visibility = View.GONE
                    binding.errorProduct.root.visibility = View.GONE
                    binding.productList.visibility = View.VISIBLE

                    val productItem = resource.data!!.mapIndexed() { index, data ->
                        NewsHorizontalModel(
                            "https://images.unsplash.com/photo-1530224264768-7ff8c1789d79?w=1024",
                            data.name
                        )
                    }
                    adapter.updateData(productItem)
                }

                is Resource.Empty -> {
                    Log.e("Get Product Room",resource.message.toString())
                    binding.emptyProduct.root.visibility = View.VISIBLE
                    binding.loadingProduct.root.visibility = View.GONE
                    binding.errorProduct.root.visibility = View.GONE
                    binding.productList.visibility = View.GONE

                    binding.emptyProduct.emptyMessage.text = resource.message
                }

                is Resource.Error -> {
                    Log.e("Get Product Room",resource.message.toString())
                    binding.emptyProduct.root.visibility = View.GONE
                    binding.loadingProduct.root.visibility = View.GONE
                    binding.errorProduct.root.visibility = View.VISIBLE
                    binding.productList.visibility = View.GONE

                    binding.errorProduct.errorMessage.text = resource.message

                    binding.errorProduct.retryButton.setOnClickListener {
                        productViewModel.getProductsLocal(true)
                    }
                }
            }
        }
    }

    private fun createProductLocal() {
        val name = "Motor Kawasaki"
        val desc = "Motor ini sangat laju"
        val price = 5200000

        val products = ProductEntity(name = name, description = desc, price = price)

        productViewModel.createProductLocal(products)
        productViewModel.createLocalStatus.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    binding.emptyProduct.root.visibility = View.GONE
                    binding.loadingProduct.root.visibility = View.VISIBLE
                    binding.errorProduct.root.visibility = View.GONE
                    binding.productList.visibility = View.GONE
                    Log.d("Insert Product Room","Memulai penambahan data lokal Product")
                }

                is Resource.Success -> {
                    binding.emptyProduct.root.visibility = View.GONE
                    binding.loadingProduct.root.visibility = View.GONE
                    binding.errorProduct.root.visibility = View.GONE
                    binding.productList.visibility = View.VISIBLE

                    Log.d("Insert Product Room","Data lokal Product berhasil ditambahkan!")

                    /*Lanjutkan dengan menampilkan SnackBar atau Alert*/
                }

                is Resource.Error -> {
                    binding.emptyProduct.root.visibility = View.GONE
                    binding.loadingProduct.root.visibility = View.GONE
                    binding.errorProduct.root.visibility = View.VISIBLE
                    binding.productList.visibility = View.GONE

                    binding.errorProduct.errorMessage.text = resource.message

                    binding.errorProduct.retryButton.setOnClickListener {
                        productViewModel.getProductsLocal(true)
                    }
                    Log.e("Insert Product Room",resource.message.toString())


                    /*Lanjutkan dengan menampilkan SnackBar atau Alert*/
                }

                else -> {}
            }
        }
    }

}