package com.example.kitajalan.Activity.basic_api.data.repository

import com.example.kitajalan.Activity.basic_api.data.model.ProductPostRequest
import com.example.kitajalan.Activity.basic_api.data.model.ProductResponse
import com.example.kitajalan.Activity.basic_api.data.network.ApiService

class ProductRepository(
    private val api: ApiService,
) {
    private val tokenBearer = "Bearer x6WxpZW2lCyQWgXubWOCgGgdB1lle01b64-wbDBu4L6asNJeSA"

    suspend fun fetchProduct(): ProductResponse {
        return api.getProducts(tokenBearer)
    }

    suspend fun createProduct(products: List<ProductPostRequest>): ProductResponse {
        return api.createProduct(tokenBearer, products)
    }


}