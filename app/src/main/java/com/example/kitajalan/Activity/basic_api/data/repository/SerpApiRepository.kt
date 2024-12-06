package com.example.kitajalan.Activity.basic_api.data.repository

import com.example.kitajalan.Activity.basic_api.data.model.ApiResponse
import com.example.kitajalan.Activity.basic_api.data.network.SerpApiService
import retrofit2.Call

class SerpApiRepository (
    private val api: SerpApiService
){
    fun fetchPopularDestinations(parameters: Map<String, String>): Call<ApiResponse> {
        return api.getPopularDestinations(parameters)
    }
}

