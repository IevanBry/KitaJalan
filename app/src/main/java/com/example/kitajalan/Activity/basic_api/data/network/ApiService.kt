package com.example.kitajalan.Activity.basic_api.data.network

import com.example.kitajalan.Activity.basic_api.data.model.ProductPostRequest
import com.example.kitajalan.Activity.basic_api.data.model.ProductResponse
import com.example.kitajalan.Activity.basic_api.data.model.User
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {
    @GET("users")
    suspend fun getUsers(): List<User>    @POST("product")
    suspend fun createProduct(
        @Header("Authorization") token: String,
        @Body products: List<ProductPostRequest>,
    ): ProductResponse

    @GET("product")
    suspend fun getProducts(
        @Header("Authorization") token: String
    ): ProductResponse
}