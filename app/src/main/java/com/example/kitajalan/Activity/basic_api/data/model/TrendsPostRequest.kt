package com.example.kitajalan.Activity.basic_api.data.model

class TrendsPostRequest (
    val title: String,
    val subtitle: String,
    val picAddress: String,
    val description: String,
    val price: String,
    var isFavorite: Boolean = false
)