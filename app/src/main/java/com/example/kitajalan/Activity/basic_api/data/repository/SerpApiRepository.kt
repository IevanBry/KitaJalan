package com.example.kitajalan.Activity.basic_api.data.repository

import com.example.kitajalan.Activity.basic_api.data.model.ApiResponse
import com.example.kitajalan.Activity.basic_api.data.network.SerpApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Call

class SerpApiRepository {
    private val apiService: SerpApiService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://serpapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        apiService = retrofit.create(SerpApiService::class.java)
    }

    fun fetchPopularDestinations(parameters: Map<String, String>): Call<ApiResponse> {
        return apiService.getPopularDestinations(parameters)
    }
}

