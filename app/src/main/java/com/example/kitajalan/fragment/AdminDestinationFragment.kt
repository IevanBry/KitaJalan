package com.example.kitajalan.fragment

import Adapter.TrendsAdapter
import com.example.kitajalan.databinding.FragmentAdminDestinationBinding
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kitajalan.basic_api.data.model.TrendsDomain
import com.example.kitajalan.basic_api.data.model.TrendsPostRequest
import com.example.kitajalan.basic_api.data.network.RetrofitInstance
import com.example.kitajalan.basic_api.data.repository.TrendsRepository
import com.example.kitajalan.basic_api.ui.viewModel.Resource
import com.example.kitajalan.basic_api.ui.viewModel.TrendsViewModel
import com.example.kitajalan.basic_api.utils.ViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.example.kitajalan.databinding.BottomAddDestinationSheetLayoutBinding
import com.google.android.material.snackbar.Snackbar

class AdminDestinationFragment : Fragment() {

    private var _binding: FragmentAdminDestinationBinding? = null
    private val binding get() = _binding!!

    private lateinit var trendsAdapter: TrendsAdapter

    private val trendsViewModel: TrendsViewModel by activityViewModels {
        ViewModelFactory(TrendsViewModel::class.java) {
            val repository = TrendsRepository(RetrofitInstance.getCrudApi())
            TrendsViewModel(repository)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAdminDestinationBinding.inflate(inflater, container, false)

        trendsAdapter = TrendsAdapter(mutableListOf(), requireContext()) { uuid ->
            deleteDestination(uuid)
        }
        binding.recyclerWisata.adapter = trendsAdapter

        binding.recyclerWisata.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        getDestination()

        setupFAB()

        return binding.root
    }

    private fun getDestination() {
        trendsViewModel.getTrends(requireContext())
        trendsViewModel.data.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Empty -> {
                    Log.d("Data Wisata", "Data Kosong. (${resource.message})")
                    binding.emptyWisata.root.visibility = View.VISIBLE
                    binding.loadingWisata.root.visibility = View.GONE
                    binding.errorWisata.root.visibility = View.GONE
                    binding.recyclerWisata.visibility = View.GONE

                    binding.emptyWisata.emptyMessage.text = resource.message
                }

                is Resource.Error -> {
                    Log.e("Data User", resource.message.toString())
                    binding.emptyWisata.root.visibility = View.GONE
                    binding.loadingWisata.root.visibility = View.GONE
                    binding.errorWisata.root.visibility = View.VISIBLE
                    binding.recyclerWisata.visibility = View.GONE

                    binding.errorWisata.errorMessage.text = resource.message

                    binding.errorWisata.retryButton.setOnClickListener {
                        trendsViewModel.getTrends(requireContext(), true)
                    }
                }

                is Resource.Loading -> {
                    Log.d("Data User", "Mohon Tunggu...")
                    binding.emptyWisata.root.visibility = View.GONE
                    binding.loadingWisata.root.visibility = View.VISIBLE
                    binding.errorWisata.root.visibility = View.GONE
                    binding.recyclerWisata.visibility = View.GONE
                }

                is Resource.Success -> {
                    Log.d("Data User", "Data berhasil didapatkan")
                    binding.emptyWisata.root.visibility = View.GONE
                    binding.loadingWisata.root.visibility = View.GONE
                    binding.errorWisata.root.visibility = View.GONE
                    binding.recyclerWisata.visibility = View.VISIBLE

                    val trendsItem = resource.data!!.items
                    trendsAdapter.updateData(trendsItem)
                }
            }
        }
    }

    private fun createNewDestination(newTrend: TrendsPostRequest) {
        trendsViewModel.createTrends(requireContext(), listOf(newTrend))
        trendsViewModel.createStatus.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    Snackbar.make(
                        binding.root,
                        "Sedang menambahkan destinasi...",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }

                is Resource.Success -> {
                    Snackbar.make(
                        binding.root,
                        "Destinasi berhasil ditambahkan!",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }

                is Resource.Error -> {
                    Snackbar.make(
                        binding.root,
                        resource.message ?: "Gagal menambahkan destinasi.",
                        Snackbar.LENGTH_LONG
                    ).show()
                }

                else -> {}
            }
        }
    }
    private fun deleteDestination(uuid: String) {
        trendsViewModel.deleteTrend(requireContext(), uuid)
        trendsViewModel.deleteStatus.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    Snackbar.make(binding.root, "Menghapus destinasi...", Snackbar.LENGTH_SHORT).show()
                }

                is Resource.Success -> {
                    Snackbar.make(binding.root, "Destinasi berhasil dihapus!", Snackbar.LENGTH_SHORT).show()
                }

                is Resource.Error -> {
                    Snackbar.make(binding.root, resource.message ?: "Gagal menghapus destinasi.", Snackbar.LENGTH_LONG).show()
                }

                else -> {}
            }
        }
    }

    private fun setupFAB() {
        binding.fab.setOnClickListener {
            showBottomSheetDialog()
        }
    }

    private fun showBottomSheetDialog() {
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        val bottomSheetBinding =
            BottomAddDestinationSheetLayoutBinding.inflate(LayoutInflater.from(requireContext()))
        bottomSheetDialog.setContentView(bottomSheetBinding.root)

        bottomSheetBinding.submitButton.setOnClickListener {
            val title = bottomSheetBinding.inputTitle.text.toString().trim()
            val subtitle = bottomSheetBinding.inputSubtitle.text.toString().trim()
            val picAddress = bottomSheetBinding.inputPicAddress.text.toString().trim()
            val description = bottomSheetBinding.inputDescription.text.toString().trim()
            val price = bottomSheetBinding.inputPrice.text.toString().trim()

            val defaultImage = "https://images.unsplash.com/photo-1516117172878-fd2c41f4a759?w=1024"

            if (title.isEmpty() || subtitle.isEmpty() || description.isEmpty() || price.isEmpty()) {
                Snackbar.make(
                    binding.root,
                    "Semua field harus diisi!",
                    Snackbar.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            val finalPicAddress = if (picAddress.isEmpty()) defaultImage else picAddress

            val newTrend = TrendsPostRequest(
                title = title,
                subtitle = subtitle,
                picAddress = finalPicAddress,
                description = description,
                price = price
            )
            createNewDestination(newTrend)

            bottomSheetDialog.dismiss()
        }

        bottomSheetDialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
