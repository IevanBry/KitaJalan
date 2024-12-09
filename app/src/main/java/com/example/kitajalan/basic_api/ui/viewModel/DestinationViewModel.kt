package com.example.kitajalan.basic_api.ui.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kitajalan.basic_api.data.model.ApiResponse
import com.example.kitajalan.basic_api.data.model.Destination
import com.example.kitajalan.basic_api.data.repository.SerpApiRepository
import com.example.kitajalan.basic_api.utils.NetworkUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DestinationViewModel(private val repository: SerpApiRepository) : ViewModel() {

    private val _destinations = MutableLiveData<Resource<List<Destination>>>()
    val destinations: LiveData<Resource<List<Destination>>> = _destinations

    fun fetchDestinations(context: Context, query: String, forceRefresh: Boolean = false) {
        if (!NetworkUtils.isNetworkAvailable(context)) {
            _destinations.postValue(Resource.Error("No internet connection. Please check your connection."))
            return
        }

        if (forceRefresh) {
            _destinations.postValue(Resource.Loading())
        }

        if (!forceRefresh && _destinations.value is Resource.Success) {
            val cachedData = (_destinations.value as Resource.Success).data
            if (!cachedData.isNullOrEmpty()) {
                _destinations.postValue(Resource.Success(cachedData))
                return
            }
        }

        val parameters = mapOf(
            "engine" to "google",
            "q" to query,
            "location_requested" to "Indonesia",
            "api_key" to "22d2f7d71dedbf8016b863a3be8badf1f2196e1b7f5a212dac94f6f1a6b86227"
        )

        repository.fetchPopularDestinations(parameters).enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.isSuccessful) {
                    val destinationsList = response.body()?.popular_destinations?.destinations
                    if (destinationsList.isNullOrEmpty()) {
                        _destinations.postValue(Resource.Empty("No destinations found."))
                    } else {
                        _destinations.postValue(Resource.Success(destinationsList))
                    }
                } else {
                    _destinations.postValue(Resource.Error("Failed to fetch data: ${response.code()}"))
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                _destinations.postValue(Resource.Error(t.message ?: "Unknown error occurred."))
            }
        })
    }
}
