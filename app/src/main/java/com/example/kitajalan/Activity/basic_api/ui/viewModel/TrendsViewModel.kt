package com.example.kitajalan.Activity.basic_api.ui.viewModel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kitajalan.Activity.basic_api.data.model.ProductPostRequest
import com.example.kitajalan.Activity.basic_api.data.model.TrendsPostRequest
import com.example.kitajalan.Activity.basic_api.data.model.TrendsResponse
import com.example.kitajalan.Activity.basic_api.data.repository.TrendsRepository
import kotlinx.coroutines.launch

class TrendsViewModel(private val repository: TrendsRepository) : ViewModel(){
    private val _data = MutableLiveData<Resource<TrendsResponse>>()
    val data: LiveData<Resource<TrendsResponse>> = _data

    private val _createStatus = MutableLiveData<Resource<Unit>>()
    val createStatus : LiveData<Resource<Unit>> = _createStatus

    private val _deleteStatus = MutableLiveData<Resource<Unit>>()
    val deleteStatus : LiveData<Resource<Unit>> = _deleteStatus

    fun getTrends(context: Context, forceRefresh: Boolean = false) {
        if (_data.value == null || forceRefresh) {
            if (NetworkUtils.isNetworkAvailable(context)) {
                viewModelScope.launch {
                    try {
                        _data.value = Resource.Loading()
                        val response = repository.fetchDestination()
                        if (response.items.isEmpty()) {
                            _data.postValue(Resource.Empty("No Trends found"))
                        } else {
                            _data.postValue(Resource.Success(response))
                        }
                    } catch (e: Exception) {
                        _data.postValue(Resource.Error("Unknown error: ${e.message}"))
                    }
                }
            } else {
                _data.postValue(Resource.Error("No internet connection"))
            }
        }
    }

    fun createTrends(context: Context, product: List<TrendsPostRequest>) {
        if (NetworkUtils.isNetworkAvailable(context)) {
            viewModelScope.launch {
                try {
                    _createStatus.value = Resource.Loading()

                    val response = repository.createDestination(product)
                    _createStatus.postValue(Resource.Success(Unit))

                    getTrends(context, forceRefresh = true)
                } catch (e: Exception) {
                    _data.postValue(Resource.Error("Unknown error: ${e.message}"))
                }
            }
        } else {
            _createStatus.postValue(Resource.Error("No internet connection"))
        }
    }
}