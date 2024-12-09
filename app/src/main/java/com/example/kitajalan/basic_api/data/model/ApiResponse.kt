package com.example.kitajalan.basic_api.data.model

data class ApiResponse(
    val popular_destinations: PopularDestinations
)

data class PopularDestinations(
    val destinations: List<Destination>
)
