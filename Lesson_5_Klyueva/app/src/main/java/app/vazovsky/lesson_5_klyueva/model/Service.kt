package app.vazovsky.lesson_5_klyueva.model

data class Service(
    val title: String,
    val coupon: String,
    val address: String,
    val image: String? = "",
    var isFavorite: Boolean = false
)