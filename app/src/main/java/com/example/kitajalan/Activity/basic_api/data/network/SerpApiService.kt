package com.example.kitajalan.Activity.basic_api.data.network

import com.example.kitajalan.Activity.basic_api.data.model.ApiResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface SerpApiService {
    @GET("search.json")
    fun getPopularDestinations(@QueryMap parameters: Map<String, String>) : Call<ApiResponse>
}
