package com.example.kitajalan.basic_api.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kitajalan.basic_api.data.local.entity.ProductEntity
import com.example.kitajalan.basic_api.data.model.ProductResponse
import com.example.kitajalan.basic_api.data.repository.ProductRepository
import kotlinx.coroutines.launch

//class ProductViewModel(private val repository: ProductRepository) : ViewModel() {
//    private val _data = MutableLiveData<Resource<ProductResponse>>()
//    val data: LiveData<Resource<ProductResponse>> = _data
//
//    private val _createStatus = MutableLiveData<Resource<Unit>>()
//    val createStatus : LiveData<Resource<Unit>> = _createStatus
//
//    fun getProducts(context: Context, forceRefresh: Boolean = false) {
//        if (_data.value == null || forceRefresh) {
//            if (NetworkUtils.isNetworkAvailable(context)) {
//                viewModelScope.launch {
//                    try {
//                        //delay(3000)
//                        _data.value = Resource.Loading()
//                        val response = repository.fetchProduct()
//                        if (response.items.isEmpty()) {
//                            _data.postValue(Resource.Empty("No product found"))
//                        } else {
//                            _data.postValue(Resource.Success(response))
//                        }
//                    } catch (e: Exception) {
//                        _data.postValue(Resource.Error("Unknown error: ${e.message}"))
//                    }
//                }
//            } else {
//                _data.postValue(Resource.Error("No internet connection"))
//            }
//        }
//    }
//
//    fun createProduct(context: Context, product: List<ProductPostRequest>) {
//        if (NetworkUtils.isNetworkAvailable(context)) {
//            viewModelScope.launch {
//                try {
//                    _createStatus.value = Resource.Loading()
//
//                    val response = repository.createProduct(product)
//                    _createStatus.postValue(Resource.Success(Unit))
//
//                    getProducts(context, forceRefresh = true)
//                } catch (e: Exception) {
//                    _data.postValue(Resource.Error("Unknown error: ${e.message}"))
//                }
//            }
//        } else {
//            _createStatus.postValue(Resource.Error("No internet connection"))
//        }
//    }
//}

class ProductViewModel(private val repository: ProductRepository) : ViewModel() {

    private val _data = MutableLiveData<Resource<ProductResponse>>()
    val data: LiveData<Resource<ProductResponse>> = _data

    private val _createStatus = MutableLiveData<Resource<Unit>>()
    val createStatus : LiveData<Resource<Unit>> = _createStatus

    //Ini function dan LiveData untuk aksi getProductLocal
    private val _dataLocal = MutableLiveData<Resource<List<ProductEntity>>>()
    val dataLocal: LiveData<Resource<List<ProductEntity>>> get() = _dataLocal
    fun getProductsLocal(forceRefresh: Boolean = false) {
        if (_dataLocal.value == null || forceRefresh) {
            viewModelScope.launch {
                try {
                    _dataLocal.value = Resource.Loading()
                    repository.getProductRoom().collect { response ->
                        if (response.isEmpty()) {
                            _dataLocal.postValue(Resource.Empty("No data found"))
                        } else {
                            _dataLocal.postValue(Resource.Success(response))
                        }
                    }
                } catch (e: Exception) {
                    _data.postValue(Resource.Error("Unknown error: ${e.message}"))
                }
            }
        }
    }

    //Ini function dan LiveData untuk aksi createProductLocal
    private val _createLocalStatus = MutableLiveData<Resource<Unit>>() // Status create
    val createLocalStatus: LiveData<Resource<Unit>> = _createLocalStatus
    fun createProductLocal(product: ProductEntity) {
        viewModelScope.launch {
            try {
                _createLocalStatus.value = Resource.Loading()

                repository.insertProductRoom(product)

                _createLocalStatus.postValue(Resource.Success(Unit))
            } catch (e: Exception) {
                _data.postValue(Resource.Error("Unknown error: ${e.message}"))
            }

        }
    }
}