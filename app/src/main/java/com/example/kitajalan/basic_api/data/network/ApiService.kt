package com.example.kitajalan.basic_api.data.network

import com.example.kitajalan.basic_api.data.model.ProductPostRequest
import com.example.kitajalan.basic_api.data.model.ProductResponse
import com.example.kitajalan.basic_api.data.model.TrendsPostRequest
import com.example.kitajalan.basic_api.data.model.TrendsResponse
import com.example.kitajalan.basic_api.data.model.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @GET("users")
    suspend fun getUsers(): List<User>
    @POST("product")
    suspend fun createProduct(
        @Header("Authorization") token: String,
        @Body products: List<ProductPostRequest>,
    ): ProductResponse

    @GET("product")
    suspend fun getProducts(
        @Header("Authorization") token: String
    ): ProductResponse

    @GET("trends")
    suspend fun getTrends(
        @Header("Authorization") token: String
    ): TrendsResponse
    @POST("trends")
    suspend fun createTrends(
        @Header("Authorization") token: String,
        @Body trends: List<TrendsPostRequest>,
    ): TrendsResponse
    @DELETE("trends/{uuid}")
    suspend fun deleteTrends(
        @Header("Authorization") token: String,
        @Path("uuid") uuid: String
    ): Response<Unit>


}