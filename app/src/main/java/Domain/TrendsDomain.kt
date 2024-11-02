package domain

data class TrendsDomain(
    val title: String,
    val subtitle: String,
    val picAddress: String,
    val description: String,
    val price: String,
    var isFavorite: Boolean = false
)