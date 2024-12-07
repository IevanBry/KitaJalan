package com.example.kitajalan.Activity.basic_api.data.model

data class TrendsResponse(
    val cursor: String,
    val items: List<TrendsDomain>,
    val next_page: String,
)

data class TrendsDomain(
    val _uuid: String,
    val title: String,
    val subtitle: String,
    val picAddress: String,
    val description: String,
    val price: String,
    var isFavorite: Boolean = false
)