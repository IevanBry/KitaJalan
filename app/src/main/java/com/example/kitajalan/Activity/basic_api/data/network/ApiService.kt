package com.example.kitajalan.Activity.basic_api.data.network

import com.example.kitajalan.Activity.basic_api.data.model.User
import retrofit2.http.GET

interface ApiService {
    @GET("users")
    suspend fun getUsers(): List<User>


}