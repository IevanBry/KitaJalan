package com.example.kitajalan.Activity.basic_api.ui.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kitajalan.Activity.basic_api.data.model.ApiResponse
import com.example.kitajalan.Activity.basic_api.data.model.Destination
import com.example.kitajalan.Activity.basic_api.data.repository.SerpApiRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DestinationViewModel(private val repository: SerpApiRepository) : ViewModel() {

    private val _destinations = MutableLiveData<List<Destination>?>()
    val destinations: MutableLiveData<List<Destination>?> = _destinations

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun fetchDestinations() {
        val parameters = mapOf(
            "engine" to "google",
            "q" to "Bali",
            "location_requested" to "Indonesia",
            "api_key" to "22d2f7d71dedbf8016b863a3be8badf1f2196e1b7f5a212dac94f6f1a6b86227"
        )

        repository.fetchPopularDestinations(parameters).enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.isSuccessful) {
                    val destinationsList = response.body()?.popular_destinations?.destinations
                    if (destinationsList.isNullOrEmpty()) {
                        _error.postValue("No destinations found.")
                    } else {
                        _destinations.postValue(destinationsList)
                    }
                } else {
                    _error.postValue("Failed to fetch data: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                _error.postValue(t.message ?: "Unknown error")
            }
        })
    }
}


