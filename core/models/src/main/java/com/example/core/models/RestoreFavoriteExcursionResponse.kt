package com.example.core.models

data class RestoreFavoriteExcursionResponse(
    val success: Boolean = false,
    val message: String = "",
    val excursion: ExcursionFavorite? = null,
)
