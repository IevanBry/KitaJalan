package com.example.kitajalan.Activity.basic_api.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class DestinationViewModelFactory<T: ViewModel>(
    private val viewModelClass: Class<T>,
    private val creator: () -> T
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(viewModelClass)) {
            @Suppress("UNCHECKED_CAST")
            return creator() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}