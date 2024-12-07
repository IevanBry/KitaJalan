package com.example.kitajalan.Activity.basic_api.data.repository

import com.example.kitajalan.Activity.basic_api.data.model.TrendsPostRequest
import com.example.kitajalan.Activity.basic_api.data.model.TrendsResponse
import com.example.kitajalan.Activity.basic_api.data.network.ApiService

class TrendsRepository(
    private val api: ApiService,
) {
    private val tokenBearer = "Bearer PcsPAEEisvYRT1l85x9n_02CzP_cNb9zNY-hpOtXmJ_1kxJqAw"

    suspend fun fetchDestination(): TrendsResponse {
        return api.getTrends(tokenBearer)
    }

    suspend fun createDestination(trends: List<TrendsPostRequest>): TrendsResponse {
        return api.createTrends(tokenBearer, trends)
    }

    suspend fun deleteDestination(uuid: String): Unit {
        api.deleteTrends(tokenBearer, uuid)
    }

}