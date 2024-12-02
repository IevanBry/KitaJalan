package com.example.kitajalan.Activity.basic_api.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kitajalan.Activity.basic_api.data.repository.SerpApiRepository
import com.example.kitajalan.Activity.basic_api.ui.viewModel.DestinationViewModel

class DestinationViewModelFactory(
    private val repository: SerpApiRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DestinationViewModel::class.java)) {
            return DestinationViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
